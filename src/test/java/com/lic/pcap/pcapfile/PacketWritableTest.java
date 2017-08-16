package com.lic.pcap.pcapfile;

import com.lic.pcap.pcapfile.packet.PacketHeader;
import java.io.IOException;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lic on 2017/6/19.
 */
public class PacketWritableTest {

  private PacketWritable packet;
  private PacketHeader header;

  @Before
  public void setUp() throws Exception {

    header = new PacketHeader();
    header.setTimeStamp("140303030");
    header.setTimeStampMicros("540000");
    header.setCapLen("1450");
    header.setFrameLen("1450");

    packet = new PacketWritable(header);
    packet.header = header;

  }


  @Test
  public void testSerializationAndDeserialization() throws IOException {
    DataOutputBuffer dataOutput = new DataOutputBuffer();
    DataInputBuffer inputBuffer = new DataInputBuffer();

    packet.write(dataOutput);

    inputBuffer.reset(dataOutput.getData(), dataOutput.getLength());

    PacketHeader packetHeader = new PacketHeader();
    PacketWritable distPacket = new PacketWritable(packetHeader);

    distPacket.readFields(inputBuffer);
    System.out.println(distPacket.header.getTimeStamp());

  }
}