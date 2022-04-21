package com.evivv.store.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class RabbitMQConfig {
    /**
     * 声明交换机
     * @return Fanout类型交换机
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("order.topic");
    }

    /**
     * 第1个队列
     */
    @Bean
    public Queue seckillQueue(){
        return new Queue("order.seckill");
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindingQueue(Queue seckillQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(seckillQueue).to(topicExchange).with("order.#");
    }

}
