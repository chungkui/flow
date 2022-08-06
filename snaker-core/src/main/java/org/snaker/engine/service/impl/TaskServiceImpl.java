package org.snaker.engine.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.entity.po.WorkItem;
import org.snaker.engine.entity.vo.WorkItemFilter;
import org.snaker.engine.mapper.TaskMapper;
import org.snaker.engine.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    public List<Task> listByOrderId(String orderId) {
        return null;
    }

    public List<Task> getNextActiveTasks(String id) {
        return null;
    }

    public List<Task> getNextActiveTasks(String id, String name, String pid) {
        return null;
    }

    @Override
    public List<Task> listActiveTasks(String id, String[] excludedIds, String[] activeNodes) {
        return this.baseMapper.listActiveTasks(id,excludedIds,activeNodes);
    }

    @Override
    public IPage<WorkItem> listWorkItems(IPage<WorkItem> iPage,WorkItemFilter filter) {
        return this.baseMapper.listWorkItems(iPage,filter);
    }
}
