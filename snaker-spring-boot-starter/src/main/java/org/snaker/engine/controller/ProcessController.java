package org.snaker.engine.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.commons.lang.StringUtils;
import org.snaker.api.FlowDesignerApi;

import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.DeployRequest;
import org.snaker.api.request.ProcessRequest;
import org.snaker.api.request.StartRequest;
import org.snaker.api.response.ProcessResponse;
import org.snaker.engine.core.SnakerEngine;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.parser.ModelParser;
import org.snaker.engine.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.snaker.engine.entity.po.Process;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 流程定义表 前端控制器
 * </p>
 *
 * @author jason
 * @since 2022-07-09
 */
@RestController
@RequestMapping("/process")
public class ProcessController implements FlowDesignerApi {
    public static final Integer STATE_ACTIVE = 1;
    public static final Integer STATE_FINISH = 0;
    public static final Integer STATE_TERMINATION = 2;
    @Autowired
    ProcessService processService;
    @Autowired
    private SnakerEngine engine;
    @Override
    public Response<ResPage<ProcessResponse>> list(ProcessRequest processRequest) {
        Page<Process> resPage = new Page<>();
        QueryWrapper<Process> queryWrapper = new QueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(processRequest.getDisplayName()), "displayName", processRequest.getDisplayName());
        return Response.page(processService.page(resPage, queryWrapper),
                ProcessResponse.class);
    }

    @Override
    public Response deploy(DeployRequest deployRequest) {
        InputStream input = null;
        try {
            //对xml进行处理 todo xml前缀交给前台拼接
            String xml = /*"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +*/
                    SnakerHelper.convertXml(deployRequest.getXml());
            input = StreamHelper.getStreamFromString(xml);
            // string 转bytes
            byte[] bytes = StreamHelper.readBytes(input);
            ProcessModel model = ModelParser.parse(bytes);
            Integer version = engine.process().getMaxVersion(model.getDisplayName());
            Process entity = new Process();
            entity.setId(deployRequest.getId());
            if (version != null && version >= 0) {
                entity.setVersion(version + 1);
            } else {
                entity.setVersion(0);
            }
            entity.setState(STATE_ACTIVE);
            entity.setContent(bytes);
            entity.setCreateTime(DateHelper.getTime());
            entity.setCreator(deployRequest.getCreateUser());
            processService.saveOrUpdate(entity);
            return Response.success();
        } catch (Exception e) {
            return Response.failed();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Response delete(String id) {
        processService.removeById(id);
        return Response.success();
    }

    @Override
    public Response start(StartRequest startRequest) {
        return Response.success(engine.startInstanceByName(startRequest.getProcessName(),
                null, startRequest.getOperator(), null));
    }
}

