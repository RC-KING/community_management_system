package com.jdd.community_management_system.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ImageCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7080424786058820644L;

    public ImageCodeException(String msg) {
        super(msg);
    }
}
