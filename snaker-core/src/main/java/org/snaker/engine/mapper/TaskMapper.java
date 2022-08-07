package org.snaker.engine.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.snaker.engine.entity.po.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.snaker.engine.entity.po.WorkItem;
import org.snaker.engine.entity.vo.WorkItemFilter;

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
    IPage<WorkItem> listWorkItems(IPage<WorkItem> iPage,@Param(value = "filter")
            WorkItemFilter filter);
    List<Task> listActiveTasks(@Param(value = "orderId") String orderId,
                               @Param(value = "excludedIds")String[] excludedIds,
                               @Param(value = "names")  String[] names);

    IPage<WorkItem> listHistoryWorkItems(IPage<WorkItem> iPage,@Param(value = "filter")
            WorkItemFilter filter);
}
