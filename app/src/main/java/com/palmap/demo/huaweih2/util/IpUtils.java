package com.palmap.demo.huaweih2.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by eric3 on 2016/10/21.
 */

public class IpUtils {
  public static String getIpAddress() {
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
            //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
            return inetAddress.getHostAddress().toString();
          }
        }
      }
    } catch (Exception e) {
    }
    return "";
  }
}
