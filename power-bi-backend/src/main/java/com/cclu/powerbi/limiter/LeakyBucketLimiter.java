package com.cclu.powerbi.limiter;

/**
 * @author ChangCheng Lu
 * @date 2023/9/11 16:31
 */
public class LeakyBucketLimiter {

    /**
     * 每秒处理数（出水率）
     */
    private long rate;

    /**
     * 当前剩余水量
     */
    private long currentWater;

    /**
     * 最后刷新时间
     */
    private long refreshTime;

    /**
     * 桶容量
     */
    private long capacity;

    /**
     * 漏桶算法
     * @return
     */
    public boolean leakyBucketLimiterTryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 流出的水量 = (当前时间 - 上次刷新时间) * 出水率
        long outWater = (currentTime - refreshTime) / 1000 * rate;
        // 当前水量 = 之前的桶内水量-流出的水量
        currentWater = Math.max(0, currentWater - outWater);
        refreshTime = currentTime;

        if (currentWater < capacity) {
            currentWater++;
            return true;
        }

        return false;
    }
}
