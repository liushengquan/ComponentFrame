package com.example.common.router

class Router private constructor() {

    private var mServiceMap: MutableMap<String, Any> = mutableMapOf()

    companion object {
        private lateinit var mRouter: Router

        fun getInstance() = if (mRouter != null) mRouter else Router()
    }

    fun registerService(serviceName: String, obj: Any) {
        mServiceMap?.put(serviceName, obj)
    }

    fun removeService(serviceName: String) {
        mServiceMap?.remove(serviceName)
    }

    fun getService(serviceName: String) = mServiceMap?.get(serviceName)
}