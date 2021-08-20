package com.example.one.common.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import org.greenrobot.eventbus.EventBus

abstract class NikeActivity:AppCompatActivity(),NikeView{
    override val viewContext: Context?
        get() = this
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup=window.decorView.findViewById<View>(android.R.id.content)as ViewGroup
            if (viewGroup !is CoordinatorLayout){
                viewGroup.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("view must be CoordinatorLayout")
            }else
                return viewGroup
        }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}