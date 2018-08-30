package cn.feezu.maint.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.feezu.common.util.BeanUtil;
import cn.feezu.common.util.Constants;
import cn.feezu.maint.entity.Dict;
import cn.feezu.maint.entity.DictExample;
import cn.feezu.maint.mapper.DictMapper;
@Service
public class DictManagerImpl implements DictManager {
	@Autowired
	private DictMapper dictMapper;


	@Override
	public Map<String, String> getDictByType(String classfyType) {
	 
		return getDictMapByType(classfyType);
	}

	@Override
	@Cacheable(value = "dictCache", key = "'getDictListByType'+#classfyType")
	public List<Dict> getDictListByType(String classfyType) {
		DictExample example =new DictExample();
		example.createCriteria().andDictTypeEqualTo(classfyType);
		return dictMapper.selectByExample(example);
	}

	@Override
	@Cacheable(value = "dictCache", key = "'getDictMapByType'+#classfyType")
	public Map<String, String> getDictMapByType(String classfyType) {
        return BeanUtil.convert(getDictListByType(classfyType), "dictCode", "dictName",
                String.class, String.class);

	}

	@Override
	@Cacheable(value = "dictCache", key = "'getDictByTypes'+#types")
	public Map<String, Map<String, String>> getDictByTypes(String... types) {
   	 Assert.notNull(types, "类型不能为空!");
     Assert.notEmpty(types, "类型不能为空!");
	Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
	 for(String str:types){
		 map.put(str, new HashMap<String, String>());
     }
	
	List<Dict> dictList= getDictListByType(types);
	dictList.forEach(dict -> map.get(dict.getDictType()).put(dict.getDictCode(), dict.getDictName()));

	return map;

	}
    private List<Dict> getDictListByType(String ...types) {
    	
        Assert.notNull(types, "类型不能为空!");
        Assert.notEmpty(types, "类型不能为空!");
        DictExample example =new DictExample();
		example.createCriteria().andDictTypeIn(Arrays.asList(types))
		.andDictStatusEqualTo(Constants.DictStatus.ENABLE);
		return dictMapper.selectByExample(example);

    }

	@Override
	public Dict findByDictCode(String dictCode) {
		DictExample example =new DictExample();
		example.createCriteria().andDictCodeEqualTo(dictCode);
		List<Dict> result = dictMapper.selectByExample(example);
		if(result==null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}

	@Override
	public Dict findByDictType(String dictType) {
		DictExample example =new DictExample();
		example.createCriteria().andDictTypeEqualTo(dictType);
		List<Dict> result = dictMapper.selectByExample(example);
		if(result==null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}

}
