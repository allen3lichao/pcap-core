package com.lic.pcap.pcapfile.packet;

import com.lic.pcap.pcapfile.Layer;
import com.lic.pcap.uitl.DataInputUtil;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.log4j.Logger;

/**
 * Created by lic on 2017/6/15.
 *
 * Packet header data structure is as follows:
 *
 * bit 0                              32
 * 0  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * +        TimeStamp 4 byte      +
 * 32 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * +        TimeStamp 4 byte      +
 * 64 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * +        Caplen    4 byte      +
 * 96 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 * +        len       4 byte      +
 * 128 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
 *
 *
 * the default is 16byte.
 */
public class PacketHeader implements Layer,Writable {

  private static final Logger LOG = org.apache.log4j.LogManager.getLogger(PacketHeader.class);

  private static final int DEFAULT_SIZE = 16;

  /**
   * Timestamp exact to seconds
   */
  private String timeStamp;
  /**
   * Timestamp exact to seconds
   */
  private String timeStampMicros;
  /**
   * The length of the current data area that is the length of the data frame fetched,Whereby the
   * position of the next data frame can be obtained.
   */
  private String capLen;
  /**
   * Offline data length:
   * The length of the actual data frame in the network is generally not greater
   * than caplen and in most cases the Caplen value is equal.
   */
  private String frameLen;


  /**
   * The byte array of the packet header,the default is 16byte.
   */
//  private byte[] payload;

  @Override
  public Layer generateLayer(DataInputStream in) throws IOException {

    timeStamp = String.valueOf(DataInputUtil.readInt(in));
    timeStampMicros = String.valueOf(DataInputUtil.readInt(in));
    capLen = String.valueOf(DataInputUtil.readInt(in));
    frameLen = String.valueOf(DataInputUtil.readInt(in));

    return this;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getTimeStampMicros() {
    return timeStampMicros;
  }

  public void setTimeStampMicros(String timeStampMicros) {
    this.timeStampMicros = timeStampMicros;
  }

  public String getCapLen() {
    return capLen;
  }

  public void setCapLen(String capLen) {
    this.capLen = capLen;
  }

  public String getFrameLen() {
    return frameLen;
  }

  public void setFrameLen(String frameLen) {
    this.frameLen = frameLen;
  }


  @Override
  public void write(DataOutput dataOutput) throws IOException {
     dataOutput.writeUTF(timeStamp);
     dataOutput.writeUTF(timeStampMicros);
     dataOutput.writeUTF(capLen);
     dataOutput.writeUTF(frameLen);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    timeStamp = dataInput.readUTF();
    timeStampMicros = dataInput.readUTF();
    capLen = dataInput.readUTF();
    frameLen = dataInput.readUTF();
  }

}
