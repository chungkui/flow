package org.snaker.api;


import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.DeployRequest;
import org.snaker.api.request.ProcessRequest;
import org.snaker.api.request.StartRequest;
import org.snaker.api.response.ProcessResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义api
 */
@RestController
@RequestMapping(value = "/process")
public interface FlowDesignerApi {
    /**
     * 流程定义查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    Response<ResPage<ProcessResponse>> list(@RequestBody ProcessRequest processRequest);
    /**
     * 部署或者更新流程定义 文件格式
     */
    /**
     * 部署或者更新流程定义 xml格式
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    Response deploy(@RequestBody DeployRequest deployRequest);

    /**
     * 删除流程定义
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    Response delete(@PathVariable("id") String id);

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    Response start(@RequestBody StartRequest startRequest);
}
