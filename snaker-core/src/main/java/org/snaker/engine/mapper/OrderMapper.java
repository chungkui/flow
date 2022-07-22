package org.snaker.engine.mapper;

import org.snaker.engine.entity.po.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 流程实例表 Mapper 接口
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> listActiveChildOrders(String parentId, String[] excludedIds);
}
