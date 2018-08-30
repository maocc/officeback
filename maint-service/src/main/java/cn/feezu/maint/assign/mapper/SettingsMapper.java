package cn.feezu.maint.assign.mapper;

import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.entity.SettingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettingsMapper {
    int countByExample(SettingsExample example);

    int deleteByExample(SettingsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Settings record);

    int insertSelective(Settings record);

    Settings selectOnlyOneByExample(SettingsExample example);

    List<Settings> selectByExample(SettingsExample example);

    Settings selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Settings record, @Param("example") SettingsExample example);

    int updateByExample(@Param("record") Settings record, @Param("example") SettingsExample example);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);
}