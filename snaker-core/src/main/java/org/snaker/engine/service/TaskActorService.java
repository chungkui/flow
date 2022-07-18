package org.snaker.engine.service;

import org.snaker.engine.entity.po.TaskActor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 任务参与者表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface TaskActorService extends IService<TaskActor> {

    List<TaskActor> getTaskActorsByTaskId(String id);

    void removeTaskActor(String id, String[] actors);
}
