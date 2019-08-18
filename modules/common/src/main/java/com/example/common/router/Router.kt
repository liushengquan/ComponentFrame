package com.example.common.router

class Router private constructor() {

    private var mServiceMap: MutableMap<String, Any> = mutableMapOf()

    companion object {
        private var instance: Router? = null
            get() {
                if (field == null) {
                    field = Router()
                }
                return field
            }

        @Synchronized
        fun get(): Router{
            return instance!!
        }
    }

    fun registerService(serviceName: String, obj: Any) {
        mServiceMap?.put(serviceName, obj)
    }

    fun removeService(serviceName: String) {
        mServiceMap?.remove(serviceName)
    }

    fun getService(serviceName: String) = mServiceMap?.get(serviceName)
}