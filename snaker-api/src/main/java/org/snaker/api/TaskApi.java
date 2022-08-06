package org.snaker.api;

import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.TaskRequest;
import org.snaker.api.response.TaskResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/task")
public interface TaskApi {

    /**
     * 主办任务
     */
    @PostMapping("/major")
    Response<ResPage<TaskResponse>> majorTask(@RequestBody TaskRequest taskRequest);
    /**
     * 抄送任务
     */
    @PostMapping("/cc")
    void ccTask();

    /**
     * 协办任务
     */
    @PostMapping("/aid")
    void aidTask();
}
