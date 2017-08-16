package com.lic.pcap.pcapfile;

/**
 * Created by lic on 2017/6/6.
 */
public enum LinkType {
  Null(-1),
  Ethernet(1),
  RawIP(101),
  LoopBack(108),
  LINUX_SLL(113);


  private int code;

  LinkType(int code) {
    this.code = code;
  }

  public static LinkType get(int code){
    for (LinkType linkType : LinkType.values()){
      if(linkType.getCode() == code){
        return linkType;
      }
    }
    return Null;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
