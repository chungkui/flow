package org.snaker.engine.service;

import org.snaker.engine.entity.po.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface TaskService extends IService<Task> {
    List<Task> listByOrderId(String orderId);

    List<Task> getNextActiveTasks(String id);
    List<Task> getNextActiveTasks(String id,String name,String pid);

    List<Task> getActiveTasks(String id, String[] strings,String[] activeNodes);
}
