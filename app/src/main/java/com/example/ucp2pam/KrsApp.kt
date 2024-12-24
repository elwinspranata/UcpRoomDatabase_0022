package com.example.ucp2pam

import com.example.ucp2pam.dependenciesinjection.ContainerApp

import android.app.Application

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}
