package com.example.one.view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.one.R

class NikeToolBar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val backBtn=findViewById<ImageView>(R.id.backBtn)
    var onClickListen:OnClickListener?=null
    set(value) {
        field=value
        backBtn.setOnClickListener(onClickListen)
    }

    init {
        inflate(context, R.layout.view_toolbar,this)

        if (attrs!=null){
            val layout=context.obtainStyledAttributes(attrs,R.styleable.NikeToolBar)
            val title=layout.getString(R.styleable.NikeToolBar_nt_toolBarTitle)
            val toolBar=findViewById<TextView>(R.id.toolbarTitleTv)
            if (title!=null){
                toolBar.text=title
            }
            layout.recycle()
        }
    }
}