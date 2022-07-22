package org.snaker.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.CcOrder;
import org.snaker.engine.mapper.CcOrderMapper;
import org.snaker.engine.service.CcOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 抄送实例表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class CcOrderServiceImpl extends ServiceImpl<CcOrderMapper, CcOrder> implements CcOrderService {

    public List<CcOrder> list(String orderId, String... actorIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        queryWrapper.in(actorIds != null, "actor_id", actorIds);
        return this.list(queryWrapper);
    }
}
