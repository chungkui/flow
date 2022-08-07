package org.snaker.api;

import org.snaker.api.common.Response;
import org.snaker.api.request.ExecTaskRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/task")
public interface FlowApi {
    @PostMapping("/execute")
    Response<String> execTask(@RequestBody ExecTaskRequest execTaskRequest);
    @PostMapping("/executeAndJump")
    Response<String> executeAndJump(@RequestBody ExecTaskRequest execTaskRequest);
    @PostMapping("/transferMajor")
    Response<String> transferMajor(@RequestBody ExecTaskRequest execTaskRequest);
    @PostMapping("/transferAidant")
    Response<String> transferAidant(@RequestBody ExecTaskRequest execTaskRequest);

}
