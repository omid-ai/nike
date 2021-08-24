package com.example.one.common.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.one.R
import com.example.one.common.exceptionHandling.NikeExceptionComponents
import com.example.one.ui.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

interface NikeView {
    val viewContext:Context?
    val rootView:CoordinatorLayout?

    fun showProgressBar(mustShow:Boolean){
        rootView?.let {
            viewContext?.let { context ->
                var progressBar=it.findViewById<View>(R.id.progressBarLayout)
                if (progressBar==null && mustShow){
                    progressBar=LayoutInflater.from(context).inflate(R.layout.layout_progressbar,it,false)
                    rootView?.addView(progressBar)
                }
                progressBar.visibility=if (mustShow)View.VISIBLE else View.GONE
            }
        }
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    fun showError(nikeExceptionComponents: NikeExceptionComponents){
        viewContext?.let {
            when(nikeExceptionComponents.type){
                NikeExceptionComponents.Type.SIMPLE-> showSnackBar(
                    nikeExceptionComponents.serverMessage?:it.getString(R.string.unknown_Error)
                )
                NikeExceptionComponents.Type.AUTH->{
                    it.startActivity(Intent(it,AuthActivity::class.java))
                    Toast.makeText(it,nikeExceptionComponents.serverMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

    fun showEmptyState(layoutRes:Int):View?{
        rootView?.let {
            viewContext?.let { context ->
                var emptyState=it.findViewById<View>(R.id.emptyStateRootView)
                if (emptyState==null){
                    emptyState=LayoutInflater.from(context).inflate(layoutRes,it,false)
                    rootView?.addView(emptyState)
                }
                emptyState.visibility=View.VISIBLE
                return emptyState
            }
        }
        return null
    }

}