package cn.feezu.maint.authority.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import cn.feezu.common.util.Constants;
import cn.feezu.common.util.PasswordEncoder;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.entity.MemberExample;
import cn.feezu.maint.authority.mapper.MemberMapper;

@Service
public class MemberManagerImpl implements MemberManager{

	@Autowired
	private MemberMapper mapper;
	@Override
	public boolean login(Member user, String password) {
		return PasswordEncoder.encodePassword(password, user.getId()).equals(user.getPassword());
	}
	
	public static void main(String[] args) {
		System.out.println(PasswordEncoder.encodePassword("123456", "2"));
	}
	@Override
	public Optional<Member> findByUserName(String userName) {

		MemberExample example = new MemberExample();
		example.createCriteria().andMobileEqualTo(userName).andStatusNotEqualTo(Constants.Member.DELETE);
		List<Member> entities = mapper.selectByExample(example);

		if (entities == null || entities.isEmpty()) {
			return Optional.empty();
		}
		Member member = entities.get(0);
		
		return Optional.of(member);
	}
	@Override
	public boolean changePassword(Member member) {

		return mapper.updateByPrimaryKeySelective(member) == 1;
	}
	@Override
	public boolean update(Member member) {

		return mapper.updateByPrimaryKeySelective(member) == 1;
	}
	
	@Override
	public boolean save(Member member) {
		return mapper.insert(member) == 1;
	}
	
	@Override
	public boolean changeStatus(Member member) {
		return mapper.updateByPrimaryKeySelective(member) == 1;
	}
	
	@Override
	public List<Member> selectByExample(MemberExample example) {
		return mapper.selectByExample(example);
	}
	
	@Override
	public Member selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Page<Member> findByExample(MemberExample example) {
		return new PageImpl<>(mapper.selectByExample(example), example.getPageable(), mapper.countByExample(example));
	}
}
