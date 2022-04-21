package com.evivv.store.listener;

import com.evivv.store.clients.ProductsClient;
import com.evivv.store.clients.UserClient;
import com.evivv.store.entity.SeckillMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductsClient productsClient;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "seckill.queue1"),
            exchange = @Exchange(name = "order.topic", type = ExchangeTypes.TOPIC),
            key = "order.seckill"
    ))
    public void listenDirectQueue1(SeckillMessage msg){
        System.out.println("消费者接收到seckill.queue1的消息：【" + msg + "】");
//        userClient.getDefaultAddress(msg.getUid());

    }

}
