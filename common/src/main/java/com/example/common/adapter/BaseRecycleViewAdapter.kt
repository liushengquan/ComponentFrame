package com.example.common.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class BaseRecycleViewAdapter<T>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TYPE_HEAD = 0
    val TYPE_BODY = 1
    val TYPE_FOOT = 2

    var mDatas = mutableListOf<T>()
    lateinit var mContext: Context

    var mOnItemClickListener: OnItemClickListener<T>? = null
    var mOnItemLongClickListener: OnItemLongClickListener<T>? = null

    constructor(datas: MutableList<T>) : this() {
        mDatas = datas
    }

    fun setDatas(datas: MutableList<T>) {
        mDatas = datas
    }

    fun addDatas(datas: MutableList<T>) {
        mDatas?.run {
            clear()
            addAll(datas)
        }
    }

    fun getDatas(): MutableList<T> {
        return mDatas
    }

    fun remove(item: T) {
        mDatas?.run {
            remove(item)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>) {
        mOnItemLongClickListener = listener
    }

    abstract fun hasHeaderView(): Boolean
    abstract fun hasFooterView(): Boolean

    abstract fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder

    abstract fun onCreateFooterViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindItemViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int, data: T)

    abstract fun onBindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder?)

    abstract fun onBindFooterViewHolder(viewHolder: RecyclerView.ViewHolder?)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (p1) {
            TYPE_HEAD -> onCreateHeaderViewHolder(p0, p1)
            TYPE_FOOT -> onCreateFooterViewHolder(p0, p1)
            else ->
                onCreateItemViewHolder(p0, p1)
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (hasHeaderView() && p1 == 0) {
            onBindHeaderViewHolder(p0)
            return
        }
        if ((hasFooterView() && p1 == mDatas.size) || (hasHeaderView() && hasFooterView() && p1 == mDatas.size + 1)) {
            onBindFooterViewHolder(p0)
            return
        }

        val data = mDatas[p1]
        onBindItemViewHolder(p0, p1, data)

        p0?.run {
            itemView.setOnClickListener {
                mOnItemClickListener?.OnItemClick(it, p1, data)
            }
        }

        p0?.run {
            itemView.setOnLongClickListener { v ->
                mOnItemLongClickListener?.OnItemLongClick(v, p1, data)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (!hasHeaderView() && !hasFooterView()) {
            return TYPE_BODY
        }
        if (hasHeaderView() && position == 0) {
            return TYPE_HEAD
        }
        if (hasFooterView() && position == mDatas.size) {
            return TYPE_FOOT
        }
        return if (hasHeaderView() && hasFooterView() && position == mDatas.size + 1) {
            TYPE_FOOT
        } else TYPE_BODY
    }

    override fun getItemCount(): Int {
        if (hasHeaderView() && hasFooterView()) {
            return mDatas.size + 2
        } else if (hasHeaderView() || hasFooterView()) {
            return mDatas.size + 1
        }
        return mDatas.size
    }

    interface OnItemClickListener<T> {
        fun OnItemClick(view: View, position: Int, data: T)
    }

    interface OnItemLongClickListener<T> {
        fun OnItemLongClick(view: View, position: Int, data: T)
    }
}