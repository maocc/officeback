package cn.feezu.maint.assign.mapper;

import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.assign.entity.RepairExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RepairMapper {
    int countByExample(RepairExample example);

    int deleteByExample(RepairExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Repair record);

    int insertSelective(Repair record);

    Repair selectOnlyOneByExample(RepairExample example);

    List<Repair> selectByExample(RepairExample example);

    Repair selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Repair record, @Param("example") RepairExample example);

    int updateByExample(@Param("record") Repair record, @Param("example") RepairExample example);

    int updateByPrimaryKeySelective(Repair record);

    int updateByPrimaryKey(Repair record);
}