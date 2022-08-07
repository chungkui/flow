/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.parser.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.AssignmentHandler;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.model.FieldModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.parser.AbstractNodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务节点解析类
 * @author yuqs
 * @since 1.0
 */
@Component
public class TaskParser extends AbstractNodeParser {
	@Autowired
	List<AssignmentHandler> assignmentHandlers;
	/**
	 * 由于任务节点需要解析form、assignee属性，这里覆盖抽象类方法实现
	 */
	protected void parseNode(NodeModel node, Element element) {
		TaskModel task = (TaskModel)node;
		task.setForm(element.getAttribute(ATTR_FORM));
		task.setAssignee(element.getAttribute(ATTR_ASSIGNEE));
		task.setExpireTime(element.getAttribute(ATTR_EXPIRETIME));
		task.setAutoExecute(element.getAttribute(ATTR_AUTOEXECUTE));
		task.setCallback(element.getAttribute(ATTR_CALLBACK));
		task.setReminderTime(element.getAttribute(ATTR_REMINDERTIME));
		task.setReminderRepeat(element.getAttribute(ATTR_REMINDERREPEAT));
		task.setPerformType(element.getAttribute(ATTR_PERFORMTYPE));
		task.setTaskType(element.getAttribute(ATTR_TASKTYPE));
		setAssignmentHandler(task,element.getAttribute(ATTR_ASSIGNEE_HANDLER));
        NodeList fieldList = element.getElementsByTagName(ATTR_FIELD);
        List<FieldModel> fields = new ArrayList<FieldModel>();
        for(int i = 0; i < fieldList.getLength(); i++) {
            Element item = (Element)fieldList.item(i);
            FieldModel fieldModel = new FieldModel();
            fieldModel.setName(item.getAttribute(ATTR_NAME));
            fieldModel.setDisplayName(item.getAttribute(ATTR_DISPLAYNAME));
            fieldModel.setType(item.getAttribute(ATTR_TYPE));
            NodeList attrList = item.getElementsByTagName(ATTR_ATTR);
            for(int j = 0; j < attrList.getLength(); j++) {
                Node attr = attrList.item(j);
                fieldModel.addAttr(((Element) attr).getAttribute(ATTR_NAME),
                        ((Element) attr).getAttribute(ATTR_VALUE));
            }
            fields.add(fieldModel);
        }
        task.setFields(fields);
	}

	private void setAssignmentHandler(TaskModel task,String assignmentHandler){
		if(CollectionUtil.isNotEmpty(assignmentHandlers)&&StringUtils.isNotEmpty(assignmentHandler)){
			assignmentHandlers.forEach(handler -> {
				if(StringUtils.equals(handler.type(),assignmentHandler)){
					task.setAssignmentHandler(handler);
					return;
				}
			});
			AssertHelper.notNull(task.getAssignmentHandler(), "分配参与者处理类实例化失败");
		}
	}
	/**
	 * 产生TaskModel模型对象
	 */
	protected NodeModel newModel() {
		return new TaskModel();
	}

	@Override
	public boolean nameEq(String name) {
		return StringUtils.equals(name,"task");

	}
}
