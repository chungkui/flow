package org.snaker.engine.service;

import org.snaker.engine.entity.po.CcOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 抄送实例表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface CcOrderService extends IService<CcOrder> {
    List<CcOrder> list(String orderId, String... actorIds);

}
