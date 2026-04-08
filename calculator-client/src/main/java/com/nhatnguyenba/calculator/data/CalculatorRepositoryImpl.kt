package com.nhatnguyenba.calculator.data

import com.nhatnguyenba.calculator.domain.CalculatorRepository
import com.nhatnguyenba.common.ICalculatorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculatorRepositoryImpl(
    private val service: ICalculatorService?
) : CalculatorRepository {

    override suspend fun add(a: Int, b: Int): Int {
        return withContext(Dispatchers.IO) {
            service?.add(a, b) ?: 0
        }
    }

    override suspend fun subtract(a: Int, b: Int): Int {
        return withContext(Dispatchers.IO) {
            service?.subtract(a, b) ?: 0
        }
    }

    override suspend fun multiply(a: Int, b: Int): Int {
        return withContext(Dispatchers.IO) {
            service?.multiply(a, b) ?: 0
        }
    }

    override suspend fun divide(a: Int, b: Int): Double {
        return withContext(Dispatchers.IO) {
            (service?.divide(a, b) ?: 0).toDouble()
        }
    }
}