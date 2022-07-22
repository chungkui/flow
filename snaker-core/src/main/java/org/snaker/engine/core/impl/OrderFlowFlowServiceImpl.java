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
package org.snaker.engine.core.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.hutool.extra.cglib.CglibUtil;
import org.snaker.engine.Completion;
import org.snaker.engine.core.OrderFlowService;
import org.snaker.engine.core.SnakerEngine;

import org.snaker.engine.cost.FlowState;
import org.snaker.engine.entity.po.*;
import org.snaker.engine.entity.po.Process;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.service.*;

/**
 * 流程实例业务类
 *
 * @author yuqs
 * @since 1.0
 */
public class OrderFlowFlowServiceImpl  implements OrderFlowService {
    OrderService orderService;

    CcOrderService ccOrderService;
    HistOrderService histOrderService;
    private Completion completion;
    private TaskService taskService;
    TaskFlowFlowServiceImpl taskFlowService;
    HistTaskService histTaskService;

    public Order getOrderById(String orderId) {
        return orderService.getById(orderId);
    }

    /**
     * 创建活动实例
     *
     * @see OrderFlowFlowServiceImpl#createOrder(Process, String, Map, String, String)
     */
    public Order createOrder(Process process, String operator, Map<String, Object> args) {
        return createOrder(process, operator, args, null, null);
    }

    /**
     * 创建活动实例
     */
    public Order createOrder(Process process, String operator, Map<String, Object> args,
                             String parentId, String parentNodeName) {
        Order order = new Order();
        order.setId(StringHelper.getPrimaryKey());
        order.setParentId(parentId);
        order.setParentNodeName(parentNodeName);
        order.setCreateTime(DateHelper.getTime());
        order.setLastUpdateTime(order.getCreateTime());
        order.setCreator(operator);
        order.setLastUpdator(order.getCreator());
        order.setProcessId(process.getId());
        ProcessModel model = process.getModel();
        if (model != null && args != null) {
            if (StringHelper.isNotEmpty(model.getExpireTime())) {
                String expireTime = DateHelper.parseTime(args.get(model.getExpireTime()));
                order.setExpireTime(expireTime);
            }
            String orderNo = (String) args.get(SnakerEngine.ID);
            if (StringHelper.isNotEmpty(orderNo)) {
                order.setOrderNo(orderNo);
            } else {
                order.setOrderNo(model.getGenerator().generate(model));
            }
        }

        order.setVariable(JsonHelper.toJson(args));
        saveOrder(order);
        return order;
    }

    /**
     * 向活动实例临时添加全局变量数据
     *
     * @param orderId 实例id
     * @param args    变量数据
     */
    public void addVariable(String orderId, Map<String, Object> args) {
        Order order = orderService.getById(orderId);
        Map<String, Object> data = JsonHelper.fromJson(order.getVariable(), Map.class);
        if (data == null) {
            data = Collections.emptyMap();
        }
        data.putAll(args);
        order.setVariable(JsonHelper.toJson(data));
        orderService.updateById(order);
    }

    /**
     * 创建实例的抄送
     */
    public void createCCOrder(String orderId, String creator, String... actorIds) {
        for (String actorId : actorIds) {
            CcOrder ccorder = new CcOrder();
            ccorder.setOrderId(orderId);
            ccorder.setActorId(actorId);
            ccorder.setCreator(creator);
            ccorder.setStatus(FlowState.STATE_ACTIVE.getState());
            ccorder.setCreateTime(DateHelper.getTime());
            ccOrderService.save(ccorder);
        }
    }

    /**
     * 流程实例数据会保存至活动实例表、历史实例表
     */
    public void saveOrder(Order order) {
        HistOrder history = CglibUtil.copy(order, HistOrder.class);
        history.setOrderState(FlowState.STATE_ACTIVE.getState());
        this.saveOrder(order);
        histOrderService.save(history);
    }

    /**
     * 更新活动实例的last_Updator、last_Update_Time、expire_Time、version、variable
     */
    public void updateOrder(Order order) {
        orderService.updateById(order);
    }

    /**
     * 更新抄送记录状态为已阅
     */
    public void updateCCStatus(String orderId, String... actorIds) {
        List<CcOrder> ccorders = ccOrderService.list(orderId, actorIds);
        AssertHelper.notNull(ccorders);
        for (CcOrder ccorder : ccorders) {
            ccorder.setStatus(FlowState.STATE_FINISH.getState());
            ccorder.setFinishTime(DateHelper.getTime());
        }
        ccOrderService.updateBatchById(ccorders);
    }

    /**
     * 删除指定的抄送记录
     */
    public void deleteCCOrder(String orderId, String actorId) {
        List<CcOrder> ccorders = ccOrderService.list(orderId, actorId);
        AssertHelper.notNull(ccorders);
        for (CcOrder ccorder : ccorders) {
            ccOrderService.removeById(ccorder.getOrderId());
        }
    }

    /**
     * 删除活动流程实例数据，更新历史流程实例的状态、结束时间
     */
    public void complete(String orderId) {
        Order order = orderService.getById(orderId);
        HistOrder history = histOrderService.getById(orderId);
        history.setOrderState(FlowState.STATE_FINISH.getState());
        history.setEndTime(DateHelper.getTime());
        histOrderService.updateById(history);
        orderService.removeById(order.getId());

        if (completion != null) {
            completion.complete(history);
        }
    }

    /**
     * 强制中止流程实例
     *
     * @see OrderFlowFlowServiceImpl#terminate(String, String)
     */
    public void terminate(String orderId) {
        terminate(orderId, null);
    }

    /**
     * 强制中止活动实例,并强制完成活动任务
     */
    public void terminate(String orderId, String operator) {

        List<Task> tasks = taskService.listByOrderId(orderId);

        for (Task task : tasks) {
            taskFlowService.complete(task.getId(), operator);
        }
        Order order = orderService.getById(orderId);
        HistOrder history = CglibUtil.copy(order, HistOrder.class);
        history.setOrderState(FlowState.STATE_TERMINATION.getState());
        history.setEndTime(DateHelper.getTime());
        histOrderService.updateById(history);
        orderService.removeById(order.getId());
        if (completion != null) {
            completion.complete(history);
        }
    }

    /**
     * 激活已完成的历史流程实例
     *
     * @param orderId 实例id
     * @return 活动实例对象
     */
    public Order resume(String orderId) {
        HistOrder historyOrder = histOrderService.getById(orderId);
        Order order = historyOrder.undo();
        orderService.save(order);
        historyOrder.setOrderState(FlowState.STATE_ACTIVE.getState());
        histOrderService.updateById(historyOrder);
        List<HistTask> histTasks = histTaskService.listByOrderId(orderId);
        if (histTasks != null && !histTasks.isEmpty()) {
            HistTask histTask = histTasks.get(0);
            taskFlowService.resume(histTask.getId(), histTask.getOperator());
        }
        return order;
    }

    /**
     * 级联删除指定流程实例的所有数据：
     * 1.wf_order,wf_hist_order
     * 2.wf_task,wf_hist_task
     * 3.wf_task_actor,wf_hist_task_actor
     * 4.wf_cc_order
     *
     * @param id 实例id
     */
    public void cascadeRemove(String id) {
        HistOrder historyOrder = histOrderService.getById(id);
        AssertHelper.notNull(historyOrder);
        List<Task> activeTasks =taskFlowService.getActiveTasks(id);
        List<HistTask> historyTasks = histTaskService.listByOrderId(id);
        for (Task task : activeTasks) {
            taskService.removeById(task.getId());
        }
        for (HistTask historyTask : historyTasks) {
            histTaskService.removeById(historyTask.getId());
        }
        List<CcOrder> ccOrders =ccOrderService.list(id);
        for (CcOrder ccOrder : ccOrders) {
            ccOrderService.removeById(ccOrder.getOrderId());
        }
        Order order = orderService.getById(id);
        histOrderService.removeById(historyOrder.getId());
        if (order != null) {
            orderService.removeById(order);
        }
    }

    @Override
    public List<Order> listActiveChildOrders(String parentId, String[] excludedIds){
        return orderService.listActiveChildOrders(parentId,excludedIds);
    }
}
