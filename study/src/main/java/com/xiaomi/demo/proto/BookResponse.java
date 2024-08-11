// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: book.proto

package com.xiaomi.demo.proto;

/**
 * Protobuf type {@code book.BookResponse}
 */
public final class BookResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:book.BookResponse)
    BookResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BookResponse.newBuilder() to construct.
  private BookResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BookResponse() {
    books_ = java.util.Collections.emptyList();
    status_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BookResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BookResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              books_ = new java.util.ArrayList<com.xiaomi.demo.proto.Book>();
              mutable_bitField0_ |= 0x00000001;
            }
            books_.add(
                input.readMessage(com.xiaomi.demo.proto.Book.parser(), extensionRegistry));
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            status_ = rawValue;
            break;
          }
          case 26: {
            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
              bookMap_ = com.google.protobuf.MapField.newMapField(
                  BookMapDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.String, com.xiaomi.demo.proto.Book>
            bookMap__ = input.readMessage(
                BookMapDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            bookMap_.getMutableMap().put(
                bookMap__.getKey(), bookMap__.getValue());
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        books_ = java.util.Collections.unmodifiableList(books_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  @java.lang.Override
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 3:
        return internalGetBookMap();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.xiaomi.demo.proto.BookResponse.class, com.xiaomi.demo.proto.BookResponse.Builder.class);
  }

  public static final int BOOKS_FIELD_NUMBER = 1;
  private java.util.List<com.xiaomi.demo.proto.Book> books_;
  /**
   * <code>repeated .book.Book books = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.xiaomi.demo.proto.Book> getBooksList() {
    return books_;
  }
  /**
   * <code>repeated .book.Book books = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.xiaomi.demo.proto.BookOrBuilder> 
      getBooksOrBuilderList() {
    return books_;
  }
  /**
   * <code>repeated .book.Book books = 1;</code>
   */
  @java.lang.Override
  public int getBooksCount() {
    return books_.size();
  }
  /**
   * <code>repeated .book.Book books = 1;</code>
   */
  @java.lang.Override
  public com.xiaomi.demo.proto.Book getBooks(int index) {
    return books_.get(index);
  }
  /**
   * <code>repeated .book.Book books = 1;</code>
   */
  @java.lang.Override
  public com.xiaomi.demo.proto.BookOrBuilder getBooksOrBuilder(
      int index) {
    return books_.get(index);
  }

  public static final int STATUS_FIELD_NUMBER = 2;
  private int status_;
  /**
   * <code>.book.Status status = 2;</code>
   * @return The enum numeric value on the wire for status.
   */
  @java.lang.Override public int getStatusValue() {
    return status_;
  }
  /**
   * <code>.book.Status status = 2;</code>
   * @return The status.
   */
  @java.lang.Override public com.xiaomi.demo.proto.Status getStatus() {
    @SuppressWarnings("deprecation")
    com.xiaomi.demo.proto.Status result = com.xiaomi.demo.proto.Status.valueOf(status_);
    return result == null ? com.xiaomi.demo.proto.Status.UNRECOGNIZED : result;
  }

  public static final int BOOKMAP_FIELD_NUMBER = 3;
  private static final class BookMapDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.String, com.xiaomi.demo.proto.Book> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.String, com.xiaomi.demo.proto.Book>newDefaultInstance(
                com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_BookMapEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                com.xiaomi.demo.proto.Book.getDefaultInstance());
  }
  private com.google.protobuf.MapField<
      java.lang.String, com.xiaomi.demo.proto.Book> bookMap_;
  private com.google.protobuf.MapField<java.lang.String, com.xiaomi.demo.proto.Book>
  internalGetBookMap() {
    if (bookMap_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          BookMapDefaultEntryHolder.defaultEntry);
    }
    return bookMap_;
  }

  public int getBookMapCount() {
    return internalGetBookMap().getMap().size();
  }
  /**
   * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
   */

  @java.lang.Override
  public boolean containsBookMap(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    return internalGetBookMap().getMap().containsKey(key);
  }
  /**
   * Use {@link #getBookMapMap()} instead.
   */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> getBookMap() {
    return getBookMapMap();
  }
  /**
   * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
   */
  @java.lang.Override

  public java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> getBookMapMap() {
    return internalGetBookMap().getMap();
  }
  /**
   * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
   */
  @java.lang.Override

  public com.xiaomi.demo.proto.Book getBookMapOrDefault(
      java.lang.String key,
      com.xiaomi.demo.proto.Book defaultValue) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> map =
        internalGetBookMap().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
   */
  @java.lang.Override

  public com.xiaomi.demo.proto.Book getBookMapOrThrow(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> map =
        internalGetBookMap().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < books_.size(); i++) {
      output.writeMessage(1, books_.get(i));
    }
    if (status_ != com.xiaomi.demo.proto.Status.SUCCESS.getNumber()) {
      output.writeEnum(2, status_);
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetBookMap(),
        BookMapDefaultEntryHolder.defaultEntry,
        3);
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < books_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, books_.get(i));
    }
    if (status_ != com.xiaomi.demo.proto.Status.SUCCESS.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, status_);
    }
    for (java.util.Map.Entry<java.lang.String, com.xiaomi.demo.proto.Book> entry
         : internalGetBookMap().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, com.xiaomi.demo.proto.Book>
      bookMap__ = BookMapDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, bookMap__);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.xiaomi.demo.proto.BookResponse)) {
      return super.equals(obj);
    }
    com.xiaomi.demo.proto.BookResponse other = (com.xiaomi.demo.proto.BookResponse) obj;

    if (!getBooksList()
        .equals(other.getBooksList())) return false;
    if (status_ != other.status_) return false;
    if (!internalGetBookMap().equals(
        other.internalGetBookMap())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getBooksCount() > 0) {
      hash = (37 * hash) + BOOKS_FIELD_NUMBER;
      hash = (53 * hash) + getBooksList().hashCode();
    }
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + status_;
    if (!internalGetBookMap().getMap().isEmpty()) {
      hash = (37 * hash) + BOOKMAP_FIELD_NUMBER;
      hash = (53 * hash) + internalGetBookMap().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.xiaomi.demo.proto.BookResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.xiaomi.demo.proto.BookResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.xiaomi.demo.proto.BookResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.xiaomi.demo.proto.BookResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code book.BookResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:book.BookResponse)
      com.xiaomi.demo.proto.BookResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 3:
          return internalGetBookMap();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 3:
          return internalGetMutableBookMap();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.xiaomi.demo.proto.BookResponse.class, com.xiaomi.demo.proto.BookResponse.Builder.class);
    }

    // Construct using com.xiaomi.demo.proto.BookResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getBooksFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (booksBuilder_ == null) {
        books_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        booksBuilder_.clear();
      }
      status_ = 0;

      internalGetMutableBookMap().clear();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.xiaomi.demo.proto.BookOuterClass.internal_static_book_BookResponse_descriptor;
    }

    @java.lang.Override
    public com.xiaomi.demo.proto.BookResponse getDefaultInstanceForType() {
      return com.xiaomi.demo.proto.BookResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.xiaomi.demo.proto.BookResponse build() {
      com.xiaomi.demo.proto.BookResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.xiaomi.demo.proto.BookResponse buildPartial() {
      com.xiaomi.demo.proto.BookResponse result = new com.xiaomi.demo.proto.BookResponse(this);
      int from_bitField0_ = bitField0_;
      if (booksBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          books_ = java.util.Collections.unmodifiableList(books_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.books_ = books_;
      } else {
        result.books_ = booksBuilder_.build();
      }
      result.status_ = status_;
      result.bookMap_ = internalGetBookMap();
      result.bookMap_.makeImmutable();
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.xiaomi.demo.proto.BookResponse) {
        return mergeFrom((com.xiaomi.demo.proto.BookResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.xiaomi.demo.proto.BookResponse other) {
      if (other == com.xiaomi.demo.proto.BookResponse.getDefaultInstance()) return this;
      if (booksBuilder_ == null) {
        if (!other.books_.isEmpty()) {
          if (books_.isEmpty()) {
            books_ = other.books_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBooksIsMutable();
            books_.addAll(other.books_);
          }
          onChanged();
        }
      } else {
        if (!other.books_.isEmpty()) {
          if (booksBuilder_.isEmpty()) {
            booksBuilder_.dispose();
            booksBuilder_ = null;
            books_ = other.books_;
            bitField0_ = (bitField0_ & ~0x00000001);
            booksBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBooksFieldBuilder() : null;
          } else {
            booksBuilder_.addAllMessages(other.books_);
          }
        }
      }
      if (other.status_ != 0) {
        setStatusValue(other.getStatusValue());
      }
      internalGetMutableBookMap().mergeFrom(
          other.internalGetBookMap());
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.xiaomi.demo.proto.BookResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.xiaomi.demo.proto.BookResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.xiaomi.demo.proto.Book> books_ =
      java.util.Collections.emptyList();
    private void ensureBooksIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        books_ = new java.util.ArrayList<com.xiaomi.demo.proto.Book>(books_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.xiaomi.demo.proto.Book, com.xiaomi.demo.proto.Book.Builder, com.xiaomi.demo.proto.BookOrBuilder> booksBuilder_;

    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public java.util.List<com.xiaomi.demo.proto.Book> getBooksList() {
      if (booksBuilder_ == null) {
        return java.util.Collections.unmodifiableList(books_);
      } else {
        return booksBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public int getBooksCount() {
      if (booksBuilder_ == null) {
        return books_.size();
      } else {
        return booksBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public com.xiaomi.demo.proto.Book getBooks(int index) {
      if (booksBuilder_ == null) {
        return books_.get(index);
      } else {
        return booksBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder setBooks(
        int index, com.xiaomi.demo.proto.Book value) {
      if (booksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBooksIsMutable();
        books_.set(index, value);
        onChanged();
      } else {
        booksBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder setBooks(
        int index, com.xiaomi.demo.proto.Book.Builder builderForValue) {
      if (booksBuilder_ == null) {
        ensureBooksIsMutable();
        books_.set(index, builderForValue.build());
        onChanged();
      } else {
        booksBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder addBooks(com.xiaomi.demo.proto.Book value) {
      if (booksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBooksIsMutable();
        books_.add(value);
        onChanged();
      } else {
        booksBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder addBooks(
        int index, com.xiaomi.demo.proto.Book value) {
      if (booksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBooksIsMutable();
        books_.add(index, value);
        onChanged();
      } else {
        booksBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder addBooks(
        com.xiaomi.demo.proto.Book.Builder builderForValue) {
      if (booksBuilder_ == null) {
        ensureBooksIsMutable();
        books_.add(builderForValue.build());
        onChanged();
      } else {
        booksBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder addBooks(
        int index, com.xiaomi.demo.proto.Book.Builder builderForValue) {
      if (booksBuilder_ == null) {
        ensureBooksIsMutable();
        books_.add(index, builderForValue.build());
        onChanged();
      } else {
        booksBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder addAllBooks(
        java.lang.Iterable<? extends com.xiaomi.demo.proto.Book> values) {
      if (booksBuilder_ == null) {
        ensureBooksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, books_);
        onChanged();
      } else {
        booksBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder clearBooks() {
      if (booksBuilder_ == null) {
        books_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        booksBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public Builder removeBooks(int index) {
      if (booksBuilder_ == null) {
        ensureBooksIsMutable();
        books_.remove(index);
        onChanged();
      } else {
        booksBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public com.xiaomi.demo.proto.Book.Builder getBooksBuilder(
        int index) {
      return getBooksFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public com.xiaomi.demo.proto.BookOrBuilder getBooksOrBuilder(
        int index) {
      if (booksBuilder_ == null) {
        return books_.get(index);  } else {
        return booksBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public java.util.List<? extends com.xiaomi.demo.proto.BookOrBuilder> 
         getBooksOrBuilderList() {
      if (booksBuilder_ != null) {
        return booksBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(books_);
      }
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public com.xiaomi.demo.proto.Book.Builder addBooksBuilder() {
      return getBooksFieldBuilder().addBuilder(
          com.xiaomi.demo.proto.Book.getDefaultInstance());
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public com.xiaomi.demo.proto.Book.Builder addBooksBuilder(
        int index) {
      return getBooksFieldBuilder().addBuilder(
          index, com.xiaomi.demo.proto.Book.getDefaultInstance());
    }
    /**
     * <code>repeated .book.Book books = 1;</code>
     */
    public java.util.List<com.xiaomi.demo.proto.Book.Builder> 
         getBooksBuilderList() {
      return getBooksFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.xiaomi.demo.proto.Book, com.xiaomi.demo.proto.Book.Builder, com.xiaomi.demo.proto.BookOrBuilder> 
        getBooksFieldBuilder() {
      if (booksBuilder_ == null) {
        booksBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.xiaomi.demo.proto.Book, com.xiaomi.demo.proto.Book.Builder, com.xiaomi.demo.proto.BookOrBuilder>(
                books_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        books_ = null;
      }
      return booksBuilder_;
    }

    private int status_ = 0;
    /**
     * <code>.book.Status status = 2;</code>
     * @return The enum numeric value on the wire for status.
     */
    @java.lang.Override public int getStatusValue() {
      return status_;
    }
    /**
     * <code>.book.Status status = 2;</code>
     * @param value The enum numeric value on the wire for status to set.
     * @return This builder for chaining.
     */
    public Builder setStatusValue(int value) {
      
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.book.Status status = 2;</code>
     * @return The status.
     */
    @java.lang.Override
    public com.xiaomi.demo.proto.Status getStatus() {
      @SuppressWarnings("deprecation")
      com.xiaomi.demo.proto.Status result = com.xiaomi.demo.proto.Status.valueOf(status_);
      return result == null ? com.xiaomi.demo.proto.Status.UNRECOGNIZED : result;
    }
    /**
     * <code>.book.Status status = 2;</code>
     * @param value The status to set.
     * @return This builder for chaining.
     */
    public Builder setStatus(com.xiaomi.demo.proto.Status value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      status_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.book.Status status = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearStatus() {
      
      status_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.MapField<
        java.lang.String, com.xiaomi.demo.proto.Book> bookMap_;
    private com.google.protobuf.MapField<java.lang.String, com.xiaomi.demo.proto.Book>
    internalGetBookMap() {
      if (bookMap_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            BookMapDefaultEntryHolder.defaultEntry);
      }
      return bookMap_;
    }
    private com.google.protobuf.MapField<java.lang.String, com.xiaomi.demo.proto.Book>
    internalGetMutableBookMap() {
      onChanged();;
      if (bookMap_ == null) {
        bookMap_ = com.google.protobuf.MapField.newMapField(
            BookMapDefaultEntryHolder.defaultEntry);
      }
      if (!bookMap_.isMutable()) {
        bookMap_ = bookMap_.copy();
      }
      return bookMap_;
    }

    public int getBookMapCount() {
      return internalGetBookMap().getMap().size();
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */

    @java.lang.Override
    public boolean containsBookMap(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetBookMap().getMap().containsKey(key);
    }
    /**
     * Use {@link #getBookMapMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> getBookMap() {
      return getBookMapMap();
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */
    @java.lang.Override

    public java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> getBookMapMap() {
      return internalGetBookMap().getMap();
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */
    @java.lang.Override

    public com.xiaomi.demo.proto.Book getBookMapOrDefault(
        java.lang.String key,
        com.xiaomi.demo.proto.Book defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> map =
          internalGetBookMap().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */
    @java.lang.Override

    public com.xiaomi.demo.proto.Book getBookMapOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> map =
          internalGetBookMap().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearBookMap() {
      internalGetMutableBookMap().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */

    public Builder removeBookMap(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableBookMap().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book>
    getMutableBookMap() {
      return internalGetMutableBookMap().getMutableMap();
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */
    public Builder putBookMap(
        java.lang.String key,
        com.xiaomi.demo.proto.Book value) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      if (value == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableBookMap().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .book.Book&gt; bookMap = 3;</code>
     */

    public Builder putAllBookMap(
        java.util.Map<java.lang.String, com.xiaomi.demo.proto.Book> values) {
      internalGetMutableBookMap().getMutableMap()
          .putAll(values);
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:book.BookResponse)
  }

  // @@protoc_insertion_point(class_scope:book.BookResponse)
  private static final com.xiaomi.demo.proto.BookResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.xiaomi.demo.proto.BookResponse();
  }

  public static com.xiaomi.demo.proto.BookResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BookResponse>
      PARSER = new com.google.protobuf.AbstractParser<BookResponse>() {
    @java.lang.Override
    public BookResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BookResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BookResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BookResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.xiaomi.demo.proto.BookResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

