package org.snaker.engine.service;

import org.snaker.engine.entity.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程实例表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface OrderService extends IService<Order> {
    List<Order> listActiveChildOrders(String parentId, String[] excludedIds);
}
