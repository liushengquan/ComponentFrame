package com.example.api.book

import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books

class BookEvent {

    class OnGetBooksEvent(val books: Books?, val msg: String?)


    class OnGetBookDetailEvent(val book: Book?, val msg: String?)

}
