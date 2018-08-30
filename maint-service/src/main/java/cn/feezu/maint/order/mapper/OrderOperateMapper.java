package cn.feezu.maint.order.mapper;

import cn.feezu.maint.order.entity.OrderOperate;
import cn.feezu.maint.order.entity.OrderOperateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderOperateMapper {
    int countByExample(OrderOperateExample example);

    int deleteByExample(OrderOperateExample example);

    int insert(OrderOperate record);

    int insertSelective(OrderOperate record);

    OrderOperate selectOnlyOneByExample(OrderOperateExample example);

    List<OrderOperate> selectByExample(OrderOperateExample example);

    int updateByExampleSelective(@Param("record") OrderOperate record, @Param("example") OrderOperateExample example);

    int updateByExample(@Param("record") OrderOperate record, @Param("example") OrderOperateExample example);
}