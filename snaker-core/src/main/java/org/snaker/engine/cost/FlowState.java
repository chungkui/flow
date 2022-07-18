package org.snaker.engine.cost;

public enum FlowState {
    STATE_ACTIVE(1,"活动状态"),
    STATE_FINISH(0,"结束状态"),
    STATE_TERMINATION(2,"终止状态");
    private Integer state;
    private String desc;

    FlowState(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
