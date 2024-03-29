/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.core.OrderFlowService;
import org.snaker.engine.core.ProcessFlowService;
import org.snaker.engine.SnakerException;
import org.snaker.engine.cache.Cache;
import org.snaker.engine.cache.CacheManager;
import org.snaker.engine.cache.CacheManagerAware;
import org.snaker.engine.cost.FlowState;
import org.snaker.engine.entity.po.HistOrder;
import org.snaker.engine.entity.po.Process;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.parser.ModelParser;
import org.snaker.engine.service.HistOrderService;
import org.snaker.engine.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * 流程定义业务类
 * @author yuqs
 * @since 1.0
 */
@Service
public class ProcessFlowServiceImpl implements ProcessFlowService,
		CacheManagerAware {
	private static final Logger log = LoggerFactory.getLogger(ProcessFlowServiceImpl.class);
	private static final String DEFAULT_SEPARATOR = ".";
	/**
	 * 流程定义对象cache名称
	 */
	private static final String CACHE_ENTITY = "snaker.process.entity";
	/**
	 * 流程id、name的cache名称
	 */
	private static final String CACHE_NAME = "snaker.process.name";
	/**
	 * cache manager
	 */
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private ProcessService processService;
	@Autowired
	private HistOrderService histOrderService;
	@Autowired
	private OrderFlowService orderFlowService;
	@Autowired
	ModelParser modelParser;
	/**
	 * 实体cache(key=name,value=entity对象)
	 */
	private Cache<String, Process> entityCache;
	/**
	 * 名称cache(key=id,value=name对象)
	 */
	private Cache<String, String> nameCache;

	public void check(Process process, String idOrName) {
		AssertHelper.notNull(process, "指定的流程定义[id/name=" + idOrName + "]不存在");
		if(process.getState() != null && process.getState()!=FlowState.STATE_ACTIVE.getState()) {
			throw new IllegalArgumentException("指定的流程定义[id/name=" + idOrName +
					",version=" + process.getVersion() + "]为非活动状态");
		}
	}

	/**
	 * 保存process实体对象
	 */
	public void saveProcess(Process process) {
		processService.save(process);

	}

	/**
	 * 更新process的类别
	 */
	public void updateType(String id, String type) {
        Process entity = getProcessById(id);
        entity.setType(type);
		processService.updateById(entity);
        cache(entity);
	}

	/**
	 * 根据id获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessById(String id) {
		AssertHelper.notEmpty(id);
		Process entity = null;
		String processName;
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if(nameCache != null && entityCache != null) {
			processName = nameCache.get(id);
			if(StringHelper.isNotEmpty(processName)) {
				entity = entityCache.get(processName);
			}
		}
		if(entity != null) {
			if(log.isDebugEnabled()) {
				log.debug("obtain process[id={}] from cache.", id);
			}
			return entity;
		}
		entity = processService.getById(id);
		if(entity != null) {
			if(log.isDebugEnabled()) {
				log.debug("obtain process[id={}] from database.", id);
			}
			cache(entity);
		}
		return entity;
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessByName(String name) {
		return getProcessByVersion(name, null);
	}

	/**
	 * 根据name获取process对象
	 * 先通过cache获取，如果返回空，就从数据库读取并put
	 */
	public Process getProcessByVersion(String name, Integer version) {
		AssertHelper.notEmpty(name);
		if(version == null) {
			version =processService.getLatestProcessVersion(name);
		}
		if(version == null) {
			version = 0;
		}
		Process entity = null;
		String processName = name + DEFAULT_SEPARATOR + version;
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		if(entityCache != null) {
			entity = entityCache.get(processName);
		}
		if(entity != null) {
			if(log.isDebugEnabled()) {
				log.debug("obtain process[name={}] from cache.", processName);
			}
			return entity;
		}

		List<Process> processs = processService.getProcesss(name,version);
		if(processs != null && !processs.isEmpty()) {
			if(log.isDebugEnabled()) {
				log.debug("obtain process[name={}] from database.", processName);
			}
			entity = processs.get(0);
			cache(entity);
		}
		return entity;
	}

	/**
	 * 根据流程定义xml的输入流解析为字节数组，保存至数据库中，并且put到缓存中
	 * @param input 定义输入流
	 */
	public String deploy(InputStream input) {
		return deploy(input, null);
	}

	/**
	 * 根据流程定义xml的输入流解析为字节数组，保存至数据库中，并且put到缓存中
	 * @param input 定义输入流
	 * @param creator 创建人
	 */
	public String deploy(InputStream input, String creator) {
		AssertHelper.notNull(input);
		try {
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = modelParser.parse(bytes);
			Integer version =processService.getLatestProcessVersion(model.getName());
			Process entity = new Process();
			entity.setId(StringHelper.getPrimaryKey());
			if(version == null || version < 0) {
				entity.setVersion(0);
			} else {
				entity.setVersion(version + 1);
			}
			entity.setState(FlowState.STATE_ACTIVE.getState());

			entity.setName(model.getName());
			entity.setDisplayName(model.getDisplayName());
			entity.setInstanceUrl(model.getInstanceUrl());

			entity.setContent(bytes);
			entity.setCreateTime(DateHelper.getTime());
			entity.setCreator(creator);
			saveProcess(entity);
			cache(entity);
			return entity.getId();
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据流程定义id、xml的输入流解析为字节数组，保存至数据库中，并且重新put到缓存中
	 * @param input 定义输入流
	 */
	public void redeploy(String id, InputStream input) {
		AssertHelper.notNull(input);
		Process entity = processService.getById(id);
		AssertHelper.notNull(entity);
		try {
			byte[] bytes = StreamHelper.readBytes(input);
			ProcessModel model = modelParser.parse(bytes);
			String oldProcessName = entity.getName();
			entity.setName(model.getName());
			entity.setDisplayName(model.getDisplayName());
			entity.setInstanceUrl(model.getInstanceUrl());
			entity.setContent(bytes);
			processService.updateById(entity);
			if(!oldProcessName.equalsIgnoreCase(entity.getName())) {
				Cache<String, Process> entityCache = ensureAvailableEntityCache();
				if(entityCache != null) {
					entityCache.remove(oldProcessName + DEFAULT_SEPARATOR + entity.getVersion());
				}
			}
			cache(entity);
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SnakerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 根据processId卸载流程
	 */
	public void undeploy(String id) {
		Process entity = processService.getById(id);
		entity.setState(FlowState.STATE_FINISH.getState());
		processService.updateById(entity);
		cache(entity);
	}

	/**
	 * 级联删除指定流程定义的所有数据
	 */
	public void cascadeRemove(String id) {
		Process entity = processService.getById(id);
		List<HistOrder> historyOrders = histOrderService.listByProcessId(id);
		for(HistOrder historyOrder : historyOrders) {
			//对流程实例的相关对象进行删除
			orderFlowService.cascadeRemove(historyOrder.getId());
		}
		processService.removeById(id);
		clear(entity);
	}

	@Override
	public Integer getMaxVersion(String displayName) {
		Process process = processService.getOne(
				new LambdaQueryWrapper<Process>().eq(Process::getDisplayName,displayName).orderByDesc(Process::getVersion).last("limit 1"));
		if (process == null) {
			return null;
		}
		return process.getVersion();
	}

	/**
	 * 缓存实体
	 * @param entity 流程定义对象
	 */
	private void cache(Process entity) {
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		 if(entity.getModel() == null && entity.getContent() != null) {
			entity.setModel(modelParser.parse(entity.getContent()));
		}
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if(nameCache != null && entityCache != null) {
			if(log.isDebugEnabled()) {
				log.debug("cache process id is[{}],name is[{}]", entity.getId(), processName);
			}
			entityCache.put(processName, entity);
			nameCache.put(entity.getId(), processName);
		} else {
			if(log.isDebugEnabled()) {
				log.debug("no cache implementation class");
			}
		}
	}

	/**
	 * 清除实体
	 * @param entity 流程定义对象
	 */
	private void clear(Process entity) {
		Cache<String, String> nameCache = ensureAvailableNameCache();
		Cache<String, Process> entityCache = ensureAvailableEntityCache();
		String processName = entity.getName() + DEFAULT_SEPARATOR + entity.getVersion();
		if(nameCache != null && entityCache != null) {
			nameCache.remove(entity.getId());
			entityCache.remove(processName);
		}
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

    private Cache<String, Process> ensureAvailableEntityCache() {
        Cache<String, Process> entityCache = ensureEntityCache();
		if(entityCache == null && this.cacheManager != null) {
			entityCache = this.cacheManager.getCache(CACHE_ENTITY);
		}
        return entityCache;
    }

    private Cache<String, String> ensureAvailableNameCache() {
        Cache<String, String> nameCache = ensureNameCache();
		if(nameCache == null && this.cacheManager != null) {
			nameCache = this.cacheManager.getCache(CACHE_NAME);
		}
        return nameCache;
    }

	public Cache<String, Process> ensureEntityCache() {
		return entityCache;
	}

	public void setEntityCache(Cache<String, Process> entityCache) {
		this.entityCache = entityCache;
	}

	public Cache<String, String> ensureNameCache() {
		return nameCache;
	}

	public void setNameCache(Cache<String, String> nameCache) {
		this.nameCache = nameCache;
	}
}
