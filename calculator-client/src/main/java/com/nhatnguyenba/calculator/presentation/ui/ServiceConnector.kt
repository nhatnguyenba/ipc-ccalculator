package com.nhatnguyenba.calculator.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.nhatnguyenba.common.ICalculatorService

class ServiceConnector(private val context: Context) {

    var service: ICalculatorService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = ICalculatorService.Stub.asInterface(binder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }
    }

    fun bind() {
        val intent = Intent("com.nhatnguyenba.calculator.SERVICE")
        intent.setPackage("com.nhatnguyenba.server")
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbind() {
        context.unbindService(connection)
    }
}