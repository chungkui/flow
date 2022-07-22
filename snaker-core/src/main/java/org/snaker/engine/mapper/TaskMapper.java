package org.snaker.engine.mapper;

import org.snaker.engine.entity.po.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface TaskMapper extends BaseMapper<Task> {

    List<Task> listActiveTasks(String orderId, String[] excludedIds, String[] names);
}
