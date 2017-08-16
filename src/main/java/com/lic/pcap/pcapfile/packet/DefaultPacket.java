package com.lic.pcap.pcapfile.packet;

import com.lic.pcap.pcapfile.Packet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

/**
 * Created by lic on 2017/6/7.
 */
public class DefaultPacket implements Packet,WritableComparable {

  public PacketHeader header;

  public LinkLayer linkLayer;

  public InternetLayer internetLayer;

  public TransportLayer transportLayer;

  public ApplicationLayer applicationLayer;

//  protected abstract void processPayload(byte[] payload);

  @Override
  public void reset() {

  }


  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {

  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {

  }
}
