package com.example.impl.book.data.remote

import android.content.Context
import com.example.impl.book.data.IBookDataSource
import com.example.liushengquan.douban.api.ApiRetrofit
import com.example.liushengquan.douban.api.BookApi
import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable

class BookRemoteDataSource private constructor(context: Context): IBookDataSource {

    private var mBookApi: BookApi? = ApiRetrofit.getInstance(context).getApiService(BookApi::class.java) as BookApi

    companion object {
        private lateinit var mLocalDataSource: BookRemoteDataSource

        fun getInstance(context: Context): IBookDataSource {
            if (mLocalDataSource == null) {
                mLocalDataSource = BookRemoteDataSource(context)
            }
            return mLocalDataSource
        }
    }

    override fun searchBookByTag(tag: String, refreshFromServer: Boolean?): Observable<Books> {
        return mBookApi?.searchBookByTag(tag)!!
    }

    override fun getBookDetail(id: String, refreshFromServer: Boolean?): Observable<Book> {
        return mBookApi?.getBookDetail(id)!!
    }
}