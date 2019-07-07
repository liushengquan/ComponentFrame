package com.example.impl.book.data.local

import android.content.Context
import com.example.impl.book.data.IBookDataSource
import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable

class BookLocalDataSource private constructor(context: Context) : IBookDataSource {

    companion object {
        private lateinit var mLocalDataSource: BookLocalDataSource
        fun getInstance(context: Context): IBookDataSource {
            if (mLocalDataSource == null) {
                mLocalDataSource = BookLocalDataSource(context)
            }
            return mLocalDataSource
        }
    }


    override fun searchBookByTag(tag: String, refreshFromServer: Boolean?): Observable<Books> {
        val books = Books()
        return Observable.just(books)
    }

    override fun getBookDetail(id: String, refreshFromServer: Boolean?): Observable<Book> {
        val book = Book()
        return Observable.just(book)
    }
}