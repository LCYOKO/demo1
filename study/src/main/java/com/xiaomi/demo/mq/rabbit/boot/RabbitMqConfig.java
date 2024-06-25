package com.xiaomi.demo.mq.rabbit.boot;

import com.xiaomi.demo.mq.rabbit.boot.adapter.MessageDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author liuchiyun
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("localhost:5672");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true);
    }

    @Bean
    public Binding binding001(Queue queue001, TopicExchange exchange001) {
        return BindingBuilder.bind(queue001).to(exchange001).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true);
    }

    @Bean
    public Binding binding002(Queue queue002, TopicExchange exchange002) {
        return BindingBuilder.bind(queue002).to(exchange002).with("rabbit.*");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> log.info("correlationData:{}, ack:{}, cause:{}", correlationData, ack, cause);
        final RabbitTemplate.ReturnsCallback returnCallback = returned -> log.info("returned:{}", returned);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnCallback);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(), queue002());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setExposeListenerChannel(true);
        container.setConsumerTagStrategy(queue -> queue + "_" + UUID.randomUUID());
//        container.setMessageListener(new CustomerListener());


//          1 适配器方式. 默认是有自己的方法名字的：handleMessage
//          可以自己指定一个方法的名字: consumeMessage
//          也可以添加一个转换器: 从字节数组转换为String
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setDefaultListenerMethod("consumeMessage");
//         adapter.setMessageConverter(new TextMessageConverter());
//         container.setMessageListener(adapter);


//          2 适配器方式: 我们的队列名称 和 方法名称 也可以进行一一的匹配
//         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setMessageConverter(new TextMessageConverter());
//         Map<String, String> queueOrTagToMethodName = new HashMap<>();
//         queueOrTagToMethodName.put("queue001", "method1");
//         queueOrTagToMethodName.put("queue002", "method2");
//         adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
//         container.setMessageListener(adapter);


//         1.1 支持json格式的转换器
//         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setDefaultListenerMethod("consumeMessage");
//
//         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//         adapter.setMessageConverter(jackson2JsonMessageConverter);
//
//         container.setMessageListener(adapter);


        // 1.2 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
//
//         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setDefaultListenerMethod("consumeMessage");
//
//         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//
//         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//
//         adapter.setMessageConverter(jackson2JsonMessageConverter);
//         container.setMessageListener(adapter);


//        //1.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
//
//         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setDefaultListenerMethod("consumeMessage");
//         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//
//         Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
//         idClassMapping.put("order", com.bfxy.spring.entity.Order.class);
//         idClassMapping.put("packaged", com.bfxy.spring.entity.Packaged.class);
//
//         javaTypeMapper.setIdClassMapping(idClassMapping);
//
//         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//         adapter.setMessageConverter(jackson2JsonMessageConverter);
//         container.setMessageListener(adapter);


        //1.4 ext convert
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//
        //全局的转换器:
        adapter.setMessageConverter(jsonMessageConverter);
        adapter.setDefaultListenerMethod("consumeMessage");
        container.setMessageListener(adapter);
        container.setAutoStartup(true);
        return container;
    }
}
