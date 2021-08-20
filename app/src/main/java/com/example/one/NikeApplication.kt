package com.example.one

import android.app.Application
import com.example.one.model.dataSource.user.UserLocalDataSource
import com.example.one.model.repository.user.UserRepository
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NikeApplication:Application() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        userRepository.loadToken()
    }
}