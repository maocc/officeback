package cn.feezu.maint.authority.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.entity.MemberExample;

public interface MemberManager {

	public boolean login(Member user, String password);

	public Optional<Member> findByUserName(String userName);

	public boolean changePassword(Member member);

	public boolean update(Member member);
	
	boolean save(Member member);
	
	boolean changeStatus(Member member);
	
	List<Member> selectByExample(MemberExample example);
	
	Member selectByPrimaryKey(Long id);
	
	Page<Member> findByExample(MemberExample example);
}
