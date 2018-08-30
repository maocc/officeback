package cn.feezu.maint.authority.manager;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;
import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.entity.YwMaintArea;
import cn.feezu.maint.authority.entity.YwMaintAreaExample;
import cn.feezu.maint.authority.entity.YwMaintAreaExample.Criteria;
import cn.feezu.maint.authority.mapper.YwMaintAreaMapper;

/**
 * 区域管理
 * @author maocc
 * @description
 * @date: 2018/4/4 10:20
 */
@Service
public class MaintAreaManagerImpl implements MaintAreaManager {

	@Autowired
	private YwMaintAreaMapper maintAreaMapper;

	@Autowired
	private YwAreaGraphManager ywAreaGraphManager;
	@Autowired
	private GroupManager groupManager;
	@Override
	public boolean save(YwMaintArea maintArea) {
		maintArea.setId(IDUtils.getId());
		maintArea.setOperateTime(new Date());
		int result = maintAreaMapper.insert(maintArea);
		return result > 0;
	}

	@Override
	public boolean update(YwMaintArea maintArea) {
		YwMaintAreaExample maintAreaExample = new YwMaintAreaExample();
		YwMaintAreaExample.Criteria criteria = maintAreaExample.createCriteria();
		criteria.andIdEqualTo(maintArea.getId());

		maintArea.setOperateTime(new Date());
		maintAreaMapper.updateByExample(maintArea, maintAreaExample);
		return true;
	}

	@Override
	public boolean deleteYwMaintArea(Long maintAreaId) {
		maintAreaMapper.deleteByPrimaryKey(maintAreaId);
		//删除区域下的图形
		YwAreaGraph ywAreaGraph = new YwAreaGraph();
		ywAreaGraph.setAreaId(maintAreaId);
		ywAreaGraphManager.deleteYwAreaGraph(ywAreaGraph);
		return true;
	}

	@Override
	public List<YwMaintArea> findYwMaintAreaList(YwMaintArea maintArea) {
		YwMaintAreaExample maintAreaExample = new YwMaintAreaExample();
		YwMaintAreaExample.Criteria criteria = maintAreaExample.createCriteria();
		if (maintArea.getId() != null) {
			criteria.andIdEqualTo(maintArea.getId());
		}
		if (!StringUtils.isEmpty(maintArea.getName())) {
			criteria.andNameLike(maintArea.getName());
		}
		if (maintArea.getGroupId() != null) {
			criteria.andGroupIdEqualTo(maintArea.getGroupId());
		}
		List<YwMaintArea> result=maintAreaMapper.selectByExample(maintAreaExample);
		
		result.forEach(entity->{
			List<YwAreaGraph> graphs = ywAreaGraphManager.findByAreaId(entity.getId());
			if(!CollectionUtils.isEmpty(graphs)){
				entity.setGraph(graphs.get(0));
			}
		});
		
		return result;
	}
	@Override
	public boolean exists(String areaName,List<Long> getGroupIds){
		YwMaintAreaExample example = new YwMaintAreaExample();
		Criteria c = example.createCriteria().andNameEqualTo(areaName);
		if(getGroupIds!=null){
			c.andGroupIdIn(getGroupIds);
		}
		List<YwMaintArea> result= maintAreaMapper.selectByExample(example);
		return !CollectionUtils.isEmpty(result);
	}
	@Override
	public List<YwMaintArea> findByGroupId(Long groupId) {
		YwMaintAreaExample example = new YwMaintAreaExample();
		example.createCriteria().andGroupIdEqualTo(groupId);
		return maintAreaMapper.selectByExample(example);
	}
	
	@Override
	public Optional<YwMaintArea> findById(Long id) {
		
		YwMaintArea area = maintAreaMapper.selectByPrimaryKey(id);
		
		List<YwAreaGraph> graphs = ywAreaGraphManager.findByAreaId( area.getId());
		if(!CollectionUtils.isEmpty(graphs)){
			area.setGraph(graphs.get(0));
		}
		return Optional.of(area);
	}
	@Override
	@Transactional
	public boolean deleteById(Long id){
		maintAreaMapper.deleteByPrimaryKey(id);
		
		return ywAreaGraphManager.deleteByAreaId(id);
	}

	@Override
	public List<YwMaintArea> findByCompanyId(String companyId, String name) {
		List<Long> groupIds =getGroupIds(companyId);
		return findByGroupIds(groupIds,name);
	}

	@Override
	public List<YwMaintArea> findByGroupIds(List<Long> groupIds, String name) {
		 
		YwMaintAreaExample example = new YwMaintAreaExample();
		Criteria  c = example.createCriteria();
		c.andGroupIdIn(groupIds);
		if(name!=null){
			c.andNameEqualTo(name);
		}
		List<YwMaintArea> result= maintAreaMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		result.forEach(entity->{
			List<YwAreaGraph> graphs = ywAreaGraphManager.findByAreaId(entity.getId());
			if(!CollectionUtils.isEmpty(graphs)){
				entity.setGraph(graphs.get(0));
			}
		});
		return result;
	}
	
	private List<Long> getGroupIds(String companyId){
		GroupExample example =new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(companyId);
		return groupManager.selectByExample(example).stream().map(Group::getId).collect(Collectors.toList());
	}
}
