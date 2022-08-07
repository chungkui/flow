package org.snaker.engine.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.snaker.api.FlowDesignerApi;
import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.DeployRequest;
import org.snaker.api.request.ProcessRequest;
import org.snaker.api.request.ShowFlowStateRequest;
import org.snaker.api.request.StartRequest;
import org.snaker.api.response.ProcessResponse;
import org.snaker.api.response.ShowFlowStateResponse;
import org.snaker.engine.core.SnakerEngine;
import org.snaker.engine.entity.po.HistTask;
import org.snaker.engine.entity.po.Process;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.parser.ModelParser;
import org.snaker.engine.service.HistTaskService;
import org.snaker.engine.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
@Slf4j
public class ProcessController implements FlowDesignerApi {
    public static final Integer STATE_ACTIVE = 1;
    public static final Integer STATE_FINISH = 0;
    public static final Integer STATE_TERMINATION = 2;
    @Autowired
    ProcessService processService;
    @Autowired
    private SnakerEngine engine;
    @Autowired
    ModelParser modelParser;
    @Autowired
    HistTaskService histTaskService;
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
            //对xml进行处理
            String xml = SnakerHelper.convertXml(deployRequest.getXml());
            input = StreamHelper.getStreamFromString(xml);
            // string 转bytes
            byte[] bytes = StreamHelper.readBytes(input);
            ProcessModel model = modelParser.parse(bytes);
            Integer version = engine.process().getMaxVersion(model.getDisplayName());
            Process entity = new Process();
            if(StringUtils.isEmpty(deployRequest.getId())){
                entity.setId(StringHelper.getPrimaryKey());
            }else {
                entity.setId(deployRequest.getId());
            }
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
            log.error("s error",e);
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

    @ResponseBody
    public Response<ShowFlowStateResponse> showOrder(ShowFlowStateRequest showFlowStateRequest){
        Process process = engine.process().getProcessById(showFlowStateRequest.getProcessId());
        AssertHelper.notNull(process);
        ProcessModel model = process.getModel();
        ShowFlowStateResponse showFlowStateResponse = new ShowFlowStateResponse();
        if(model != null) {
            showFlowStateResponse.setProcess(SnakerHelper.getModelJson(model));
        }

        if(StringUtils.isNotEmpty(showFlowStateRequest.getOrderId())) {
            List<Task> tasks = engine.task().listActiveTasks(showFlowStateRequest.getOrderId());
            List<HistTask> historyTasks =histTaskService.listByOrderId(showFlowStateRequest.getOrderId());
            showFlowStateResponse.setState( SnakerHelper.getStateJson(model, tasks, historyTasks));
        }
        //{"historyRects":{"rects":[{"paths":["TO 任务1"],"name":"开始"},{"paths":["TO 分支"],"name":"任务1"},{"paths":["TO 任务3","TO 任务4","TO 任务2"],"name":"分支"}]}}
        return Response.success(showFlowStateResponse);
    }
}

