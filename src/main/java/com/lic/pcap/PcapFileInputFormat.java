package com.lic.pcap;

import com.lic.pcap.io.BytesRefArrayWritable;
import com.lic.pcap.pcapfile.Packet;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * Created by lic on 2017/6/6.
 */
public class PcapFileInputFormat extends FileInputFormat<LongWritable, Packet> {

  @Override
  protected boolean isSplitable(JobContext context, Path filename) {
    return false;
  }

  @Override
  public List<InputSplit> getSplits(JobContext job) throws IOException {
    return super.getSplits(job);
  }

  @Override
  public RecordReader<LongWritable, Packet> createRecordReader(InputSplit inputSplit,
      TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    return new PcapFileRecordReader<LongWritable, Packet>();
  }
}
