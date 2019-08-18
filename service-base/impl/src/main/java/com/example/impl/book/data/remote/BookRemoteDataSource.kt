package com.example.impl.book.data.remote

import android.content.Context
import com.example.impl.book.data.IBookDataSource
import com.example.liushengquan.douban.api.ApiRetrofit
import com.example.liushengquan.douban.api.BookApi
import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable

class BookRemoteDataSource private constructor(context: Context) : IBookDataSource {

    private var mBookApi: BookApi? = ApiRetrofit.getInstance(context).getApiService(BookApi::class.java) as BookApi

    companion object {
        @Volatile
        var mBookRemoteDataSource: BookRemoteDataSource? = null

        fun getInstance(context: Context): IBookDataSource {
            if (mBookRemoteDataSource == null) {
                synchronized(BookRemoteDataSource::class) {
                    if (mBookRemoteDataSource == null) {
                        mBookRemoteDataSource = BookRemoteDataSource(context)
                    }
                }
            }
            return mBookRemoteDataSource!!
        }
    }

    override fun searchBookByTag(tag: String, refreshFromServer: Boolean?): Observable<Books> {
        return mBookApi?.searchBookByTag(tag)!!
    }

    override fun getBookDetail(id: String, refreshFromServer: Boolean?): Observable<Book> {
        return mBookApi?.getBookDetail(id)!!
    }
}