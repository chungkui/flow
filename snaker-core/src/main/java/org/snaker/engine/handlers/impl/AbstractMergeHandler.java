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
package org.snaker.engine.handlers.impl;

import java.util.List;


import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.po.Order;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.handlers.IHandler;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.SubProcessModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 合并处理的抽象处理器
 * 需要子类提供查询无法合并的task集合的参数map
 * @author yuqs
 * @since 1.0
 */
public abstract class AbstractMergeHandler implements IHandler {
	public void handle(Execution execution) {
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		Order order = execution.getOrder();
		ProcessModel model = execution.getModel();
		String[] activeNodes = findActiveNodes();
		boolean isSubProcessMerged = false;
		boolean isTaskMerged = false;
		if(model.containsNodeNames(SubProcessModel.class, activeNodes)) {
			//查询父流程中除了本实例之外的所有子流程
			List<Order> orders = execution.getEngine().order().
					listActiveChildOrders(order.getId(),new String[]{execution.getChildOrderId()});
			//如果所有子流程都已完成，则表示可合并
			if(orders == null || orders.isEmpty()) {
				isSubProcessMerged = true;
			}
		} else {
			isSubProcessMerged = true;
		}
		if(isSubProcessMerged && model.containsNodeNames(TaskModel.class, activeNodes)) {
			List<Task> tasks =execution.getEngine().task().listActiveTasks(order.getId(),
					new String[]{execution.getTask().getId() },activeNodes);


			if(tasks == null || tasks.isEmpty()) {
				//如果所有task都已完成，则表示可合并
				isTaskMerged = true;
			}
		}
		execution.setMerged(isSubProcessMerged && isTaskMerged);
	}

	/**
	 * 子类需要提供如何查询未合并任务的参数map
	 * @return
	 */
	protected abstract String[] findActiveNodes();
}
