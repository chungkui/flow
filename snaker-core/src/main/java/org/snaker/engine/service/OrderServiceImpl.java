package org.snaker.engine.service;

import org.snaker.engine.entity.po.Order;
import org.snaker.engine.mapper.OrderMapper;
import org.snaker.engine.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 流程实例表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    public List<Order> getActiveOrders(String id, String[] strings) {
        return this.baseMapper.getActiveOrders(id,strings);
    }
}
