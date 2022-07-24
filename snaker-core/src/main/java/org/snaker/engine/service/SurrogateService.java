package org.snaker.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.snaker.engine.entity.po.Surrogate;

import java.util.List;

/**
 * <p>
 * 委托代理表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface SurrogateService extends IService<Surrogate> {
    List<Surrogate> list(String operator, String processName, String start, String end);
}
