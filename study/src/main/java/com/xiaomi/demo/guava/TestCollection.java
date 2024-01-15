package com.xiaomi.demo.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;


/**
 * @Author: liuchiyun
 * @Date: 2023/12/18
 */
@Slf4j
public class TestCollection {

    @Test
    public void testSet() {
        Set<Integer> set1 = ImmutableSet.of(1, 2, 3);
        Set<Integer> set2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> difference = Sets.intersection(set1, set2);
        Assert.assertEquals(2, difference.size());
        //返回的是视图，如果修改会有影响
        set2.add(1);
        Assert.assertEquals(3, difference.size());
    }

    @Test
    public void testPrim() {
        //把数组转换成List
        List<Integer> integers = Ints.asList(1, 2, 3);
        Assert.assertTrue(CollectionUtils.isNotEmpty(integers));
        //不允许操作
        //integers.add(3);
        //  Assert.assertTrue(4 == integers.size());
    }

    @Test
    public void testRange() {
        //(a..b)	open(C, C)
        //[a..b]	closed(C, C)
        //[a..b)	closedOpen(C, C)
        //(a..b]	openClosed(C, C)
        //(a..+∞)	greaterThan(C)
        //[a..+∞)	atLeast(C)
        //(-∞..b)	lessThan(C)
        //(-∞..b]	atMost(C)
        //(-∞..+∞)	all()
        Range<Integer> range = Range.closed(1, 2);
        Assert.assertTrue(range.contains(1));
        Assert.assertTrue(range.contains(2));
        Assert.assertFalse(range.contains(3));
    }

    @Test
    public void testJoiner() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        joiner.join("Harry", null, "Ron", "Hermione");
        log.info("str:{}", joiner.join("Harry", null, "Ron", "Hermione"));
    }
}
