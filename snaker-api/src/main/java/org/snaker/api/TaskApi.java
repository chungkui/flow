package org.snaker.api;

import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.*;
import org.snaker.api.response.ListUserHistoryTaskResponse;
import org.snaker.api.response.WorkItemResponse;
import org.snaker.api.response.GetTaskActorsResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
public interface TaskApi {

    /**
     * 主办任务/协办任务
     */
    @PostMapping("/work")
    Response<ResPage<WorkItemResponse>> majorTask(@RequestBody WorkRequest workRequest);
    /**
     * 抄送任务
     */
    @PostMapping("/cc")
    void ccTask();

    /**
     * 添加参与者
     * @return
     */
    @PostMapping("/addActor")
    Response<String> addActor(@RequestBody AddActorRequest addActorRequest);


    /**
     * 获取任务参与者
     * @return
     */
    @PostMapping("/getTaskActor")
    Response<List<GetTaskActorsResponse>> getTaskActor(@RequestBody GetTaskActorsRequest getTaskActorsRequest);

    /**
     * 执行任务
     * @return
     */
    @PostMapping("/execTask")
    Response<String> execTask(@RequestBody ExecTaskRequest execTaskRequest);
    @PostMapping("/reject")
    Response<String> reject(@RequestBody ExecTaskRequest execTaskRequest);

    @PostMapping("/listUserHistoryTask")
    Response<ResPage<ListUserHistoryTaskResponse>> listUserHistoryTask(@RequestBody ListUserHistoryTaskRequest listUserHistoryTaskRequest);
}
