package com.yaoxj.unifyException;

import lombok.Data;

/**
 * @Author: bssc
 * @CreateTime: 2022-12-07
 * @Description: ExceptionResponse
 * @Version: 1.0
 */

@Data
public class ExceptionResponse {
    public static final String FILED_FAILED = "failed";
    public static final String FILED_CODE = "code";
    public static final String FILED_MESSAGE = "message";
    public static final String FILED_TYPE = "type";

    private Boolean failed;
    private String code;
    private String message;
    private String type;
}
