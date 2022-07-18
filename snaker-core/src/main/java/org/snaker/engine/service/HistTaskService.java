package org.snaker.engine.service;

import org.snaker.engine.entity.po.HistTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 历史任务表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface HistTaskService extends IService<HistTask> {

    List<HistTask> listByOrderId(String orderId);
}
