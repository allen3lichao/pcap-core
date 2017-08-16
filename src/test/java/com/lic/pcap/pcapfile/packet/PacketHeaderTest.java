package com.lic.pcap.pcapfile.packet;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.IOException;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lic on 2017/6/19.
 */
public class PacketHeaderTest {

  private PacketHeader header;
  private DataInputStream in;

  @Before
  public void setUp() throws Exception {
    header = new PacketHeader();
    header.setTimeStamp("140303030");
    header.setTimeStampMicros("540000");
    header.setCapLen("1450");
    header.setFrameLen("1450");
//
//    byte[] bytes = new byte[] {94, -104, -20, 88, -118, 30, 8, 0, -86, 5, 0, 0, -86, 5, 0, 0};
//
//    in = new DataInputStream(new ByteArrayInputStream(bytes));
//    header.generateLayer(in);
  }
  @Test
  public void testSerializationAndDeserialization() throws IOException {

    // Write it to a normal output buffer
    DataOutputBuffer out = new DataOutputBuffer();
    DataInputBuffer in = new DataInputBuffer();
    header.write(out);

    // Read the output buffer with TextReadable. Since the valueClass is defined,
    // this should succeed
    PacketHeader destHeader = new PacketHeader();
    in.reset(out.getData(), out.getLength());
    destHeader.readFields(in);

    assertEquals(destHeader.getTimeStamp(),header.getTimeStamp());
    assertEquals(destHeader.getTimeStampMicros(),header.getTimeStampMicros());
    assertEquals(destHeader.getCapLen(),header.getCapLen());
    assertEquals(destHeader.getFrameLen(),header.getFrameLen());

  }


}