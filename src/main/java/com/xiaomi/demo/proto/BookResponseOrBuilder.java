// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/Book1.proto

package com.xiaomi.demo.proto;

public interface BookResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.xiaomi.demo.BookResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string bookName = 1;</code>
   */
  java.lang.String getBookName();
  /**
   * <code>string bookName = 1;</code>
   */
  com.google.protobuf.ByteString
      getBookNameBytes();

  /**
   * <code>int64 id = 2;</code>
   */
  long getId();

  /**
   * <code>.com.xiaomi.demo.ErrorCode code = 3;</code>
   */
  int getCodeValue();
  /**
   * <code>.com.xiaomi.demo.ErrorCode code = 3;</code>
   */
  com.xiaomi.demo.proto.ErrorCode getCode();

  /**
   * <code>string errorMsg = 4;</code>
   */
  java.lang.String getErrorMsg();
  /**
   * <code>string errorMsg = 4;</code>
   */
  com.google.protobuf.ByteString
      getErrorMsgBytes();
}
