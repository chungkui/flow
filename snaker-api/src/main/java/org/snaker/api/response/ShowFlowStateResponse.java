package org.snaker.api.response;

public class ShowFlowStateResponse {
    private String process;
    private String state;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
