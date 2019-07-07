package com.example.impl.book.data

import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable

interface IBookDataSource {
    fun searchBookByTag(tag: String, refreshFromServer: Boolean?): Observable<Books>
    fun getBookDetail(id: String, refreshFromServer: Boolean?): Observable<Book>
}