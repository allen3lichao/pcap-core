package com.lic.pcap.io;

import com.lic.pcap.pcapfile.LinkType;
import com.lic.pcap.pcapfile.packet.ApplicationLayer;
import com.lic.pcap.pcapfile.packet.DefaultPacket;
import com.lic.pcap.pcapfile.packet.InternetLayer;
import com.lic.pcap.pcapfile.packet.LinkLayer;
import com.lic.pcap.pcapfile.packet.PacketHeader;
import com.lic.pcap.pcapfile.packet.TransportLayer;
import com.lic.pcap.uitl.DataInputUtil;
import java.io.EOFException;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by lic on 2017/6/6.
 */
public class PcapFile {

  private static final Logger LOG = LogManager.getLogger(PcapFile.class);

  /**
   * Read KeyBuffer/ValueBuffer pairs from a PcapFile.
   */
  public static class Reader {

    //The hex String of Magic is 'D4 C3 B2 A1' or 'A1 B2 C3 D4'
    //private static final byte[] MAGIC = new byte[] {-44, -61, -78, -95};
    private static final long MAGIC = 0xD4C3B2A1;
    private static final long MAGIC_REVERSED = 0xA1B2C3D4;


    private static final long VERSION_NUMBER_MAJOR_LEN = 2;// Consider whether or not to save
    private static final long VERSION_NUMBER_MINOR_LEN = 2;//Consider whether or not to save
    private static final long SIG_FIGS_LEN = 4;//Consider whether or not to save
    private static final long TIME_ZONE_LEN = 4;//Consider whether or not to save

    private int passedRowsNum = 0;

    private LinkType linkType;
    private long snapLen;

    private final Configuration conf;
    private final Path file;
    private final FSDataInputStream in;
    private final long end;
    private DefaultPacket packet;


    /**
     * Create a new PcapFile reader.
     */
    public Reader(FileSystem fs, Path file, Configuration conf) throws IOException {
      this(fs, file, conf.getInt("io.file.buffer.size", 4096), conf, 0,
          fs.getFileStatus(file).getLen());
    }

    /**
     * Create a new PcapFile reader.
     */
    public Reader(FileSystem fs, Path file, int bufferSize, Configuration conf, long start,
        long length) throws IOException {
      conf.setInt("io.file.buffer.size", bufferSize);
      this.conf = conf;
      this.file = file;

      in = openFile(fs, file, bufferSize, length);
      end = start + length;

      boolean success = false;
      try {
        if (start > 0) {
          seek(0);
          init();
          seek(start);
        } else {
          init();
        }
        success = true;
      } finally {
        if (!success) {
          if (in != null) {
            try {
              in.close();
            } catch (IOException e) {
              if (LOG != null && LOG.isDebugEnabled()) {
                LOG.debug("Exception in closing" + in, e);
              }
            }
          }
        }
      }


    }

    private void init() throws IOException {
      int magic;
      try {

        magic = in.readInt();//Read Magic key
      } catch (EOFException e) {
        LOG.warn("Skipping empty file");
        return;
      }

      if (magic == MAGIC || magic == MAGIC_REVERSED) {
        in.skip(VERSION_NUMBER_MAJOR_LEN + VERSION_NUMBER_MINOR_LEN + SIG_FIGS_LEN + TIME_ZONE_LEN);
        snapLen = DataInputUtil.readInt(in);
        linkType = LinkType.get(DataInputUtil.readInt(in));
      } else {
        throw new IOException(file + "not a pcap file.");
      }
    }

    public synchronized void seek(long position) throws IOException {
      in.seek(position);
    }


    /**
     * Get the current row used,make sure called {@link #next(LongWritable)}} first.
     */
    public synchronized void getCurrentPacket(DefaultPacket packet) throws IOException {
      PacketHeader header = new PacketHeader();
      packet.header = (PacketHeader) header.generateLayer(in);

      LinkLayer linkLayer = new LinkLayer();
      packet.linkLayer = (LinkLayer) linkLayer.generateLayer(in);

      InternetLayer internetLayer = new InternetLayer();
      packet.internetLayer = (InternetLayer) internetLayer.generateLayer(in);

      TransportLayer transportLayer = new TransportLayer();
      packet.transportLayer = (TransportLayer) transportLayer.generateLayer(in);

      ApplicationLayer applicationLayer = new ApplicationLayer();


      System.out.println();
    }


    /**
     * Next boolean.
     *
     * @param readPackets the read packets
     * @return the boolean
     */
    public synchronized boolean next(LongWritable readPackets) throws IOException {
      readPackets.set(passedRowsNum);
      passedRowsNum++;
      if (getPosition() <= end) {
        return true;
      } else {
        return false;
      }
    }


    /**
     * Override this method to specialize the type of {@link FSDataInputStream} returned.
     * Open file fs data input stream.
     *
     * @return the fs data input stream
     */
    protected FSDataInputStream openFile(FileSystem fs, Path file, int bufferSize, long length)
        throws IOException {
      return fs.open(file, bufferSize);
    }

    /**
     * Seek to next sync mark past a given position.
     *
     * @param position a given position
     */
    public synchronized void sync(long position) throws IOException {
      if (position >= end) {
        in.seek(end);
        return;
      }
    }

    /**
     * Return the current byte position in then input file.
     */
    public synchronized long getPosition() throws IOException {
      return in.getPos();
    }

    /**
     * Close the reader.
     */
    public void close() {

    }

  }

}
