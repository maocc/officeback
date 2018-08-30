package cn.feezu.maint.order.mapper;

import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectOnlyOneByExample(OrderExample example);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}