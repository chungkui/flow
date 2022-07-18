package org.snaker.engine.service;

import org.snaker.engine.entity.po.HistOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 历史流程实例表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface HistOrderService extends IService<HistOrder> {

    List<HistOrder>listByProcessId(String processId);
}
