package org.snaker.engine.test;

import org.snaker.engine.AssignmentHandler;
import org.snaker.engine.core.Execution;
import org.springframework.stereotype.Component;

@Component
public class TestAssignmentHandler implements AssignmentHandler {
    @Override
    public Object assign(Execution execution) {
        return "jason";
    }

    @Override
    public String type() {
        return "test";
    }
}
