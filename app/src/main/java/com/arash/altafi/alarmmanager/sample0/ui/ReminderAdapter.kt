package com.arash.altafi.alarmmanager.sample0.ui

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arash.altafi.alarmmanager.R
import com.arash.altafi.alarmmanager.databinding.ItemReminderBinding
import com.arash.altafi.alarmmanager.sample0.utils.applyValue
import com.arash.altafi.alarmmanager.sample0.base.BaseAdapter
import com.arash.altafi.alarmmanager.sample0.utils.getAttrColor
import com.arash.altafi.alarmmanager.sample0.repository.ReminderEntity
import com.arash.altafi.alarmmanager.sample0.utils.withAlpha
import saman.zamani.persiandate.PersianDate

class ReminderAdapter : BaseAdapter<
        ItemReminderBinding,
        BaseAdapter.VHolder<ItemReminderBinding, ReminderEntity>, ReminderEntity>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemReminderBinding
        get() = ItemReminderBinding::inflate

    var onClickListenerView: ((View) -> Unit)? = null

    override fun onBindViewHolder(
        holder: VHolder<ItemReminderBinding, ReminderEntity>, position: Int
    ) {
        holder.binding?.apply {
            getItem(position)?.let { item ->
                val context = holder.itemView.context

//                if (position == 0) view.toGone()
//                else view.toShow()

                tvTitle.text = item.text

                ivMore.setOnClickListener {
                    onClickListener?.let { it(item) }
                    onClickListenerView?.invoke(it)
                }

                val persianDate = PersianDate(item.unixTime)

                tvTime.text = holder.itemView.context.getString(
                    R.string.str_reminder_time_format
                ).applyValue(
                    persianDate.shDay, persianDate.monthName(), persianDate.shYear,
                    "${persianDate.hour}:${persianDate.minute}"
                )

                ivMore.setOnClickListener {
                    onClickListener?.let { it(item) }
                    onClickListenerView?.invoke(it)
                }


                if (item.unixTime > System.currentTimeMillis()) {
                    val color = context.getAttrColor(R.attr.colorPrimary).withAlpha(1f)
                    val colorPrimary = context.getAttrColor(R.attr.colorPrimary)

                    tvTitle.setTextColor(color)
                    tvTime.setTextColor(color)
                    tvTime.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                    ivClock.imageTintList = ColorStateList.valueOf(colorPrimary)
                } else {
                    val color = context.getAttrColor(R.attr.colorPrimary).withAlpha(0.5f)

                    tvTitle.setTextColor(color)
                    tvTime.setTextColor(color)
                    tvTime.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_tick_double,
                        0,
                        0,
                        0
                    )
                    ivClock.imageTintList = ColorStateList.valueOf(color)
                }

            }
        }
    }
}