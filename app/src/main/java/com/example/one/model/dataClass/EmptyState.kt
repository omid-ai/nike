package com.example.one.model.dataClass

import androidx.annotation.StringRes

data class EmptyState(
    var showEmptyStatePage: Boolean,
    @StringRes val emptyStateMessage: Int = 0,
    val showCtaBtn: Boolean = false
)
