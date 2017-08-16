package com.lic.pcap.builder;

import com.lic.pcap.builder.impl.DefaultPacketBuilder;

/**
 * Created by lic on 2017/6/14.
 */
public class Director {

  private PacketBuilder pb;

  public Director(PacketBuilder pb) {
    this.pb = new DefaultPacketBuilder();
  }

  public void creatPacket(){
    pb.generateDatagramHeader();
    pb.generateLinkLayer();
    pb.generateInternetLayer();
    pb.generateTransportLayer();
    pb.generateApplicationLayer();
    pb.retrievePacket();

  }


}
