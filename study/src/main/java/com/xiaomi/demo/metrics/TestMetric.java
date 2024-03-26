package com.xiaomi.demo.metrics;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/16
 * <p>
 * io.dropwizard.metrics 经典的开源metrics库
 * micrometer spring自带的监控指标库
 * <p>
 * 提到监控，无非以下这些常见的词汇：
 * Atlas、Datadog、Ganglia、Graphite、Influx、JMX、NewRelic、Prometheus、SignalFx、StatsD、Wavefront、Micrometer、Spring-actuator、Pinpoint、Zipkin、Skywalking、Grafana等等。
 * 我从数据流的角度将以上框架大致分为三种：
 * 生产类监控指标框架：进行指标统计与监控，首先要生产指标数据，这类框架如Micrometer，我们也叫埋点类指标框架。
 * 消费类监控指标框架：有了指标数据，我们就要采集处理进行消费，这类框架如Ganglia、Prometheus、Influx等。
 * 展示类监控指标框架：这类框架主要用于数据图标的前端展示，如：Graphite、Grafana等。
 * 而今天我们了解到的Dropwizard-Metrics监控框架，就属于生产类监控指标框架。那Dropwizard-Metrics监控框架与Micrometer有什么关系吗？如何做选择呢？
 * <p>
 * 回答是：没有关系，并存关系。
 * <p>
 * 我们知道，Spring监控框架是基于Micrometer，并深度集成的。然而Hadoop、HBase则是基于Dropwizard-Metrics拓展开来，你猜是为什么？先读读下面一句话。
 * “springboot2在spring-boot-actuator中引入了micrometer，对1.x的metrics进行了重构，另外支持对接的监控系统也更加丰富(Atlas、Datadog、Ganglia、Graphite、Influx、JMX、NewRelic、Prometheus、SignalFx、StatsD、Wavefront)。1.x的metrics都有点对齐dropwizard-metrics的味道，而micrometer除了一些基本metrics与dropwizard-metrics相类似外，重点支持了tag。这是一个很重要的信号，标志着老一代的statsd、graphite逐步让步于支持tag的influx以及prometheus。”
 * 看着好像Micrometer更强大似的，简直像Dropwizard-Metrics二代升级版呀！不仅对接的监控系统更加丰富，而且还重点支持了tag
 */
@Slf4j
public class TestMetric {
//    private final MetricRegistry metricRegistry = new MetricRegistry();
//    private final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).build();
//
//    @Test
//    public void testCounter() {
//        Counter counter = metricRegistry.counter("counter");
//        counter.inc(10);
//        reporter.report();
//    }
//
//    @Test
//    public void testMeter() {
//        Meter meter = metricRegistry.meter("meter");
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        meter.mark();
//        reporter.report();
//    }
//
//    @Test
//    public void testTimer() {
//        Timer timer = metricRegistry.timer("timer");
//        try (Timer.Context ignored = timer.time()) {
//            Thread.sleep(10000);
//        } catch (Exception exception) {
//
//        }
//        reporter.report();
//    }
//
//    @Test
//    public void testGauge() {
//        List<Integer> cache = new ArrayList<>();
//        metricRegistry.register(
//                MetricRegistry.name(TestMetric.class, "cache", "size"),
//                (Gauge<Integer>) cache::size
//        );
//        reporter.report();
//        cache.add(1);
//        reporter.report();
//        cache.add(2);
//        reporter.report();
//    }
//
//    @Test
//    public void testHistogram(){
//        Histogram histogram = metricRegistry.histogram("histogram");
//        histogram.update(1);
//        reporter.report();
//    }
}