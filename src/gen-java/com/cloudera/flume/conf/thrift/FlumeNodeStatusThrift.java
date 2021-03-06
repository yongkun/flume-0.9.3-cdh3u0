/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.cloudera.flume.conf.thrift;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlumeNodeStatusThrift implements org.apache.thrift.TBase<FlumeNodeStatusThrift, FlumeNodeStatusThrift._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FlumeNodeStatusThrift");

  private static final org.apache.thrift.protocol.TField STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("state", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField LASTSEEN_FIELD_DESC = new org.apache.thrift.protocol.TField("lastseen", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField LAST_SEEN_DELTA_MILLIS_FIELD_DESC = new org.apache.thrift.protocol.TField("lastSeenDeltaMillis", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField HOST_FIELD_DESC = new org.apache.thrift.protocol.TField("host", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PHYSICAL_NODE_FIELD_DESC = new org.apache.thrift.protocol.TField("physicalNode", org.apache.thrift.protocol.TType.STRING, (short)5);

  /**
   * 
   * @see com.cloudera.flume.conf.thrift.FlumeNodeState
   */
  public com.cloudera.flume.conf.thrift.FlumeNodeState state; // required
  public long version; // required
  public long lastseen; // required
  public long lastSeenDeltaMillis; // required
  public String host; // required
  public String physicalNode; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see com.cloudera.flume.conf.thrift.FlumeNodeState
     */
    STATE((short)1, "state"),
    VERSION((short)2, "version"),
    LASTSEEN((short)3, "lastseen"),
    LAST_SEEN_DELTA_MILLIS((short)6, "lastSeenDeltaMillis"),
    HOST((short)4, "host"),
    PHYSICAL_NODE((short)5, "physicalNode");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // STATE
          return STATE;
        case 2: // VERSION
          return VERSION;
        case 3: // LASTSEEN
          return LASTSEEN;
        case 6: // LAST_SEEN_DELTA_MILLIS
          return LAST_SEEN_DELTA_MILLIS;
        case 4: // HOST
          return HOST;
        case 5: // PHYSICAL_NODE
          return PHYSICAL_NODE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VERSION_ISSET_ID = 0;
  private static final int __LASTSEEN_ISSET_ID = 1;
  private static final int __LASTSEENDELTAMILLIS_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STATE, new org.apache.thrift.meta_data.FieldMetaData("state", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.cloudera.flume.conf.thrift.FlumeNodeState.class)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LASTSEEN, new org.apache.thrift.meta_data.FieldMetaData("lastseen", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LAST_SEEN_DELTA_MILLIS, new org.apache.thrift.meta_data.FieldMetaData("lastSeenDeltaMillis", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.HOST, new org.apache.thrift.meta_data.FieldMetaData("host", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PHYSICAL_NODE, new org.apache.thrift.meta_data.FieldMetaData("physicalNode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FlumeNodeStatusThrift.class, metaDataMap);
  }

  public FlumeNodeStatusThrift() {
  }

  public FlumeNodeStatusThrift(
    com.cloudera.flume.conf.thrift.FlumeNodeState state,
    long version,
    long lastseen,
    long lastSeenDeltaMillis,
    String host,
    String physicalNode)
  {
    this();
    this.state = state;
    this.version = version;
    setVersionIsSet(true);
    this.lastseen = lastseen;
    setLastseenIsSet(true);
    this.lastSeenDeltaMillis = lastSeenDeltaMillis;
    setLastSeenDeltaMillisIsSet(true);
    this.host = host;
    this.physicalNode = physicalNode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FlumeNodeStatusThrift(FlumeNodeStatusThrift other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetState()) {
      this.state = other.state;
    }
    this.version = other.version;
    this.lastseen = other.lastseen;
    this.lastSeenDeltaMillis = other.lastSeenDeltaMillis;
    if (other.isSetHost()) {
      this.host = other.host;
    }
    if (other.isSetPhysicalNode()) {
      this.physicalNode = other.physicalNode;
    }
  }

  public FlumeNodeStatusThrift deepCopy() {
    return new FlumeNodeStatusThrift(this);
  }

  @Override
  public void clear() {
    this.state = null;
    setVersionIsSet(false);
    this.version = 0;
    setLastseenIsSet(false);
    this.lastseen = 0;
    setLastSeenDeltaMillisIsSet(false);
    this.lastSeenDeltaMillis = 0;
    this.host = null;
    this.physicalNode = null;
  }

  /**
   * 
   * @see com.cloudera.flume.conf.thrift.FlumeNodeState
   */
  public com.cloudera.flume.conf.thrift.FlumeNodeState getState() {
    return this.state;
  }

  /**
   * 
   * @see com.cloudera.flume.conf.thrift.FlumeNodeState
   */
  public FlumeNodeStatusThrift setState(com.cloudera.flume.conf.thrift.FlumeNodeState state) {
    this.state = state;
    return this;
  }

  public void unsetState() {
    this.state = null;
  }

  /** Returns true if field state is set (has been assigned a value) and false otherwise */
  public boolean isSetState() {
    return this.state != null;
  }

  public void setStateIsSet(boolean value) {
    if (!value) {
      this.state = null;
    }
  }

  public long getVersion() {
    return this.version;
  }

  public FlumeNodeStatusThrift setVersion(long version) {
    this.version = version;
    setVersionIsSet(true);
    return this;
  }

  public void unsetVersion() {
    __isset_bit_vector.clear(__VERSION_ISSET_ID);
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return __isset_bit_vector.get(__VERSION_ISSET_ID);
  }

  public void setVersionIsSet(boolean value) {
    __isset_bit_vector.set(__VERSION_ISSET_ID, value);
  }

  public long getLastseen() {
    return this.lastseen;
  }

  public FlumeNodeStatusThrift setLastseen(long lastseen) {
    this.lastseen = lastseen;
    setLastseenIsSet(true);
    return this;
  }

  public void unsetLastseen() {
    __isset_bit_vector.clear(__LASTSEEN_ISSET_ID);
  }

  /** Returns true if field lastseen is set (has been assigned a value) and false otherwise */
  public boolean isSetLastseen() {
    return __isset_bit_vector.get(__LASTSEEN_ISSET_ID);
  }

  public void setLastseenIsSet(boolean value) {
    __isset_bit_vector.set(__LASTSEEN_ISSET_ID, value);
  }

  public long getLastSeenDeltaMillis() {
    return this.lastSeenDeltaMillis;
  }

  public FlumeNodeStatusThrift setLastSeenDeltaMillis(long lastSeenDeltaMillis) {
    this.lastSeenDeltaMillis = lastSeenDeltaMillis;
    setLastSeenDeltaMillisIsSet(true);
    return this;
  }

  public void unsetLastSeenDeltaMillis() {
    __isset_bit_vector.clear(__LASTSEENDELTAMILLIS_ISSET_ID);
  }

  /** Returns true if field lastSeenDeltaMillis is set (has been assigned a value) and false otherwise */
  public boolean isSetLastSeenDeltaMillis() {
    return __isset_bit_vector.get(__LASTSEENDELTAMILLIS_ISSET_ID);
  }

  public void setLastSeenDeltaMillisIsSet(boolean value) {
    __isset_bit_vector.set(__LASTSEENDELTAMILLIS_ISSET_ID, value);
  }

  public String getHost() {
    return this.host;
  }

  public FlumeNodeStatusThrift setHost(String host) {
    this.host = host;
    return this;
  }

  public void unsetHost() {
    this.host = null;
  }

  /** Returns true if field host is set (has been assigned a value) and false otherwise */
  public boolean isSetHost() {
    return this.host != null;
  }

  public void setHostIsSet(boolean value) {
    if (!value) {
      this.host = null;
    }
  }

  public String getPhysicalNode() {
    return this.physicalNode;
  }

  public FlumeNodeStatusThrift setPhysicalNode(String physicalNode) {
    this.physicalNode = physicalNode;
    return this;
  }

  public void unsetPhysicalNode() {
    this.physicalNode = null;
  }

  /** Returns true if field physicalNode is set (has been assigned a value) and false otherwise */
  public boolean isSetPhysicalNode() {
    return this.physicalNode != null;
  }

  public void setPhysicalNodeIsSet(boolean value) {
    if (!value) {
      this.physicalNode = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((com.cloudera.flume.conf.thrift.FlumeNodeState)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((Long)value);
      }
      break;

    case LASTSEEN:
      if (value == null) {
        unsetLastseen();
      } else {
        setLastseen((Long)value);
      }
      break;

    case LAST_SEEN_DELTA_MILLIS:
      if (value == null) {
        unsetLastSeenDeltaMillis();
      } else {
        setLastSeenDeltaMillis((Long)value);
      }
      break;

    case HOST:
      if (value == null) {
        unsetHost();
      } else {
        setHost((String)value);
      }
      break;

    case PHYSICAL_NODE:
      if (value == null) {
        unsetPhysicalNode();
      } else {
        setPhysicalNode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STATE:
      return getState();

    case VERSION:
      return Long.valueOf(getVersion());

    case LASTSEEN:
      return Long.valueOf(getLastseen());

    case LAST_SEEN_DELTA_MILLIS:
      return Long.valueOf(getLastSeenDeltaMillis());

    case HOST:
      return getHost();

    case PHYSICAL_NODE:
      return getPhysicalNode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STATE:
      return isSetState();
    case VERSION:
      return isSetVersion();
    case LASTSEEN:
      return isSetLastseen();
    case LAST_SEEN_DELTA_MILLIS:
      return isSetLastSeenDeltaMillis();
    case HOST:
      return isSetHost();
    case PHYSICAL_NODE:
      return isSetPhysicalNode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FlumeNodeStatusThrift)
      return this.equals((FlumeNodeStatusThrift)that);
    return false;
  }

  public boolean equals(FlumeNodeStatusThrift that) {
    if (that == null)
      return false;

    boolean this_present_state = true && this.isSetState();
    boolean that_present_state = true && that.isSetState();
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (!this.state.equals(that.state))
        return false;
    }

    boolean this_present_version = true;
    boolean that_present_version = true;
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (this.version != that.version)
        return false;
    }

    boolean this_present_lastseen = true;
    boolean that_present_lastseen = true;
    if (this_present_lastseen || that_present_lastseen) {
      if (!(this_present_lastseen && that_present_lastseen))
        return false;
      if (this.lastseen != that.lastseen)
        return false;
    }

    boolean this_present_lastSeenDeltaMillis = true;
    boolean that_present_lastSeenDeltaMillis = true;
    if (this_present_lastSeenDeltaMillis || that_present_lastSeenDeltaMillis) {
      if (!(this_present_lastSeenDeltaMillis && that_present_lastSeenDeltaMillis))
        return false;
      if (this.lastSeenDeltaMillis != that.lastSeenDeltaMillis)
        return false;
    }

    boolean this_present_host = true && this.isSetHost();
    boolean that_present_host = true && that.isSetHost();
    if (this_present_host || that_present_host) {
      if (!(this_present_host && that_present_host))
        return false;
      if (!this.host.equals(that.host))
        return false;
    }

    boolean this_present_physicalNode = true && this.isSetPhysicalNode();
    boolean that_present_physicalNode = true && that.isSetPhysicalNode();
    if (this_present_physicalNode || that_present_physicalNode) {
      if (!(this_present_physicalNode && that_present_physicalNode))
        return false;
      if (!this.physicalNode.equals(that.physicalNode))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(FlumeNodeStatusThrift other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    FlumeNodeStatusThrift typedOther = (FlumeNodeStatusThrift)other;

    lastComparison = Boolean.valueOf(isSetState()).compareTo(typedOther.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.state, typedOther.state);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(typedOther.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, typedOther.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastseen()).compareTo(typedOther.isSetLastseen());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastseen()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastseen, typedOther.lastseen);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastSeenDeltaMillis()).compareTo(typedOther.isSetLastSeenDeltaMillis());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastSeenDeltaMillis()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastSeenDeltaMillis, typedOther.lastSeenDeltaMillis);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHost()).compareTo(typedOther.isSetHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.host, typedOther.host);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhysicalNode()).compareTo(typedOther.isSetPhysicalNode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhysicalNode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.physicalNode, typedOther.physicalNode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // STATE
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.state = com.cloudera.flume.conf.thrift.FlumeNodeState.findByValue(iprot.readI32());
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // VERSION
          if (field.type == org.apache.thrift.protocol.TType.I64) {
            this.version = iprot.readI64();
            setVersionIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // LASTSEEN
          if (field.type == org.apache.thrift.protocol.TType.I64) {
            this.lastseen = iprot.readI64();
            setLastseenIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // LAST_SEEN_DELTA_MILLIS
          if (field.type == org.apache.thrift.protocol.TType.I64) {
            this.lastSeenDeltaMillis = iprot.readI64();
            setLastSeenDeltaMillisIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // HOST
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.host = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // PHYSICAL_NODE
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.physicalNode = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.state != null) {
      oprot.writeFieldBegin(STATE_FIELD_DESC);
      oprot.writeI32(this.state.getValue());
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(VERSION_FIELD_DESC);
    oprot.writeI64(this.version);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(LASTSEEN_FIELD_DESC);
    oprot.writeI64(this.lastseen);
    oprot.writeFieldEnd();
    if (this.host != null) {
      oprot.writeFieldBegin(HOST_FIELD_DESC);
      oprot.writeString(this.host);
      oprot.writeFieldEnd();
    }
    if (this.physicalNode != null) {
      oprot.writeFieldBegin(PHYSICAL_NODE_FIELD_DESC);
      oprot.writeString(this.physicalNode);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(LAST_SEEN_DELTA_MILLIS_FIELD_DESC);
    oprot.writeI64(this.lastSeenDeltaMillis);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FlumeNodeStatusThrift(");
    boolean first = true;

    sb.append("state:");
    if (this.state == null) {
      sb.append("null");
    } else {
      sb.append(this.state);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    sb.append(this.version);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastseen:");
    sb.append(this.lastseen);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastSeenDeltaMillis:");
    sb.append(this.lastSeenDeltaMillis);
    first = false;
    if (!first) sb.append(", ");
    sb.append("host:");
    if (this.host == null) {
      sb.append("null");
    } else {
      sb.append(this.host);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("physicalNode:");
    if (this.physicalNode == null) {
      sb.append("null");
    } else {
      sb.append(this.physicalNode);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

