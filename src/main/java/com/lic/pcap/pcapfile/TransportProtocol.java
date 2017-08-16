package com.lic.pcap.pcapfile;

import com.sun.xml.internal.xsom.impl.WildcardImpl.Other;

/**
 * Created by lic on 2017/6/20.
 */
public enum TransportProtocol {
  TCP(6, "TCP"),
  UDP(17, "UDP"),
  SCTP(132, "SCTP"),
  FRAGMENT(44, "FRAGMENT"),
  OTHERS(-1,"");

  private int code;
  private String description;

  TransportProtocol(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public static TransportProtocol getDescription(int code) {
    for (TransportProtocol transPro : TransportProtocol.values()) {

      if (code == transPro.getCode()) {
        return transPro;
      }
    }
    return OTHERS;
  }


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
