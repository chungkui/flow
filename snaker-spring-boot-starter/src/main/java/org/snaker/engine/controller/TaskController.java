package org.snaker.engine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snaker.api.TaskApi;
import org.snaker.api.common.ResPage;
import org.snaker.api.common.Response;
import org.snaker.api.request.TaskRequest;
import org.snaker.api.response.TaskResponse;
import org.snaker.engine.core.SnakerEngine;
import org.snaker.engine.entity.po.WorkItem;
import org.snaker.engine.entity.vo.WorkItemFilter;
import org.snaker.engine.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController implements TaskApi {
    @Autowired
    private SnakerEngine engine;
    @Autowired
    private TaskService taskService;

    @Override
    public Response<ResPage<TaskResponse>> majorTask(TaskRequest taskRequest) {
        Page<WorkItem> page = new Page();
        page.setCurrent(taskRequest.getPage());
        page.setSize(taskRequest.getPageSize());
        WorkItemFilter workItemFilter = new WorkItemFilter();
        workItemFilter.setOperators(new String[]{taskRequest.getAssignees()});
        workItemFilter.setTaskType(taskRequest.getType());
        return Response.page( taskService.listWorkItems(page, workItemFilter),TaskResponse.class);
    }

    @Override
    public void ccTask() {

    }

    @Override
    public void aidTask() {

    }
}
