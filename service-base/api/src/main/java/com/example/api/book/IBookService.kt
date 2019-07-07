package com.example.api.book.bean

import com.example.api.report.IBaseService

interface IBookService : IBaseService {
    fun searchBookByTag(TAG: String, isLoadMore: Boolean, refreshFromServer: Boolean?)
    fun getBookDetailById(id: String, refreshFromServer: Boolean?)
}