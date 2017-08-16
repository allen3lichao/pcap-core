package com.lic.pcap.pcapfile;

import com.lic.pcap.PcapFileInputFormat;
import java.io.File;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lic on 2017/6/7.
 */
public class PcapFileInputFormatTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void map() throws Exception {

    Configuration conf = new Configuration(false);
    conf.set("fs.default.name", "file:///");

    File testFile = new File(
        "F:\\s1u2017041115\\1491900510_1.pcap");
    Path path = new Path(testFile.getAbsoluteFile().toURI());
    FileSplit split = new FileSplit(path, 0, testFile.length(), null);

    PcapFileInputFormat inputFormat = ReflectionUtils.newInstance(PcapFileInputFormat.class, conf);
    TaskAttemptContext context = new TaskAttemptContextImpl(conf, new TaskAttemptID());
    RecordReader reader =  inputFormat.createRecordReader(split, context);
    reader.initialize(split, context);
    reader.nextKeyValue();
    reader.getCurrentKey();

  }

}