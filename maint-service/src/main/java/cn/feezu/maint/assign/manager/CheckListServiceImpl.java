package cn.feezu.maint.assign.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.CheckList;
import cn.feezu.maint.assign.entity.CheckListExample;
import cn.feezu.maint.assign.entity.SettingContent;
import cn.feezu.maint.assign.mapper.CheckListMapper;
import cn.feezu.maint.util.DictUtils;

@Service
public class CheckListServiceImpl implements CheckListService {

	@Autowired
	private CheckListMapper checkListMapper;

	@Override
	public Map<String, Map<String, SettingContent>> findByOrderIdAndOpId(Long orderId, Long opId) {
		CheckListExample example = new CheckListExample();
		example.createCriteria().andOrderIdEqualTo(orderId).andOrderOperateIdEqualTo(opId);
		List<CheckList> entities = checkListMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(entities)) {
			return null;
		}

		Map<String, Map<String, SettingContent>> result = new HashMap<>();

		entities.forEach(entity -> {

			String checkContent = entity.getCheckContent();
			@SuppressWarnings("unchecked")
			Map<String, String> content = JSON.parseObject(checkContent, Map.class);

			
			Map<String, SettingContent> keyAndValue= new HashMap<>();
			
			if(Constants.CheckList.VEHICLE_CONDITION.equals(entity.getCheckType())
					||Constants.CheckList.CLEAR.equals(entity.getCheckType())
					||Constants.CheckList.CHARGING_CONDITION.equals(entity.getCheckType())){
				
				DictUtils.getCode(entity.getCheckType()).forEach((k,v)->{
					if(Constants.CheckList.CLEAR.equals(entity.getCheckType())){
						keyAndValue.put(k, new SettingContent(v,"1".equals(content.get(k))?"已清洁":"未清洁"));
					}else if(Constants.CheckList.VEHICLE_CONDITION.equals(entity.getCheckType())){
						keyAndValue.put(k, new SettingContent(v,"1".equals(content.get(k))?"已检查":"未检查"));
					}else{
						keyAndValue.put(k, new SettingContent(v,content.get(k)));
					}
					
				});
			}else if(Constants.CheckList.EVALUATE.equals(entity.getCheckType())){
				//评价
				keyAndValue.put("star", new SettingContent("评价分值",content.get("star")));
				keyAndValue.put("content", new SettingContent("评价内容",content.get("content")));
			}
			
			if (!Constants.CheckList.EVALUATE.equals(entity.getCheckType())) {// 不是评价

				keyAndValue.put("images", new SettingContent("照片", entity.getImages()==null?null:entity.getImages().split(":")));
			}
			
			result.put(entity.getCheckType(), keyAndValue);
		});
		return result;
	}

	@Override
	public CheckList save(CheckList record) {
		boolean result = checkListMapper.insertSelective(record) == 1;
		if (result) {
			return record;
		}
		return null;
	}

	@Override
	public List<CheckList> save(List<CheckList> entities) {
		boolean result = true;

		for (CheckList record : entities) {
			record = save(record);
			if (record == null) {
				result = false;
			}
		}
		if (result) {
			return entities;
		}
		return null;
	}

}
