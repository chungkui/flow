package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.TaskActor;
import org.snaker.engine.mapper.TaskActorMapper;
import org.snaker.engine.service.TaskActorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务参与者表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class TaskActorServiceImpl extends ServiceImpl<TaskActorMapper, TaskActor> implements TaskActorService {

    public List<TaskActor> getTaskActorsByTaskId(String id) {
        QueryWrapper<TaskActor>queryWrapper=new QueryWrapper<TaskActor>();
        queryWrapper.eq("task_id",id);
        return this.list(queryWrapper);
    }

    public void removeTaskActor(String id, String[] actors) {

    }
}
