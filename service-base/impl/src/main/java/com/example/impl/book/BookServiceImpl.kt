package com.example.impl.book

import android.content.Context
import com.example.api.book.BookEvent
import com.example.api.book.bean.IBookService
import com.example.impl.book.data.BookRepository
import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
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
                .subscribe(object : Observer<Books> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Books) {
                        EventBus.getDefault().post(BookEvent.OnGetBooksEvent(t, null))
                    }

                    override fun onError(e: Throwable) {
                        EventBus.getDefault().post(BookEvent.OnGetBooksEvent(null, e.message))
                    }
                })
    }

    override fun getBookDetailById(id: String, refreshFromServer: Boolean?) {
        mRepository.getBookDetail(id, refreshFromServer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Book> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Book) {
                        EventBus.getDefault().post(BookEvent.OnGetBookDetailEvent(t, null))
                    }

                    override fun onError(e: Throwable) {
                        EventBus.getDefault().post(BookEvent.OnGetBookDetailEvent(null, e.message))
                    }
                })
    }

    override fun onServiceStart() {

    }

    override fun onServiceStop() {

    }
}