package com.lic.pcap.pcapfile;

import com.lic.pcap.pcapfile.packet.ApplicationLayer;
import com.lic.pcap.pcapfile.packet.InternetLayer;
import com.lic.pcap.pcapfile.packet.LinkLayer;
import com.lic.pcap.pcapfile.packet.PacketHeader;
import com.lic.pcap.pcapfile.packet.TransportLayer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 * Created by lic on 2017/6/19.
 */
public class PacketWritable implements Writable {


  public PacketHeader header;

//  public LinkLayer linkLayer;
//
//  public InternetLayer internetLayer;
//
//  public TransportLayer transportLayer;
//
//  public ApplicationLayer applicationLayer;


  public PacketWritable(PacketHeader header) {
    this.header = header;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
      header.write(dataOutput);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
      header.readFields(dataInput);
  }
}
