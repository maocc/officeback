package cn.feezu.maint.assign.mapper;

import cn.feezu.maint.assign.entity.CheckList;
import cn.feezu.maint.assign.entity.CheckListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CheckListMapper {
    int countByExample(CheckListExample example);

    int deleteByExample(CheckListExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CheckList record);

    int insertSelective(CheckList record);

    CheckList selectOnlyOneByExample(CheckListExample example);

    List<CheckList> selectByExample(CheckListExample example);

    CheckList selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CheckList record, @Param("example") CheckListExample example);

    int updateByExample(@Param("record") CheckList record, @Param("example") CheckListExample example);

    int updateByPrimaryKeySelective(CheckList record);

    int updateByPrimaryKey(CheckList record);
}