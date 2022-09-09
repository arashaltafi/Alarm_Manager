package com.arash.altafi.alarmmanager.sample0.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.arash.altafi.alarmmanager.R
import com.google.android.material.textfield.TextInputLayout
import com.tapadoo.alerter.Alerter
import com.xdev.arch.persiancalendar.datepicker.CalendarConstraints
import com.xdev.arch.persiancalendar.datepicker.DateValidatorPointForward
import com.xdev.arch.persiancalendar.datepicker.MaterialDatePicker
import com.xdev.arch.persiancalendar.datepicker.Month
import com.xdev.arch.persiancalendar.datepicker.calendar.PersianCalendar
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import saman.zamani.persiandate.PersianDate
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.roundToInt

fun String.toIntHash(): Int {
    if (length == 0)
        return 0

    return abs(
        (hashCode() + reversed().hashCode())
                / (length * 2)
    ) % Int.MAX_VALUE
}

inline fun <reified NEW> Any.cast(): NEW? {
    return if (this.isCastable<NEW>())
        this as NEW
    else
        null
}

inline fun <reified NEW> Any.isCastable(): Boolean {
    return this is NEW
}

fun String.applyValue(vararg args: Any?): String {
    return String.format(Locale.US, this, *args)
}

@ColorInt
fun Context.getAttrColor(@AttrRes attrID: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attrID, typedValue, true)
    return typedValue.data
}

@ColorInt
fun Int.withAlpha(alpha: Float): Int {
    var alphaa: Int = Color.alpha(this)
    val red: Int = Color.red(this)
    val green: Int = Color.green(this)
    val blue: Int = Color.blue(this)

    alphaa = (alpha * alphaa).toInt()

    return Color.argb(alphaa, red, green, blue)
}

fun Int.toPx(): Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Int.toDp(): Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (this / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Float.toPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}

fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels
fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

fun TextView.setDrawableStart(res: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        res, 0,
        0, 0
    )
}

fun <F> runAfter(
    delay: Long, fx: () -> F, unit: TimeUnit = TimeUnit.MILLISECONDS
): Disposable {
    return Completable.timer(delay, unit)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete { fx() }
        .subscribe()
}

fun Fragment.popSuccess(
    @StringRes text: Int? = null,
    @StringRes title: Int? = null,
    duration: Long? = null, gravity: Int? = null
) = popSuccess(text?.let { getString(it) }, title?.let { getString(it) },
    duration, gravity
)

fun Fragment.popError(
    @StringRes text: Int? = null, @StringRes title: Int? = null,
    duration: Long? = null, gravity: Int? = null
) = popError(text?.let { getString(it) }, title?.let { getString(it) },
    duration, gravity
)

fun Fragment.popSuccess(
    text: String? = null,
    title: String? = null,
    duration: Long? = null, gravity: Int? = null
) = requireActivity().popSuccess(text, title, duration, gravity)

fun Fragment.popError(
    text: String? = null, title: String? = null,
    duration: Long? = null, gravity: Int? = null
) = requireActivity().popError(text, title, duration, gravity)

fun Activity.popSuccess(
    text: String? = null,
    title: String? = null,
    duration: Long? = null, gravity: Int? = null
) = popMessage(
    text, title, duration, gravity
).apply { success() }.show()

fun Activity.popError(
    text: String? = null,
    title: String? = null,
    duration: Long? = null, gravity: Int? = null
) = popMessage(
    text, title, duration, gravity
).apply { error() }.show()

private fun Activity.popMessage(
    text: String? = null, title: String? = null,
    duration: Long? = null, gravity: Int? = null
): Alerter {

    return Alerter.create(this).apply {
        default(duration ?: 3000, gravity ?: Gravity.BOTTOM)
        title?.let { setTitle(it) }
        text?.let { setText(it) }
    }
}

fun Alerter.default(
    duration: Long = 3000,
    gravity: Int
): Alerter {
    if (gravity == Gravity.BOTTOM)
        bottom() else top()

    enableIconPulse(false)
    enableClickAnimation(false)
    setDuration(duration)
    return this
}

fun Alerter.top(): Alerter {
    setLayoutGravity(Gravity.TOP)
//    setEnterAnimation(R.anim.slide_up)// FIXME: 6/22/2021 animation has issue
//    setExitAnimation(R.anim.slide_down)// FIXME: 6/22/2021 default animations is good :)
    return this
}

fun Alerter.bottom(): Alerter {
    setLayoutGravity(Gravity.BOTTOM)
    setEnterAnimation(R.anim.slide_fade_in_bottom)
    setExitAnimation(R.anim.slide_fade_out_bottom)
    return this
}

fun Alerter.error(): Alerter {
    setBackgroundResource(R.drawable.bg_alert_error)
    setIcon(R.drawable.ic_error)

    return this
}

fun Alerter.success(): Alerter {
    setBackgroundResource(R.drawable.bg_alert_success)
//    setBackgroundColorInt(Color.BLUE)
    setIcon(R.drawable.ic_success)
    return this
}

fun Alerter.info(): Alerter {
    setBackgroundResource(R.drawable.bg_alert_info)
    setIcon(R.drawable.ic_info)

    return this
}

fun Alerter.loading(): Alerter {
    showIcon(false)
    enableInfiniteDuration(true)
    enableProgress(true)
    return this
}

fun PersianDate.getDateString(): String {
    val year = shYear
    val month = if (shMonth < 10) "0${shMonth}" else shMonth
    val day = if (shDay < 10) "0${shDay}" else shDay

    return ("$year/$month/$day")
}

fun PersianDate.getTimeString(withSecond: Boolean = false): String {
    val secondString: String = if (withSecond) ":$second" else ""
    return ("$hour:$minute$secondString")
}

fun EditText.textString() =
    this.text.toString()

fun TextInputLayout.textString() =
    this.editText?.editableText.toString()


fun AppCompatActivity.showCalendarDialog(
    fromNow: Boolean = false,
    listener: (timestamp: Long?) -> Unit
) {
    val calendar = PersianCalendar()
    calendar.setPersian(1358, Month.FARVARDIN, 1)
    val start = calendar.timeInMillis
    calendar.setPersian(1500, Month.ESFAND, 29)
    val end = calendar.timeInMillis

    val openAt = PersianCalendar.getToday().timeInMillis
    val constraints = CalendarConstraints.Builder()
        .setStart(if (fromNow) openAt else start)
        .setEnd(end)
        .setOpenAt(openAt)

    if (fromNow)
        constraints.setValidator(DateValidatorPointForward.from(openAt))


    MaterialDatePicker.Builder
        .datePicker()
        .setTitleText(getString(R.string.select_date))
        .setCalendarConstraints(constraints.build()).build().apply {
            addOnPositiveButtonClickListener {
                listener(selection)
            }
        }
        .show(supportFragmentManager, "MaterialDatePicker")
}

fun PersianDate.getDateStringWithClock(withSecond: Boolean = false): String {
    val year = shYear
    val month = if (shMonth < 10) "0${shMonth}" else shMonth
    val day = if (shDay < 10) "0${shDay}" else shDay

    val secondString: String = if (withSecond) ":$second" else ""
    return ("$year/$month/$day $hour:$minute$secondString")
}

inline fun <reified T> NavController.getBackStackLiveData(key: String): MutableLiveData<T>? =
    this.currentBackStackEntry?.savedStateHandle?.getLiveData(key)

fun <T> NavController.setBackStackLiveData(
    key: String,
    value: T?,
    destinationId: Int? = null,
    doBack: Boolean = true,
    inclusive: Boolean = false
) {
    destinationId?.let {
        this.getBackStackEntry(it).savedStateHandle.set(key, value)
        if (doBack)
            popBackStack(it, inclusive)
    } ?: kotlin.run {
        this.previousBackStackEntry?.savedStateHandle?.set(key, value)
        if (doBack)
            popBackStack()
    }

}

fun NavController.setBackStackLiveData(
    key: String,
    value: Pair<Any, Any>?,
    destinationId: Int? = null,
    doBack: Boolean = true,
    inclusive: Boolean = false
) {
    destinationId?.let {
        this.getBackStackEntry(it).savedStateHandle.set(key, value)
        if (doBack)
            popBackStack(it, inclusive)
    } ?: kotlin.run {
        this.previousBackStackEntry?.savedStateHandle?.set(key, value)
        if (doBack)
            popBackStack()
    }

}