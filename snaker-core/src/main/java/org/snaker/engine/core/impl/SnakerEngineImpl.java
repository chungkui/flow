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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.*;
import org.snaker.engine.entity.po.Order;
import org.snaker.engine.entity.po.Process;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本的流程引擎实现类
 *
 * @author yuqs
 * @since 1.0
 */
@Service
public class SnakerEngineImpl implements SnakerEngine {
    private static final Logger log = LoggerFactory.getLogger(SnakerEngineImpl.class);
    @Autowired
    private List<SnakerInterceptor> interceptors;
    /**
     * 流程定义业务类
     */
    @Autowired
    protected ProcessFlowService processFlowService;
    /**
     * 流程实例业务类
     */
    @Autowired
    protected OrderFlowService orderFlowService;

    public List<SnakerInterceptor> getInterceptors() {
        return interceptors;
    }

    /**
     * 任务业务类
     */
    @Autowired
    protected TaskFlowService taskFlowService;

    /**
     * 管理业务类
     */
    @Autowired
    protected ManagerFlowService managerFlowService;

    /**
     * 获取流程定义服务
     */
    public ProcessFlowService process() {
        AssertHelper.notNull(processFlowService);
        return processFlowService;
    }


    /**
     * 获取实例服务
     *
     * @since 1.2.2
     */
    public OrderFlowService order() {
        AssertHelper.notNull(orderFlowService);
        return orderFlowService;
    }

    /**
     * 获取任务服务
     *
     * @since 1.2.2
     */
    public TaskFlowService task() {
        AssertHelper.notNull(taskFlowService);
        return taskFlowService;
    }

    /**
     * 获取管理服务
     *
     * @since 1.4
     */
    public ManagerFlowService manager() {
        AssertHelper.notNull(managerFlowService);
        return managerFlowService;
    }

    /**
     * 根据流程定义ID启动流程实例
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceById(String id) {
        return startInstanceById(id, null, null);
    }

    /**
     * 根据流程定义ID，操作人ID启动流程实例
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceById(String id, String operator) {
        return startInstanceById(id, operator, null);
    }

    /**
     * 根据流程定义ID，操作人ID，参数列表启动流程实例
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceById(String id, String operator, Map<String, Object> args) {
        if (args == null) args = new HashMap<String, Object>();
        Process process = process().getProcessById(id);
        process().check(process, id);
        return startProcess(process, operator, args);
    }

    /**
     * 根据流程名称启动流程实例
     *
     * @since 1.3
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceByName(String name) {
        return startInstanceByName(name, null, null, null);
    }

    /**
     * 根据流程名称、版本号启动流程实例
     *
     * @since 1.3
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceByName(String name, Integer version) {
        return startInstanceByName(name, version, null, null);
    }

    /**
     * 根据流程名称、版本号、操作人启动流程实例
     *
     * @since 1.3
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceByName(String name, Integer version,
                                     String operator) {
        return startInstanceByName(name, version, operator, null);
    }

    /**
     * 根据流程名称、版本号、操作人、参数列表启动流程实例
     *
     * @since 1.3
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceByName(String name, Integer version,
                                     String operator, Map<String, Object> args) {
        if (args == null) args = new HashMap<String, Object>();
        Process process = process().getProcessByVersion(name, version);
        process().check(process, name);
        return startProcess(process, operator, args);
    }
    private Order startProcess(Process process, String operator, Map<String, Object> args) {
        Execution execution = execute(process, operator, args, null, null);
        if (process.getModel() != null) {
            StartModel start = process.getModel().getStart();
            AssertHelper.notNull(start, "流程定义[name=" + process.getName() + ", version=" + process.getVersion() + "]没有开始节点");
            start.execute(execution);
        }

        return execution.getOrder();
    }

    /**
     * 根据父执行对象启动子流程实例（用于启动子流程）
     */
    @Transactional(rollbackFor = Exception.class)
    public Order startInstanceByExecution(Execution execution) {
        Process process = execution.getProcess();
        StartModel start = process.getModel().getStart();
        AssertHelper.notNull(start, "流程定义[id=" + process.getId() + "]没有开始节点");

        Execution current = execute(process, execution.getOperator(), execution.getArgs(),
                execution.getParentOrder().getId(), execution.getParentNodeName());
        start.execute(current);
        return current.getOrder();
    }

    /**
     * 创建流程实例，并返回执行对象
     *
     * @param process        流程定义
     * @param operator       操作人
     * @param args           参数列表
     * @param parentId       父流程实例id
     * @param parentNodeName 启动子流程的父流程节点名称
     * @return Execution
     */
    private Execution execute(Process process, String operator, Map<String, Object> args,
                              String parentId, String parentNodeName) {
        Order order = order().createOrder(process, operator, args, parentId, parentNodeName);
        if (log.isDebugEnabled()) {
            log.debug("创建流程实例对象:" + order);
        }
        Execution current = new Execution(this, process, order, args);
        current.setOperator(operator);
        return current;
    }

    /**
     * 根据任务主键ID执行任务
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Task> executeTask(String taskId) {
        return executeTask(taskId, null);
    }

    /**
     * 根据任务主键ID，操作人ID执行任务
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Task> executeTask(String taskId, String operator) {
        return executeTask(taskId, operator, null);
    }

    /**
     * 根据任务主键ID，操作人ID，参数列表执行任务
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Task> executeTask(String taskId, String operator, Map<String, Object> args) {
        //完成任务，并且构造执行对象
        Execution execution = execute(taskId, operator, args);
        if (execution == null) return Collections.emptyList();
        ProcessModel model = execution.getProcess().getModel();
        if (model != null) {
            NodeModel nodeModel = model.getNode(execution.getTask().getTaskName());
            //将执行对象交给该任务对应的节点模型执行
            nodeModel.execute(execution);
        }
        return execution.getTasks();
    }

    /**
     * 根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点
     * 1、nodeName为null时，则驳回至上一步处理
     * 2、nodeName不为null时，则任意跳转，即动态创建转移
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName) {
        Execution execution = execute(taskId, operator, args);
        if (execution == null) return Collections.emptyList();
        ProcessModel model = execution.getProcess().getModel();
        AssertHelper.notNull(model, "当前任务未找到流程定义模型");
        if (StringHelper.isEmpty(nodeName)) {
            Task newTask = task().rejectTask(model, execution.getTask());
            execution.addTask(newTask);
        } else {
            NodeModel nodeModel = model.getNode(nodeName);
            AssertHelper.notNull(nodeModel, "根据节点名称[" + nodeName + "]无法找到节点模型");
            //动态创建转移对象，由转移对象执行execution实例
            TransitionModel tm = new TransitionModel();
            tm.setTarget(nodeModel);
            tm.setEnabled(true);
            tm.execute(execution);
        }

        return execution.getTasks();
    }

    /**
     * 根据流程实例ID，操作人ID，参数列表按照节点模型model创建新的自由任务
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Task> createFreeTask(String orderId, String operator, Map<String, Object> args, TaskModel model) {
        Order order = orderFlowService.getOrderById(orderId);
        AssertHelper.notNull(order, "指定的流程实例[id=" + orderId + "]已完成或不存在");
        order.setLastUpdator(operator);
        order.setLastUpdateTime(DateHelper.getTime());
        Process process = process().getProcessById(order.getProcessId());
        Execution execution = new Execution(this, process, order, args);
        execution.setOperator(operator);
        return task().createTask(model, execution);
    }

    /**
     * 根据任务主键ID，操作人ID，参数列表完成任务，并且构造执行对象
     *
     * @param taskId   任务id
     * @param operator 操作人
     * @param args     参数列表
     * @return Execution
     */
    private Execution execute(String taskId, String operator, Map<String, Object> args) {
        if (args == null) args = new HashMap<String, Object>();
        Task task = task().complete(taskId, operator, args);
        if (log.isDebugEnabled()) {
            log.debug("任务[taskId=" + taskId + "]已完成");
        }
        Order order = orderFlowService.getOrderById(task.getOrderId());
        AssertHelper.notNull(order, "指定的流程实例[id=" + task.getOrderId() + "]已完成或不存在");
        order.setLastUpdator(operator);
        order.setLastUpdateTime(DateHelper.getTime());
        order().updateOrder(order);
        //协办任务完成不产生执行对象
        if (!task.isMajor()) {
            return null;
        }
        Map<String, Object> orderMaps = JsonHelper.fromJson(order.getVariable(), Map.class);
        if (orderMaps != null) {
            for (Map.Entry<String, Object> entry : orderMaps.entrySet()) {
                if (args.containsKey(entry.getKey())) {
                    continue;
                }
                args.put(entry.getKey(), entry.getValue());
            }
        }
        Process process = process().getProcessById(order.getProcessId());
        Execution execution = new Execution(this, process, order, args);
        execution.setOperator(operator);
        execution.setTask(task);
        return execution;
    }
}
