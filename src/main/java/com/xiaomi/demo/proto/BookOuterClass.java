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

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\nBook.proto\"(\n\004Book\022\016\n\006bookId\030\001 \001(\003\022\020\n\010" +
      "bookName\030\002 \001(\t\"\035\n\013BookRequest\022\016\n\006bookId\030" +
      "\001 \001(\003\"=\n\014BookResponse\022\024\n\005books\030\001 \003(\0132\005.B" +
      "ook\022\027\n\006status\030\002 \001(\0162\007.Status*!\n\006Status\022\013" +
      "\n\007SUCCESS\020\000\022\n\n\006FAILED\020\00128\n\013BookService\022)" +
      "\n\010getBooks\022\014.BookRequest\032\r.BookResponse0" +
      "\001B\031\n\025com.xiaomi.demo.protoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Book_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Book_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Book_descriptor,
        new String[] { "BookId", "BookName", });
    internal_static_BookRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_BookRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BookRequest_descriptor,
        new String[] { "BookId", });
    internal_static_BookResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_BookResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BookResponse_descriptor,
        new String[] { "Books", "Status", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
