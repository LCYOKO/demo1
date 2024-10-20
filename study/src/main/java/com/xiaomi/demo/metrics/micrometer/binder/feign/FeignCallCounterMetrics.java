package com.xiaomi.demo.metrics.micrometer.binder.feign;


//public class FeignCallCounterMetrics implements RequestInterceptor, MeterBinder {
//
//    private static MeterRegistry meterRegistry;
//
//    private static Counter totalCounter;
//
//    @Override
//    public void apply(RequestTemplate template) {
//        // FeignClient 子上下文调用
//        // 异步执行
////        async(() -> {
////            // 方法统计
////            String feignMethod = template.methodMetadata().configKey();
////            Counter counter = Counter.builder("feign.calls")
////                    .tags("method", feignMethod)
////                    .register(meterRegistry);
////            counter.increment();
////            // 全局统计
////            totalCounter.increment();
////        });
//
//    }
//
//    @Override
//    public void bindTo(MeterRegistry registry) { // Spring Boot 主上下文调用
//        this.meterRegistry = registry;
//        this.totalCounter = Counter.builder("feign.total-calls")
//                .register(registry);
//    }
//}
