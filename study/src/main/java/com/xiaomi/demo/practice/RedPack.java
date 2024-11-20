package com.xiaomi.demo.practice;

/**
 * @Author liuchiyun
 * @Date 2024/11/16
 */
public class RedPack {

//    public static List<Long> preSplit(long money, int n) {
//        if (n <= 0) {
//            throw new IllegalArgumentException("wrong red pack num");
//        }
//        if (money < n) {
//            throw new IllegalArgumentException("money " + money + "don't match num " + n);
//        }
//        List<Long> result = new ArrayList<>();
//        Random random = new Random((System.currentTimeMillis() + money) / (n + 1));
//        // 创建一个包含 n-1 个随机位置的列表
//        Set<Long> positions = new HashSet<>();
//        while (positions.size() < n - 1) {
//            positions.add(random.nextLong(money - 1) + 1);
//        }
//        // 将总金额分成 n 份
//        positions.add(0L);
//        positions.add(money);
//        ArrayList<Long> arrPositions = new ArrayList<>(positions);
//        Collections.sort(arrPositions);
//
//        for (int i = 1; i < arrPositions.size(); i++) {
//            result.add(arrPositions.get(i) - arrPositions.get(i - 1));
//        }
//        Collections.shuffle(result);
//        return result;
//    }

//    /**
//     * @param totalAmount    总金额
//     * @param totalPeopleNum 总个数
//     */
//    public static List<Long> divideRedPackage(long totalAmount, int totalPeopleNum) {
//
//        List<Long> amountList = new ArrayList<>();
//        long restAmount = totalAmount;
//        int restPeopleNum = totalPeopleNum;
//        Random random = new Random();
//        for (int i = 0; i < totalPeopleNum - 1; i++) {
//
//            // 随机范围：[1，剩余人均金额的两倍)，左闭右开
//            long amount = random.nextLong(restAmount / restPeopleNum * 2 - 1) + 1;
//            // 金额递减
//            restAmount -= amount;
//            // 红包数量递减
//            restPeopleNum--;
//        }
//        //最后一个拿剩余全部
//        amountList.add(restAmount);
//        Collections.shuffle(amountList);
//        return amountList;
//    }
}
