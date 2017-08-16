package com.lic.pcap.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

/**
 * Created by lic on 2017/6/6.
 */
public class BytesRefArrayWritable implements WritableComparable<BytesRefArrayWritable> {

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public int compareTo(BytesRefArrayWritable o) {
    return 0;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {

  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {

  }
}
