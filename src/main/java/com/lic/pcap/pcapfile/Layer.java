package com.lic.pcap.pcapfile;

import java.io.DataInputStream;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 * Created by lic on 2017/6/15.
 */
public interface Layer {

  public Layer generateLayer(DataInputStream in) throws IOException;


}
