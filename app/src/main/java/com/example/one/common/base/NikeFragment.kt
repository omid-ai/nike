package com.example.one.common.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

abstract class NikeFragment:Fragment(),NikeView{
    override val viewContext: Context?
        get() = context
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout?

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}