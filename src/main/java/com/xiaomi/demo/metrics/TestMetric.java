package com.xiaomi.demo.metrics;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2023/10/16
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