package cn.feezu.maint.authority.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;
import cn.feezu.maint.authority.mapper.GroupMapper;

@Service
public class GroupManagerImpl implements GroupManager {

	@Autowired
	private GroupMapper groupMapper;
	
	@Override
	@Transactional
	public int save(Group group) {
		return groupMapper.insert(group);
	}

	@Override
	@Transactional
	public int update(Group group) {
		return groupMapper.updateByPrimaryKeySelective(group);
	}

	@Override
	public List<Group> selectByExample(GroupExample example) {
		return groupMapper.selectByExample(example);
	}
	
	public Group selectByPrimaryKey(Long id) {
		return groupMapper.selectByPrimaryKey(id);
	}

	@Override
	public Optional<List<Group>> findByCompanyId(String companyId) {
		GroupExample example =new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(companyId);
		return Optional.of(selectByExample(example));
	}

}
