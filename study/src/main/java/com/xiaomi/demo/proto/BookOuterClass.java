// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Book.proto

package com.xiaomi.demo.proto;

public final class BookOuterClass {
  private BookOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Book_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Book_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BookRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BookRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BookResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BookResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BookResponse_BookMapEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BookResponse_BookMapEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nBook.proto\032\036google/protobuf/wrappers.p" +
      "roto\" \n\004Book\022\n\n\002id\030\001 \001(\003\022\014\n\004name\030\002 \001(\t\"S" +
      "\n\013BookRequest\022\n\n\002id\030\001 \001(\003\022/\n\004name\030\002 \001(\0132" +
      "\034.google.protobuf.StringValueH\000\210\001\001B\007\n\005_n" +
      "ame\"\241\001\n\014BookResponse\022\024\n\005books\030\001 \003(\0132\005.Bo" +
      "ok\022\027\n\006status\030\002 \001(\0162\007.Status\022+\n\007bookMap\030\003" +
      " \003(\0132\032.BookResponse.BookMapEntry\0325\n\014Book" +
      "MapEntry\022\013\n\003key\030\001 \001(\t\022\024\n\005value\030\002 \001(\0132\005.B" +
      "ook:\0028\001*+\n\006Status\022\013\n\007SUCCESS\020\000\022\n\n\006FAILED" +
      "\020\001\022\010\n\004DEAD\020\0022\275\001\n\013BookService\022(\n\tgetBooks" +
      "1\022\014.BookRequest\032\r.BookResponse\022*\n\tgetBoo" +
      "ks2\022\014.BookRequest\032\r.BookResponse0\001\022*\n\tge" +
      "tBooks3\022\014.BookRequest\032\r.BookResponse(\001\022," +
      "\n\tgetBooks4\022\014.BookRequest\032\r.BookResponse" +
      "(\0010\001B\031\n\025com.xiaomi.demo.protoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.WrappersProto.getDescriptor(),
        });
    internal_static_Book_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Book_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Book_descriptor,
        new java.lang.String[] { "Id", "Name", });
    internal_static_BookRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_BookRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BookRequest_descriptor,
        new java.lang.String[] { "Id", "Name", "Name", });
    internal_static_BookResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_BookResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BookResponse_descriptor,
        new java.lang.String[] { "Books", "Status", "BookMap", });
    internal_static_BookResponse_BookMapEntry_descriptor =
      internal_static_BookResponse_descriptor.getNestedTypes().get(0);
    internal_static_BookResponse_BookMapEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BookResponse_BookMapEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    com.google.protobuf.WrappersProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
