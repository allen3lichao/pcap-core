package com.lic.pcap.uitl;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lic on 2017/6/17.
 */
public class DataInputUtil {

  public static byte[] readFully(DataInputStream in, int size) throws IOException {

    byte[] byffer = new byte[size];
    in.readFully(byffer);
    return byffer;
  }


  public static int readInt(InputStream in) throws IOException {
    return readInt(in, false);
  }

  /**
   * Bytes for this operation are read from the contained
   * input stream.
   *
   * @return the next four bytes of this input stream, interpreted as an <code>int</code>.
   * @throws EOFException if this input stream reaches the end before reading four bytes.
   * @throws IOException the stream has been closed and the contained input stream does not support
   * reading after close, or another I/O error occurs.
   */
  public static int readInt(InputStream in, boolean reversed) throws IOException {
    int ch1 = in.read();
    int ch2 = in.read();
    int ch3 = in.read();
    int ch4 = in.read();
    if (!reversed) {
      return ((ch4 & 0xFF) << 24) | ((ch3 & 0xFF) << 16) | ((ch2 & 0xFF) << 8) | (ch1 & 0xFF);
    } else {
      return ((ch1 & 0xFF) << 24) | ((ch2 & 0xFF) << 16) | ((ch3 & 0xFF) << 8) | (ch4 & 0xFF);
    }
  }


  public static long convertInt(byte[] data) {
    return convertInt(data, false);
  }

  public static long
  convertInt(byte[] data, boolean reversed) {
    if (!reversed) {
      return ((data[3] & 0xFF) << 24) | ((data[2] & 0xFF) << 16)
          | ((data[1] & 0xFF) << 8) | (data[0] & 0xFF);
    } else {
      return ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16)
          | ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
    }
  }


  /**
   * Convert short long.
   *
   * @param in the data
   * @return the long
   */
  public static int readShort(InputStream in) throws IOException {
    int ch1 = in.read();
    int ch2 = in.read();
    return ((ch1 & 0xFF) << 8) | (ch2 & 0xFF);
  }


  /**
   * Convert address to string.
   *
   * @param in the in
   * @return the string
   * @throws IOException the io exception
   */
  public static String readAddress(DataInputStream in) throws IOException {
    int ch1 = in.read();
    int ch2 = in.read();
    int ch3 = in.read();
    int ch4 = in.read();
    return (int) (ch1 & 0xFF) + "." + (int) (ch2 & 0xFF) + "." + (int) (ch3 & 0xFF) + "." + (int) (
        ch4 & 0xFF);
  }


}