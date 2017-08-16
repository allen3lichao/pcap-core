package com.lic.pcap;

import com.lic.pcap.io.BytesRefArrayWritable;
import com.lic.pcap.io.PcapFile;
import com.lic.pcap.io.PcapFile.Reader;
import com.lic.pcap.pcapfile.Packet;
import com.lic.pcap.pcapfile.packet.DefaultPacket;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * Created by lic on 2017/6/6.
 */
public class PcapFileRecordReader<K extends LongWritable, V extends Packet> extends
    RecordReader<LongWritable, Packet> {

  private Reader in;
  private long start, end;
  private boolean more = true;

  // key and value objects are created once in initialize() and then reused
  // for every getCurrentKey() and getCurrentValue() call.
  private LongWritable key;
  private DefaultPacket value;


  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
      throws IOException, InterruptedException {
    FileSplit fSplit = (FileSplit) inputSplit;
    Path path = fSplit.getPath();

    Configuration conf = taskAttemptContext.getConfiguration();
    in = new PcapFile.Reader(path.getFileSystem(conf), path, conf);

    this.end = fSplit.getStart() + fSplit.getLength();
    if (fSplit.getStart() > in.getPosition()) {
      //Seek to the next sync mark past a given position.
      in.sync(fSplit.getStart());
    }

    this.start = in.getPosition();

    more = start < end;

    key = new LongWritable();
    value = new DefaultPacket();
  }

  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {



    more = next(key);
    if (more) {
      in.getCurrentPacket(value);
    }

    return more;
  }

  /**
   * Next boolean.
   *
   * @param key the key
   * @return the boolean
   */
  private boolean next(LongWritable key) throws IOException {
    if (!more) {
      return false;
    }

    more = in.next(key);

    if (!more) {
      return false;
    }

    return more;
  }


  @Override
  public LongWritable getCurrentKey() throws IOException, InterruptedException {
    return key;
  }

  @Override
  public Packet getCurrentValue() throws IOException, InterruptedException {
    return value;
  }

  /**
   * The current progress of the record reader through its data.
   *
   * @return a number between 0.0 and 1.0 that is the fraction of the data read
   */
  @Override
  public float getProgress() throws IOException, InterruptedException {
    if (end == start) {
      return 0.0f;
    } else {
      return Math.min(1.0f, (in.getPosition() - start) / (float) (end - start));
    }
  }

  @Override
  public void close() throws IOException {
    in.close();
  }
}
