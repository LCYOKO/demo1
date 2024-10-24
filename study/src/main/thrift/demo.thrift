namespace java com.xiaomi.demo.thrift

enum BizCode{

   SUCCESS=200,

   INTERNAL_ERROR=500

}

exception BizException{
 1: string msg,
 2: BizCode code
}


struct BookInfo{
 1: required i32 bookId
 2: string bookName
}

struct Book{
 1: required i32 bookId,
 2: BookInfo info
}


service BookService{
   BookInfo getBookInfo(1: i32 bookId);
   list<Book> getAllBook();
}


service HelloService{

    string HelloString(1:string para)

    i32 HelloInt(1:i32 para)

    bool HelloBoolean(1:bool para)

    void HelloVoid()

    oneway void HelloOneway()

    string HelloNull()
}