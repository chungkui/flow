package org.snaker.api.request;

public class DeployRequest {
    /**
     * 流程定义xml
     */
    String xml;
    /**
     * 流程定义id
     */
    String id;

    public String getXml() {
        return xml;
    }
    String createUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
