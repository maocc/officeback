package cn.feezu.maint.authority.manager;

import java.util.List;
import java.util.Optional;

import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;

public interface GroupManager {

	int save(Group group);

	int update(Group group);

	Group selectByPrimaryKey(Long id);

	List<Group> selectByExample(GroupExample example);

	Optional<List<Group>> findByCompanyId(String companyId);
}
