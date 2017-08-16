package com.lic.pcap.pcapfile;

import com.lic.pcap.pcapfile.packet.DefaultPacket;

/**
 * Created by lic on 2017/6/15.
 */
public interface PacketFactory {
  public DefaultPacket createPacket();
}
