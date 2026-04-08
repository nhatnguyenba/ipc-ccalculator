package com.nhatnguyenba.server.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.nhatnguyenba.common.ICalculatorService
import com.nhatnguyenba.server.data.CalculatorRepositoryImpl
import com.nhatnguyenba.server.domain.CalculatorRepository

class CalculatorService : Service() {

    private val repo: CalculatorRepository = CalculatorRepositoryImpl()

    private val binder = object : ICalculatorService.Stub() {

        override fun add(a: Int, b: Int): Int {
            return repo.add(a, b)
        }

        override fun subtract(a: Int, b: Int): Int {
            return repo.subtract(a, b)
        }

        override fun multiply(a: Int, b: Int): Int {
            return repo.multiply(a, b)
        }

        override fun divide(a: Int, b: Int): Double {
            return repo.divide(a, b)
        }

        override fun asBinder(): IBinder {
            return this
        }
    }

    override fun onBind(intent: Intent): IBinder = binder
}