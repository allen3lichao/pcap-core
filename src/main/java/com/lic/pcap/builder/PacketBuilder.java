package com.lic.pcap.builder;

/**
 * Created by lic on 2017/6/14.
 */
public abstract class PacketBuilder {

  public abstract void generateDatagramHeader();

  public abstract void generateLinkLayer();

  public abstract void generateInternetLayer();

  public abstract void generateTransportLayer();

  public abstract void generateApplicationLayer();

  public abstract void retrievePacket();

}
