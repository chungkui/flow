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
package org.snaker.engine.parser;

import org.snaker.engine.model.NodeModel;
import org.w3c.dom.Element;

/**
 * 节点解析接口
 * @author yuqs
 * @since 1.0
 */
 public interface NodeParser {
	/**
	 * 变迁节点名称
	 */
	  String NODE_TRANSITION = "transition";

	/**
	 * 节点属性名称
	 */
	  String ATTR_NAME = "name";
	  String ATTR_DISPLAYNAME = "displayName";
	  String ATTR_INSTANCEURL = "instanceUrl";
	  String ATTR_INSTANCENOCLASS = "instanceNoClass";
	  String ATTR_EXPR = "expr";
	  String ATTR_HANDLECLASS = "handleClass";
	  String ATTR_FORM = "form";
      String ATTR_FIELD = "field";
      String ATTR_VALUE = "value";
      String ATTR_ATTR = "attr";
      String ATTR_TYPE= "type";
	  String ATTR_ASSIGNEE = "assignee";
	  String ATTR_ASSIGNEE_HANDLER = "assignmentHandler";
	  String ATTR_PERFORMTYPE = "performType";
	  String ATTR_TASKTYPE = "taskType";
	  String ATTR_TO = "to";
	  String ATTR_PROCESSNAME = "processName";
	  String ATTR_VERSION = "version";
	  String ATTR_EXPIRETIME = "expireTime";
	  String ATTR_AUTOEXECUTE = "autoExecute";
	  String ATTR_CALLBACK = "callback";
	  String ATTR_REMINDERTIME = "reminderTime";
	  String ATTR_REMINDERREPEAT = "reminderRepeat";
      String ATTR_CLAZZ = "clazz";
      String ATTR_METHODNAME = "methodName";
      String ATTR_ARGS = "args";
      String ATTR_VAR = "var";
      String ATTR_LAYOUT = "layout";
      String ATTR_G = "g";
      String ATTR_OFFSET = "offset";
      String ATTR_PREINTERCEPTORS = "preInterceptors";
      String ATTR_POSTINTERCEPTORS = "postInterceptors";

	/**
	 * 节点dom元素解析方法，由实现类完成解析
	 * @param element dom元素
	 */
	 void parse(Element element);

	/**
	 * 解析成功后，提供返回NodeModel模型对象
	 * @return 节点模型
	 */
	 NodeModel getModel();

	 boolean nameEq(String name);
}
