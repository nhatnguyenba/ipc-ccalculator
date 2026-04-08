package com.nhatnguyenba.server.domain

interface CalculatorRepository {
    fun add(a: Int, b: Int): Int
    fun subtract(a: Int, b: Int): Int
    fun multiply(a: Int, b: Int): Int
    fun divide(a: Int, b: Int): Double
}