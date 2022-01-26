package com.jdd.community_management_system.config.security.exception;

import org.springframework.security.core.AuthenticationException;

/** token异常 */
public class CustomAuthorizationException extends AuthenticationException {
  private static final long serialVersionUID = 7722674266118235370L;

  public CustomAuthorizationException(String msg) {
    super(msg);
  }
}
