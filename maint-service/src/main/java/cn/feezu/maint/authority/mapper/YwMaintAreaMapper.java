package cn.feezu.maint.authority.mapper;

import cn.feezu.maint.authority.entity.YwMaintArea;
import cn.feezu.maint.authority.entity.YwMaintAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YwMaintAreaMapper {
    long countByExample(YwMaintAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YwMaintArea record);

    int insertSelective(YwMaintArea record);

    List<YwMaintArea> selectByExample(YwMaintAreaExample example);

    YwMaintArea selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YwMaintArea record, @Param("example") YwMaintAreaExample example);

    int updateByExample(@Param("record") YwMaintArea record, @Param("example") YwMaintAreaExample example);

    int updateByPrimaryKeySelective(YwMaintArea record);

    int updateByPrimaryKey(YwMaintArea record);
}