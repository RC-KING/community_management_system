package com.jdd.community_management_system.utils.log.utils;

import javax.servlet.http.HttpServletRequest;

public class IPHelper {
  private static final String UNKNOWN = "unknown";

  /**
   * 得到用户的真实地址,如果有多个就取第一个
   *
   * @return
   */
  public static String getIpAddr(HttpServletRequest request) {
    if (request == null) {
      return "unknown";
    }
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    String[] ips = ip.split(",");
    return ips[0].trim();
  }
}
