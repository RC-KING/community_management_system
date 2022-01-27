package com.jdd.community_management_system.utils.dataUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}