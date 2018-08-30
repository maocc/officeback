package cn.feezu.maint.mapper;

import cn.feezu.maint.entity.Dict;
import cn.feezu.maint.entity.DictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictMapper {
    int countByExample(DictExample example);

    int deleteByExample(DictExample example);

    int deleteByPrimaryKey(Long dictId);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectOnlyOneByExample(DictExample example);

    List<Dict> selectByExample(DictExample example);

    Dict selectByPrimaryKey(Long dictId);

    int updateByExampleSelective(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByExample(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}