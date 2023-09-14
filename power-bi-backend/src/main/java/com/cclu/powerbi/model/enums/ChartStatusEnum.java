package com.cclu.powerbi.model.enums;

import lombok.Getter;

/**
 * @author ChangCheng Lu
 * @date 2023/9/14 8:39
 */
@Getter
public enum ChartStatusEnum {

    /**
     * status
     */
    WAIT("wait"),
    RUNNING("running"),
    SUCCEED("succeed"),
    FAILED("failed");


    private final String value;

    ChartStatusEnum(String value) {
        this.value = value;
    }

}
