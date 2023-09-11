package com.cclu.powerbi.limiter;

/**
 * @author ChangCheng Lu
 * @date 2023/9/11 16:39
 */
public class TokenBucketLimiter {

    /**
     * 每秒处理数量（放入令牌数量）
     */
    private long putTokenRate;

    /**
     * 最后刷新时间
     */
    private long refreshTime;

    /**
     * 令牌桶容量
     */
    private long capacity;

    /**
     * 当前桶内令牌数
     */
    private long currentToken = 0L;

    /**
     * 令牌桶算法
     * @return
     */
    private boolean tokenBucketTryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 生成的令牌 = (当前时间 - 上次刷新时间) * 放入令牌的速率
        long generateToken = (currentTime - refreshTime) / 1000 * putTokenRate;
        // 当前令牌数量 = 之前的桶内令牌数量+放入的令牌数量
        currentToken = Math.min(capacity, generateToken + currentToken);
        refreshTime = currentTime;

        if (currentToken > 0) {
            currentToken--;
            return true;
        }
        return false;
    }

}
