package com.lic.pcap.pcapfile.packet;

import com.lic.pcap.pcapfile.Layer;
import com.lic.pcap.pcapfile.TransportProtocol;
import com.lic.pcap.uitl.ByteArrayUtils;
import com.lic.pcap.uitl.DataInputUtil;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 * Created by lic on 2017/6/15.
 *
 * Internet Header Format
 *
 * A summary of the contents of the internet header follows:
 *
 * 0                   1                   2                     3
 * 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |Version|  IHL  |Type of Service|          Total Length         |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |         Identification        |Flags|      Fragment Offset    |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |  Time to Live |    Protocol   |         Header Checksum       |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                       Source Address                          |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                    Destination Address                        |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                    Options                    |    Padding    |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 * Note that each tick mark represents one bit position.
 *
 * Version:  4 bits
 *
 * The Version field indicates the format of the internet header.  This
 * document describes version 4.
 *
 * IHL:  4 bits
 *
 * Internet Header Length is the length of the internet header in 32
 * bit words, and thus points to the beginning of the data.  Note that
 * the minimum value for a correct header is 5.
 *
 * Type of Service:  8 bits
 *
 * The Type of Service provides an indication of the abstract
 * parameters of the quality of service desired.  These parameters are
 * to be used to guide the selection of the actual service parameters
 * when transmitting a datagram through a particular network.  Several
 * networks offer service precedence, which somehow treats high
 * precedence traffic as more important than other traffic (generally
 * by accepting only traffic above a certain precedence at time of high
 * load).  The major choice is a three way tradeoff between low-delay,
 * high-reliability, and high-throughput.
 *
 * Bits 0-2:  Precedence.
 * Bit    3:  0 = Normal Delay,      1 = Low Delay.
 * Bits   4:  0 = Normal Throughput, 1 = High Throughput.
 * Bits   5:  0 = Normal Relibility, 1 = High Relibility.
 * Bit  6-7:  Reserved for Future Use.
 *
 * 0     1     2     3     4     5     6     7
 * +-----+-----+-----+-----+-----+-----+-----+-----+
 * |                 |     |     |     |     |     |
 * |   PRECEDENCE    |  D  |  T  |  R  |  0  |  0  |
 * |                 |     |     |     |     |     |
 * +-----+-----+-----+-----+-----+-----+-----+-----+
 *
 * Precedence
 *
 * 111 - Network Control
 * 110 - Internetwork Control
 * 101 - CRITIC/ECP
 * 100 - Flash Override
 * 011 - Flash
 * 010 - Immediate
 * 001 - Priority
 * 000 - Routine
 *
 * The use of the Delay, Throughput, and Reliability indications may
 * increase the cost (in some sense) of the service.  In many networks
 * better performance for one of these parameters is coupled with worse
 * performance on another.  Except for very unusual cases at most two
 * of these three indications should be set.
 *
 * The type of service is used to specify the treatment of the datagram
 * during its transmission through the internet system.
 *
 * The Network Control precedence designation is intended to be used
 * within a network only.  The actual use and control of that
 * designation is up to each network. The Internetwork Control
 * designation is intended for use by gateway control originators only.
 * If the actual use of these precedence designations is of concern to
 * a particular network, it is the responsibility of that network to
 * control the access to, and use of, those precedence designations.
 *
 * Total Length:  16 bits
 *
 * Total Length is the length of the datagram, measured in octets,
 * including internet header and data.  This field allows the length of
 * a datagram to be up to 65,535 octets.  Such long datagrams are
 * impractical for most hosts and networks.  All hosts must be prepared
 * to accept datagrams of up to 576 octets (whether they arrive whole
 * or in fragments).  It is recommended that hosts only send datagrams
 * larger than 576 octets if they have assurance that the destination
 * is prepared to accept the larger datagrams.
 *
 * The number 576 is selected to allow a reasonable sized data block to
 * be transmitted in addition to the required header information.  For
 * example, this size allows a data block of 512 octets plus 64 header
 * octets to fit in a datagram.  The maximal internet header is 60
 * octets, and a typical internet header is 20 octets, allowing a
 * margin for headers of higher level protocols.
 *
 * Identification:  16 bits
 *
 * An identifying value assigned by the sender to aid in assembling the
 * fragments of a datagram.
 *
 * Flags:  3 bits
 *
 * Various Control Flags.
 *
 * Bit 0: reserved, must be zero
 * Bit 1: (DF) 0 = May Fragment,  1 = Don't Fragment.
 * Bit 2: (MF) 0 = Last Fragment, 1 = More Fragments.
 *
 * 0   1   2
 * +---+---+---+
 * |   | D | M |
 * | 0 | F | F |
 * +---+---+---+
 *
 * Fragment Offset:  13 bits
 *
 * This field indicates where in the datagram this fragment belongs.
 * The fragment offset is measured in units of 8 octets (64 bits).  The
 * first fragment has offset zero.
 *
 * Time to Live:  8 bits
 *
 * This field indicates the maximum time the datagram is allowed to
 * remain in the internet system.  If this field contains the value
 * zero, then the datagram must be destroyed.  This field is modified
 * in internet header processing.  The time is measured in units of
 * seconds, but since every module that processes a datagram must
 * decrease the TTL by at least one even if it process the datagram in
 * less than a second, the TTL must be thought of only as an upper
 * bound on the time a datagram may exist.  The intention is to cause
 * undeliverable datagrams to be discarded, and to bound the maximum
 * datagram lifetime.
 *
 * Protocol:  8 bits
 *
 * This field indicates the next level protocol used in the data
 * portion of the internet datagram.  The values for various protocols
 * are specified in "Assigned Numbers" [9].
 *
 * Header Checksum:  16 bits
 *
 * A checksum on the header only.  Since some header fields change
 * (e.g., time to live), this is recomputed and verified at each point
 * that the internet header is processed.
 *
 * The checksum algorithm is:
 *
 * The checksum field is the 16 bit one's complement of the one's
 * complement sum of all 16 bit words in the header.  For purposes of
 * computing the checksum, the value of the checksum field is zero.
 *
 * This is a simple to compute checksum and experimental evidence
 * indicates it is adequate, but it is provisional and may be replaced
 * by a CRC procedure, depending on further experience.
 *
 * Source Address:  32 bits
 *
 * The source address.  See section 3.2.
 *
 * Destination Address:  32 bits
 *
 * The destination address.  See section 3.2.
 *
 * Options:  variable
 *
 * The options may appear or not in datagrams.  They must be
 * implemented by all IP modules (host and gateways).  What is optional
 * is their transmission in any particular datagram, not their
 * implementation.
 *
 * In some environments the security option may be required in all
 * datagrams.
 *
 * The option field is variable in length.  There may be zero or more
 * options.  There are two cases for the format of an option:
 *
 * Case 1:  A single octet of option-type.
 *
 * Case 2:  An option-type octet, an option-length octet, and the
 * actual option-data octets.
 *
 * The option-length octet counts the option-type octet and the
 * option-length octet as well as the option-data octets.
 *
 * The option-type octet is viewed as having 3 fields:
 *
 * 1 bit   copied flag,
 * 2 bits  option class,
 * 5 bits  option number.
 *
 * The copied flag indicates that this option is copied into all
 * fragments on fragmentation.
 *
 * 0 = not copied
 * 1 = copied
 *
 * The option classes are:
 *
 * 0 = control
 * 1 = reserved for future use
 * 2 = debugging and measurement
 * 3 = reserved for future use
 */
public class InternetLayer implements Layer, Writable {

  private int ipVersion;
  private int diffServ;
  private int ipTotalLen;
  private int identification;
  //  private int flags;
//  private int fragmentOffset;
  private int timeToLive;
  private String transportLayerProtocol;
  private int headerChecksum;
  private String ipSourceAddress;
  private String ipDestinationAddress;


  @Override
  public Layer generateLayer(DataInputStream in) throws IOException {
    ipVersion = (in.read() >> 4) & 0xF;

    diffServ = in.read();

    ipTotalLen = in.readShort();

    identification = DataInputUtil.readShort(in);

    in.skip(2);//skip flags and fragmentOffset

    timeToLive = in.read();

    transportLayerProtocol = TransportProtocol.getDescription(in.read()).getDescription();

    headerChecksum = in.readShort();

    ipSourceAddress = DataInputUtil.readAddress(in);
    ipDestinationAddress = DataInputUtil.readAddress(in);

    return this;
  }




  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(ipVersion);
    dataOutput.writeInt(diffServ);
    dataOutput.writeInt(ipTotalLen);
    dataOutput.writeInt(identification);
    dataOutput.writeInt(timeToLive);
    dataOutput.writeUTF(transportLayerProtocol);
    dataOutput.writeInt(headerChecksum);
    dataOutput.writeUTF(ipSourceAddress);
    dataOutput.writeUTF(ipDestinationAddress);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    ipVersion = dataInput.readInt();
    diffServ = dataInput.readInt();
    ipTotalLen = dataInput.readInt();
    identification = dataInput.readInt();
    timeToLive = dataInput.readInt();
    transportLayerProtocol  = dataInput.readUTF();
    headerChecksum  = dataInput.readInt();
    ipSourceAddress  = dataInput.readUTF();
    ipDestinationAddress  = dataInput.readUTF();
  }

  public int getIpVersion() {
    return ipVersion;
  }

  public void setIpVersion(int ipVersion) {
    this.ipVersion = ipVersion;
  }

  public int getDiffServ() {
    return diffServ;
  }

  public void setDiffServ(int diffServ) {
    this.diffServ = diffServ;
  }

  public int getIpTotalLen() {
    return ipTotalLen;
  }

  public void setIpTotalLen(int ipTotalLen) {
    this.ipTotalLen = ipTotalLen;
  }

  public int getIdentification() {
    return identification;
  }

  public void setIdentification(int identification) {
    this.identification = identification;
  }

  public int getTimeToLive() {
    return timeToLive;
  }

  public void setTimeToLive(int timeToLive) {
    this.timeToLive = timeToLive;
  }

  public String getTransportLayerProtocol() {
    return transportLayerProtocol;
  }

  public void setTransportLayerProtocol(String transportLayerProtocol) {
    this.transportLayerProtocol = transportLayerProtocol;
  }

  public int getHeaderChecksum() {
    return headerChecksum;
  }

  public void setHeaderChecksum(int headerChecksum) {
    this.headerChecksum = headerChecksum;
  }

  public String getIpSourceAddress() {
    return ipSourceAddress;
  }

  public void setIpSourceAddress(String ipSourceAddress) {
    this.ipSourceAddress = ipSourceAddress;
  }

  public String getIpDestinationAddress() {
    return ipDestinationAddress;
  }

  public void setIpDestinationAddress(String ipDestinationAddress) {
    this.ipDestinationAddress = ipDestinationAddress;
  }
}
