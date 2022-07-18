package org.snaker.engine.mapper;

import org.apache.ibatis.annotations.Select;
import org.snaker.engine.entity.po.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 流程定义表 Mapper 接口
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
public interface ProcessMapper extends BaseMapper<Process> {
    @Select("SELECT max(version) FROM wf_process WHERE name = #{name}")
    Integer selectLatestProcessVersionByName(String name);
}
