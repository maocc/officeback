package cn.feezu.maint.authority.mapper;

import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.entity.YwAreaGraphExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YwAreaGraphMapper {
    long countByExample(YwAreaGraphExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YwAreaGraph record);

    int insertSelective(YwAreaGraph record);

    List<YwAreaGraph> selectByExample(YwAreaGraphExample example);

    YwAreaGraph selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YwAreaGraph record, @Param("example") YwAreaGraphExample example);

    int updateByExample(@Param("record") YwAreaGraph record, @Param("example") YwAreaGraphExample example);

    int updateByPrimaryKeySelective(YwAreaGraph record);

    int updateByPrimaryKey(YwAreaGraph record);
}