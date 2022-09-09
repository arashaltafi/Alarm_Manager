package com.arash.altafi.alarmmanager.sample0.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    var binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    lateinit var activityContext: AppCompatActivity

    var isRestoredFromBackStack = false

    protected abstract fun viewHandler(view: View, savedInstanceState: Bundle?)
    protected open fun initObservers() {}
    protected open fun initBackStackObservers() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRestoredFromBackStack = false
        initBackStackObservers()
    }

    private var isAnimEnd = false
    open var doOnAnimEnd: (() -> Unit)? = null
        set(value) {
            if (isAnimEnd)
                value?.invoke()
            else
                field = value
        }

    override fun onCreateAnimation(
        transit: Int,
        enter: Boolean,
        nextAnim: Int
    ): Animation? {//from seyed
        if (!enter) {
            return null
        }
        if (0 != nextAnim) {
            val anim = AnimationUtils.loadAnimation(activity, nextAnim)
            anim?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    try {
                        isAnimEnd = true
                        doOnAnimEnd?.invoke()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onAnimationStart(p0: Animation?) {
                }

            })
            return anim
        } else {
            isAnimEnd = true
            doOnAnimEnd?.invoke()
            return null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityContext.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding = null
        isRestoredFromBackStack = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        ViewCompat.setLayoutDirection(requireNotNull(binding).root, ViewCompat.LAYOUT_DIRECTION_RTL)

        return requireNotNull(binding).root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewHandler(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as AppCompatActivity
    }
}