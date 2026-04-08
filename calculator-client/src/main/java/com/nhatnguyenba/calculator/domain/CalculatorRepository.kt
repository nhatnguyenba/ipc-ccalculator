package com.nhatnguyenba.calculator.domain

interface CalculatorRepository {
    suspend fun add(a: Int, b: Int): Int
    suspend fun subtract(a: Int, b: Int): Int
    suspend fun multiply(a: Int, b: Int): Int
    suspend fun divide(a: Int, b: Int): Double
}