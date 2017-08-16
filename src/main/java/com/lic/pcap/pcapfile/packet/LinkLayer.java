package com.lic.pcap.pcapfile.packet;

import com.lic.pcap.pcapfile.Layer;
import com.lic.pcap.uitl.ByteArrayUtils;
import com.lic.pcap.uitl.DataInputUtil;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.Writable;

/**
 * Created by lic on 2017/6/15.
 *
 * Packet header data structure is as follows:
 * * bit 0                                              48
 * *   0 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * *     +        Destination Mac 6 byte                +
 * *  48 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * *     +       Source Mac      6 byte                 +
 * *  96 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * *     +  Type 2byte  +                               +
 * * 144 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 *
 * the default is 14byte.
 *
 * Currently version only supports Ethernet
 *
 * */
public class LinkLayer implements Layer, Writable {

  //private static final int ETHERNET = 0x0800;  //Internet Protocol version 4 (IPv4)

  /**
   * The Destination mac address.
   */
  private String destinationMac;

  /**
   * The source mac address.
   */
  private String sourceMac;

  /**
   * The protocol type of InternetLayer.
   */
  private String internetProtocolType;

  /**
   * The byte array of the TransportLayer ,the default is 14byte.
   */
//  private byte[] payload;
  @Override
  public Layer generateLayer(DataInputStream in) throws IOException {
    byte[] mac = new byte[6];

    in.readFully(mac);
    destinationMac = ByteArrayUtils.toHexString(mac);

    in.readFully(mac);
    sourceMac = ByteArrayUtils.toHexString(mac);

    internetProtocolType = String.valueOf(in.readShort());

    return this;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {

    dataOutput.writeUTF(destinationMac);
    dataOutput.writeUTF(sourceMac);
    dataOutput.writeUTF(internetProtocolType);

  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    destinationMac = dataInput.readUTF();
    sourceMac = dataInput.readUTF();
    internetProtocolType = dataInput.readUTF();
  }

  public String getDestinationMac() {
    return destinationMac;
  }

  public void setDestinationMac(String destinationMac) {
    this.destinationMac = destinationMac;
  }

  public String getSourceMac() {
    return sourceMac;
  }

  public void setSourceMac(String sourceMac) {
    this.sourceMac = sourceMac;
  }

  public String getInternetProtocolType() {
    return internetProtocolType;
  }

  public void setInternetProtocolType(String internetProtocolType) {
    this.internetProtocolType = internetProtocolType;
  }
}
