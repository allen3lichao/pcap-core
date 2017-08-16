import java.util.Arrays;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/**
 * Created by lic on 2017/6/6.
 */
public class TYest {

  public static void main(String[] args) {
    Text txt = new Text();
    IntWritable iw = new IntWritable();
    String ip = "192.168.1.1";
    byte[] ipBytes = ip.getBytes();
    txt.set(ipBytes);
    System.out.println(txt.toString());



  }

  public static byte[] hexStringToBytes(String hexString) {
    if (hexString == null || hexString.equals("")) {
      return null;
    }
    hexString = hexString.toUpperCase();
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
    }
    return d;
  }

  private static byte charToByte(char c) {
    return (byte) "0123456789ABCDEF".indexOf(c);
  }

}
