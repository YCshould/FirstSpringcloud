package com.hmall.tradeservice.listener;

import com.hmall.tradeservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaySuccessListener {

    private final IOrderService orderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "trade.pay.success.queue"),
            exchange = @Exchange(name = "pay.direct"),
            key = "pay.success"
    ))
    public void ListenSuccess(Long orderId){
        orderService.markOrderPaySuccess(orderId);
    }
}
