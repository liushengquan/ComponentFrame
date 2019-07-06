package com.example.api.report

import android.content.Context

interface IReportService : IBaseService {
    fun umengOnProfileSignIn(userId: String)
    fun umengOnProfileSignOff()
    fun umengOnPageStart(viewName: String)
    fun umengOnPageEnd(viewName: String)
    fun umengOnEvent(context: Context, eventID: String)
    fun umengOnEvent(context: Context, eventID: String, label: String)
    fun umengOnEvent(context: Context, eventID: String, map: Map<String, String>)
    fun umengOnEventValue(context: Context, eventID: String, map: Map<String, String>, du: Int)
    fun umengSetFirstLaunchEvent(context: Context, list: List<String>)
    fun umengReportError(context: Context, e: Throwable)
    fun umengReportError(context: Context, error: String)
}