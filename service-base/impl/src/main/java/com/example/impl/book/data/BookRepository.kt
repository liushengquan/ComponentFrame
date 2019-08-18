package com.example.impl.book.data

import android.content.Context
import com.example.impl.book.data.local.BookLocalDataSource
import com.example.impl.book.data.remote.BookRemoteDataSource
import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable

class BookRepository private constructor(context: Context) : IBookDataSource {

    private val mLocalBookDataSource: IBookDataSource = BookLocalDataSource.getInstance(context)
    private val mRemoteBookDataSource: IBookDataSource = BookRemoteDataSource.getInstance(context)

    companion object {
        @Volatile
        var mBookRepository: BookRepository? = null

        fun getInstance(context: Context): BookRepository {
            if (mBookRepository == null) {
                synchronized(BookRepository::class) {
                    if (mBookRepository == null) {
                        mBookRepository = BookRepository(context)
                    }
                }
            }
            return mBookRepository!!
        }
    }

    override fun searchBookByTag(tag: String, refreshFromServer: Boolean?): Observable<Books> {
        return if (refreshFromServer == null || !refreshFromServer)
            mLocalBookDataSource.searchBookByTag(tag, null)
        else
            mRemoteBookDataSource.searchBookByTag(tag, null)
    }

    override fun getBookDetail(id: String, refreshFromServer: Boolean?): Observable<Book> {
        return if (refreshFromServer == null || !refreshFromServer)
            mLocalBookDataSource.getBookDetail(id, null)
        else
            mRemoteBookDataSource.getBookDetail(id, null)
    }
}