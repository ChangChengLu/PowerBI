package com.cclu.powerbi.limiter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ChangCheng Lu
 * @date 2023/9/11 16:05
 */
public class SlidingWindowsLimiter {

    /**
     * 单位时间划分的小周期（单位时间是 1 分钟，10S 一个小窗口，一共是 6 个小窗口）
     */
    private int subCycle = 10;

    /**
     * 每分钟限制请求数量
     */
    private int thresholdPerMin = 100;

    /**
     * 计数器，key 为当前窗口的开始时间秒数，value 为当前窗口的计数
     */
    private final TreeMap<Long, Integer> counters = new TreeMap<>();


    /**
     * 滑动窗口算法实现
     * @return
     */
    public boolean slidingWindowsTryAcquire() {
        long currentWindowTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) / subCycle * subCycle;
        int currentWindowNum = countCurrentWindow(currentWindowTime);

        if (currentWindowNum > thresholdPerMin) {
            return false;
        }

       if (counters.containsKey(currentWindowTime)) {
           counters.put(currentWindowTime, 1);
       } else {
           counters.put(currentWindowTime, counters.get(currentWindowTime) + 1);
       }
        return true;
    }

    /**
     * 统计当前窗口的请求数
     * @return
     */
    private int countCurrentWindow(long currentWindowTime) {
        long startTime = currentWindowTime - subCycle * (60 * 1000 / subCycle - 1);
        int count = 0;
        Iterator<Map.Entry<Long, Integer>> iterator = counters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Integer> entry = iterator.next();
            if (entry.getKey() < startTime) {
                iterator.remove();
            } else {
                count += entry.getValue();
            }
        }
        return count;
    }

}
