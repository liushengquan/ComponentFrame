package com.example.liushengquan.douban.api

import com.example.liushengquan.douban.bean.book.Book
import com.example.liushengquan.douban.bean.book.Books
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by liushengquan on 2017/12/25.
 */
interface BookApi {

    /**
     * 根据tag获取图书
     * @param tag
     * @return
     */
    @GET("v2/book/search")
    fun searchBookByTag(@Query("tag") tag: String): Observable<Books>

    @GET("v2/book/{id}")
    fun getBookDetail(@Path("id") id: String): Observable<Book>
}