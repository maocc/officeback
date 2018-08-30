package cn.feezu.maint.authority.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.entity.YwAreaGraphExample;
import cn.feezu.maint.authority.entity.YwMaintArea;
import cn.feezu.maint.authority.mapper.YwAreaGraphMapper;
import cn.feezu.maint.authority.mapper.YwMaintAreaMapper;

/**
 * 区域图形管理
 * @author maocc
 * @description
 * @date: 2018/4/4 10:33
 */
@Service
public class YwAreaGraphManagerImpl implements YwAreaGraphManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(YwAreaGraphManagerImpl.class);
	@Autowired
	private YwAreaGraphMapper ywAreaGraphMapper;
	@Autowired
	private MaintAreaManager areaManager;
	
	@Autowired
	private YwMaintAreaMapper maintAreaMapper;

	@Override
	public boolean saveYwAreaGraph(YwAreaGraph ywAreaGraph) {
		if (ywAreaGraph.getId() != null) {//存在id 就更新
			return this.updateYwAreaGraph(ywAreaGraph);
		}
		//删除区域图形
		this.deleteYwAreaGraph(ywAreaGraph);

		LOGGER.info("saveYwAreaGraph param is {}",ywAreaGraph.toString());
		ywAreaGraph.setId(IDUtils.getId());
		ywAreaGraph.setOperateTime(new Date());
		int result = ywAreaGraphMapper.insert(ywAreaGraph);
		return result > 0;
	}

	@Override
	@Transactional
	public boolean saveYwAreaGraph(String name, Long groupId,Byte graphType,String points) {
		YwMaintArea maintArea = new YwMaintArea();
		maintArea.setId(IDUtils.getId());
		maintArea.setName(name);
		maintArea.setGroupId(groupId);
		maintArea.setOperateTime(new Date());
		boolean result = maintAreaMapper.insertSelective(maintArea)==1;
		if(!result){
			return false;
		}
		//保存图形信息
		YwAreaGraph ywAreaGraph = new YwAreaGraph();
		ywAreaGraph.setAreaId(maintArea.getId());
		
		ywAreaGraph.setGraphType(graphType);
		ywAreaGraph.setId(IDUtils.getId());
		ywAreaGraph.setPoints(points);
		ywAreaGraph.setOperateTime(new Date());
		result = ywAreaGraphMapper.insert(ywAreaGraph)==1;
		return result;
				
	}
	
	@Override
	@Transactional
	public boolean updateYwAreaGraph(Long id,String name, Long groupId,Byte graphType,String points) {
		YwMaintArea maintArea = new YwMaintArea();
		maintArea.setId(id);
		maintArea.setName(name);
		maintArea.setGroupId(groupId);
		maintArea.setOperateTime(new Date());
		boolean result = maintAreaMapper.updateByPrimaryKeySelective(maintArea)==1;
		if(!result){
			return false;
		}
		
		//修改图形信息
		YwAreaGraph ywAreaGraph = new YwAreaGraph();
		ywAreaGraph.setAreaId(id);
		ywAreaGraph.setGraphType(graphType);
		ywAreaGraph.setPoints(points);
		ywAreaGraph.setOperateTime(new Date());
		result = ywAreaGraphMapper.updateByPrimaryKeySelective(ywAreaGraph)==1;
		return result;
				
	}
	@Override
	@Transactional
	public boolean deleteByAreaId(Long areaId){
		YwAreaGraphExample example = new YwAreaGraphExample();
		example.createCriteria().andAreaIdEqualTo(areaId);
		List<YwAreaGraph> graphs = ywAreaGraphMapper.selectByExample(example);
		if(graphs!=null){
			
			graphs.forEach(entity->ywAreaGraphMapper.deleteByPrimaryKey(entity.getId()));
		}
		
		return  true;
	}
	
	@Override
	@Transactional
	public boolean saveYwAreaGraph(List<YwAreaGraph> ywAreaGraphs) {
		Date now =new Date();
		boolean flag=true;
		for(YwAreaGraph entity:ywAreaGraphs){
			entity.setId(IDUtils.getId());
			entity.setOperateTime(now);
			if(ywAreaGraphMapper.insert(entity)!=1){
				flag=false;
			}
		}
		return flag;
	}
	@Override
	@Transactional
	public boolean deleteYwAreaGraphById(Long ywAreaGraphId) {
		LOGGER.info("deleteYwAreaGraphById param is {}",ywAreaGraphId);
		ywAreaGraphMapper.deleteByPrimaryKey(ywAreaGraphId);
		return true;
	}

	@Override
	public boolean deleteYwAreaGraph(YwAreaGraph ywAreaGraph) {
		List<YwAreaGraph> ywAreaGraphList = this.findYwAreaGraphList(ywAreaGraph);
		ywAreaGraphList.forEach(entity ->{
			this.deleteYwAreaGraphById(entity.getId());
		});
		return true;
	}

	@Override
	public boolean updateYwAreaGraph(YwAreaGraph ywAreaGraph) {
		LOGGER.info("updateYwAreaGraph param is {}",ywAreaGraph.toString());
		ywAreaGraph.setOperateTime(new Date());
		ywAreaGraphMapper.updateByPrimaryKey(ywAreaGraph);
		return true;
	}

	@Override
	public List<YwAreaGraph> findYwAreaGraphList(YwAreaGraph ywAreaGraph) {
		if(ywAreaGraph.getId() != null){
			return Arrays.asList(ywAreaGraphMapper.selectByPrimaryKey(ywAreaGraph.getId()));
		}
		YwAreaGraphExample ywAreaGraphExample = new YwAreaGraphExample();
		YwAreaGraphExample.Criteria criteria = ywAreaGraphExample.createCriteria();
		if(ywAreaGraph.getGraphType() != null){
			criteria.andGraphTypeEqualTo(ywAreaGraph.getGraphType());
		}
		if(ywAreaGraph.getAreaId() != null){
			criteria.andAreaIdEqualTo(ywAreaGraph.getAreaId());
		}

		return ywAreaGraphMapper.selectByExample(ywAreaGraphExample);
	}

	@Override
	public List<YwAreaGraph> findByGroupIdAndAreaId(Long groupId, Long areaId) {
		
		if(areaId==null){
			return findByGroupId(groupId);
		}
		
		YwAreaGraphExample example =new YwAreaGraphExample();
		
		 example.createCriteria().andAreaIdEqualTo(areaId);
		
		 return ywAreaGraphMapper.selectByExample(example);
		
	}
	
	
	
	private List<YwAreaGraph> findByGroupId(Long groupId){
		List<Long> areaIds = areaManager.findByGroupId(groupId)
				.stream()
				.map(YwMaintArea::getId)
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(areaIds)){
			return null;
		}
		 YwAreaGraphExample example =new YwAreaGraphExample();
		
		 example.createCriteria().andAreaIdIn(areaIds);
		
		 return ywAreaGraphMapper.selectByExample(example);
		 
	}
	
	
	@Override
	public List<YwAreaGraph> findByAreaId(Long areaId) {
		
		
		YwAreaGraphExample example =new YwAreaGraphExample();
		
		 example.createCriteria().andAreaIdEqualTo(areaId);
		
		 return ywAreaGraphMapper.selectByExample(example);
		
	}

}
