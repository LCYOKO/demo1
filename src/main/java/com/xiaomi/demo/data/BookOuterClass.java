// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/Book.proto

package com.xiaomi.demo.data;

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
    internal_static_com_xiaomi_demo_data_Book_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_xiaomi_demo_data_Book_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020proto/Book.proto\022\024com.xiaomi.demo.data" +
      "\"*\n\004Book\022\014\n\004name\030\002 \001(\t\022\016\n\006scores\030\003 \003(\005J\004" +
      "\010\001\020\002B\030\n\024com.xiaomi.demo.dataP\001b\006proto3"
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
    internal_static_com_xiaomi_demo_data_Book_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_xiaomi_demo_data_Book_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_xiaomi_demo_data_Book_descriptor,
        new java.lang.String[] { "Name", "Scores", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}