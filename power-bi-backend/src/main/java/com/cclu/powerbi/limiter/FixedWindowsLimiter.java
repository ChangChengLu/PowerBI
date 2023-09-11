package com.cclu.powerbi.limiter;

import lombok.Data;

/**
 * @author ChangCheng Lu
 * @date 2023/9/11 15:56
 */
@Data
public class FixedWindowsLimiter {

    private static final long WINDOW_UNIT = 1000;

    private long lastRequestTime;

    private int counter;

    private int threshold;

    public FixedWindowsLimiter(int threshold) {
        lastRequestTime = System.currentTimeMillis();
        counter = 0;
        this.threshold = threshold;
    }

    public boolean fixedWindowsTryAcquire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTime > WINDOW_UNIT) {
            counter = 0;
            lastRequestTime = currentTime;
        }
        if (counter < threshold) {
            counter++;
            return true;
        }
        return false;
    }
}