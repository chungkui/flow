package org.snaker.engine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.snaker.api.TaskApi;
import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.*;
import org.snaker.api.response.GetTaskActorsResponse;
import org.snaker.api.response.WorkItemResponse;
import org.snaker.engine.core.SnakerEngine;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.entity.po.TaskActor;
import org.snaker.engine.entity.po.WorkItem;
import org.snaker.engine.entity.vo.WorkItemFilter;
import org.snaker.engine.service.TaskActorService;
import org.snaker.engine.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TaskController implements TaskApi {
    @Autowired
    private SnakerEngine engine;
    @Autowired
    private TaskService taskService;
    @Autowired
    TaskActorService taskActorService;

    @Override
    public Response<ResPage<WorkItemResponse>> majorTask(WorkRequest workRequest) {
        Page<WorkItem> page = new Page();
        page.setCurrent(workRequest.getPage());
        page.setSize(workRequest.getPageSize());
        WorkItemFilter workItemFilter = new WorkItemFilter();
        workItemFilter.setOperators(new String[]{workRequest.getAssignees()});
        workItemFilter.setTaskType(workRequest.getType());
        return Response.page(taskService.listWorkItems(page, workItemFilter), WorkItemResponse.class);
    }

    @Override
    public void ccTask() {

    }

    @Override
    public Response<String> addActor(AddActorRequest addActorRequest) {
        List<Task> tasks = engine.task().listActiveTasks(addActorRequest.getOrderId(), null, null);
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(addActorRequest.getTaskName())
                    && StringUtils.isNotEmpty(addActorRequest.getOperator())) {
                engine.task().addTaskActor(task.getId(), addActorRequest.getOperator());
            }
        }
        return Response.success();
    }

    @Override
    public Response<List<GetTaskActorsResponse>> getTaskActor(GetTaskActorsRequest getTaskActorsRequest) {
        List<Task> tasks = engine.task().listActiveTasks(getTaskActorsRequest.getOrderId());
        List<TaskActor> actors = Lists.newArrayList();
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(getTaskActorsRequest.getTaskName())) {
                List<TaskActor> datas = taskActorService.getTaskActorsByTaskId(task.getId());
                actors.addAll(datas);
            }
        }
        return Response.list(actors, GetTaskActorsResponse.class);
    }

    @Override
    public Response<String> execTask(ExecTaskRequest execTaskRequest) {
        engine.executeTask(execTaskRequest.getTaskId(), execTaskRequest.getUserName(), null);
        return Response.success();
    }

    @Override
    public Response<String> reject(ExecTaskRequest execTaskRequest) {
        engine.executeAndJumpTask(execTaskRequest.getTaskId(),
                execTaskRequest.getUserName(), null, null);
        return Response.success();
    }

    @Override
    public Response<String> undo(ExecTaskRequest execTaskRequest) {
        engine.task().withdrawTask(execTaskRequest.getTaskId(),execTaskRequest.getUserName());
        return Response.success();
    }

    @Override
    public Response<ResPage<WorkItemResponse>> listUserHistoryTask(ListUserHistoryTaskRequest listUserHistoryTaskRequest) {
        Page<WorkItem> page = new Page();
        page.setCurrent(listUserHistoryTaskRequest.getPage());
        page.setSize(listUserHistoryTaskRequest.getPageSize());
        WorkItemFilter workItemFilter = new WorkItemFilter();
        workItemFilter.setOperators(new String[]{listUserHistoryTaskRequest.getUserName()});
        return Response.page(taskService.listHistoryWorkItems(page, workItemFilter), WorkItemResponse.class);
    }
}
