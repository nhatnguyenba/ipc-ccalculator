package com.nhatnguyenba.server.data

import com.nhatnguyenba.server.domain.CalculatorRepository

class CalculatorRepositoryImpl : CalculatorRepository {

    override fun add(a: Int, b: Int) = a + b

    override fun subtract(a: Int, b: Int) = a - b

    override fun multiply(a: Int, b: Int) = a * b

    override fun divide(a: Int, b: Int): Double {
        if (b == 0) throw ArithmeticException("Divide by zero")
        return a.toDouble() / b
    }
}