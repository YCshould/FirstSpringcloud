package com.hmall.tradeservice.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hmall.tradeservice.domain.po.OrderDetail;
import com.hmall.tradeservice.mapper.OrderDetailMapper;
import com.hmall.tradeservice.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
