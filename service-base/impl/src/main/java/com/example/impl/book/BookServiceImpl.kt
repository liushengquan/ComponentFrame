package com.example.impl.book

import android.content.Context
import com.example.api.book.bean.IBookService
import com.example.impl.book.data.BookRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BookServiceImpl private constructor(context: Context) : IBookService {

    private val mContext = context
    private var mRepository: BookRepository = BookRepository.getInstance(mContext)

    companion object {
        private lateinit var mBookService: BookServiceImpl

        fun init(context: Context): IBookService {
            mBookService = BookServiceImpl(context)
            return mBookService
        }
    }

    override fun searchBookByTag(TAG: String, isLoadMore: Boolean, refreshFromServer: Boolean?) {
        mRepository.searchBookByTag(TAG, refreshFromServer)
                .debounce(500, TimeUnit.SECONDS)
                .throttleFirst(1000, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun getBookDetailById(id: String, refreshFromServer: Boolean?) {
        mRepository.getBookDetail(id,refreshFromServer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun onServiceStart() {

    }

    override fun onServiceStop() {

    }
}