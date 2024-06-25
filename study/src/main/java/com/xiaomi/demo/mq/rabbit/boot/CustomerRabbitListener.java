package com.xiaomi.demo.mq.rabbit.boot;

/**
 * @author liuchiyun
 * spring.rabbitmq.addresses=192.168.11.76:5672
 * spring.rabbitmq.username=guest
 * spring.rabbitmq.password=guest
 * spring.rabbitmq.virtual-host=/
 * spring.rabbitmq.connection-timeout=15000
 * <p>
 * spring.rabbitmq.listener.simple.acknowledge-mode=manual
 * spring.rabbitmq.listener.simple.concurrency=5
 * spring.rabbitmq.listener.simple.max-concurrency=10
 * <p>
 * spring.rabbitmq.listener.order.queue.name=queue-2
 * spring.rabbitmq.listener.order.queue.durable=true
 * spring.rabbitmq.listener.order.exchange.name=exchange-2
 * spring.rabbitmq.listener.order.exchange.durable=true
 * spring.rabbitmq.listener.order.exchange.type=topic
 * spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
 * spring.rabbitmq.listener.order.key=springboot.*
 */
//@Component]
//@Slf4j
//public class CustomerRabbitListener {
//
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue-1",
//                    durable = "true"),
//            exchange = @Exchange(value = "exchange-1",
//                    durable = "true",
//                    type = "topic",
//                    ignoreDeclarationExceptions = "true"),
//            key = "springboot.*"
//    )
//    )
//    @RabbitHandler
//    public void onMessage(Message message, Channel channel) throws Exception {
//        log.info("消费端Payload:{} ", message.getPayload());
//        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        //手工ACK
//        channel.basicAck(deliveryTag, false);
//    }
//
//    /**
//     * spring.rabbitmq.listener.order.queue.name=queue-2
//     * spring.rabbitmq.listener.order.queue.durable=true
//     * spring.rabbitmq.listener.order.exchange.name=exchange-1
//     * spring.rabbitmq.listener.order.exchange.durable=true
//     * spring.rabbitmq.listener.order.exchange.type=topic
//     * spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
//     * spring.rabbitmq.listener.order.key=springboot.*
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
//                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
//            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
//                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
//                    type = "${spring.rabbitmq.listener.order.exchange.type}",
//                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
//            key = "${spring.rabbitmq.listener.order.key}"
//    )
//    )
//    @RabbitHandler
//    public void onOrderMessage(@Payload Order order,
//                               Channel channel,
//                               @Headers Map<String, Object> headers) throws Exception {
//        log.info("消费端order:{}", order);
//        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//        //手工ACK
//        channel.basicAck(deliveryTag, false);
//    }
//}
