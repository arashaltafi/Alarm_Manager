package com.arash.altafi.alarmmanager.sample0.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, RESPONSE : BaseResponseData>(val binding: VB?) :
    RecyclerView.ViewHolder(requireNotNull(binding?.root)) {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>


    open fun bind(item: RESPONSE?) {
    }
}