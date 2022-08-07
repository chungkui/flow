package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.snaker.engine.entity.po.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snaker.engine.entity.po.WorkItem;
import org.snaker.engine.entity.vo.WorkItemFilter;

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

    List<Task> listActiveTasks(String orderId, String[] excludedIds,String[] activeNodes);

    IPage<WorkItem> listWorkItems(IPage<WorkItem> ipage,WorkItemFilter filter);
    IPage<WorkItem> listHistoryWorkItems(IPage<WorkItem> iPage,@Param(value = "filter")
            WorkItemFilter filter);
}
