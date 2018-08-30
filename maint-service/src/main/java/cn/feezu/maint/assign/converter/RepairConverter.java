package cn.feezu.maint.assign.converter;

import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;

@Component
public class RepairConverter {
	@Autowired
	private MemberManager memberManager;

	public Repair convertToEntity(Repair entity) {
		if (entity == null) {
			return null;
		}

		entity.setImageList(entity.getImages().split(":"));
		entity.setImages(null);
		if (entity.getCreateTime() != null) {
			entity.setCreateTimeString(DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm"));
		}

		if (entity.getReportUserId() != null) {
			Member member = memberManager.selectByPrimaryKey(entity.getReportUserId());
			entity.setReportUserName(member.getName());
		}

		if (entity.getStatus() != null) {
			Map<Short, String> titles = Constants.Repair.titles;
			if (titles.containsKey(entity.getStatus())) {

				entity.setStatusName(titles.get(entity.getStatus()));
			}
		}

		return entity;
	}

}
