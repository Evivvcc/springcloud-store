package com.evivv.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.evivv.store.entity.Order;
import com.evivv.store.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {



    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
