package org.snaker.engine.service;

import org.snaker.engine.entity.po.Process;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 流程定义表 服务类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface ProcessService extends IService<Process> {
    /**
     * 通过名字获取
     * @param name /
     * @return /
     */
    Integer getLatestProcessVersion(String name);

    List<Process> getProcesss(String name, Integer version);
}
