package org.snaker.engine.controller;

import org.snaker.api.FlowApi;
import org.snaker.api.common.Response;
import org.snaker.api.request.ExecTaskRequest;
import org.snaker.engine.core.SnakerEngine;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FlowController implements FlowApi {
    @Autowired
    private SnakerEngine engine;

    @Override
    public Response<String> execTask(ExecTaskRequest execTaskRequest) {
        engine.executeTask(execTaskRequest.getTaskId(), execTaskRequest.getUserName(), execTaskRequest.getParams());
        return Response.success();
    }

    @Override
    public Response<String> executeAndJump(ExecTaskRequest execTaskRequest) {
        engine.executeAndJumpTask(execTaskRequest.getTaskId(), execTaskRequest.getUserName(), execTaskRequest.getParams(), execTaskRequest.getNodeName());
        return Response.success();
    }

    @Override
    public Response<String> transferMajor(ExecTaskRequest execTaskRequest) {
        String[] act = (String[]) execTaskRequest.getNextOperator().toArray();
        engine.task().createNewTask(
                execTaskRequest.getTaskId(),
                TaskModel.TaskType.Major.ordinal(), act);
        engine.task().complete(execTaskRequest.getTaskId(), execTaskRequest.getUserName());
        return Response.success();
    }

    @Override
    public Response<String> transferAidant(ExecTaskRequest execTaskRequest) {
        String[] actors = (String[]) execTaskRequest.getNextOperator().toArray();
        engine.task().createNewTask(execTaskRequest.getTaskId(),
                TaskModel.TaskType.Aidant.ordinal(), actors);
        engine.task().complete(execTaskRequest.getTaskId(), execTaskRequest.getUserName());
        return Response.success();
    }
}
