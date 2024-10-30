package com.hmall.itemservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.hmapi.dto.OrderDetailDTO;
import com.hmall.itemservice.domain.po.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    @Update("UPDATE item SET stock = stock - #{num} WHERE id = #{itemId}")
    void updateStock(OrderDetailDTO orderDetail);
}
