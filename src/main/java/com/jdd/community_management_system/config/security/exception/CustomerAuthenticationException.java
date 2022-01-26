package com.jdd.community_management_system.config.security.exception;

import org.springframework.security.core.AuthenticationException;

/** token异常 */
public class CustomerAuthenticationException extends AuthenticationException {
  private static final long serialVersionUID = 7722674266118235370L;

  public CustomerAuthenticationException(String msg) {
    super(msg);
  }
}
