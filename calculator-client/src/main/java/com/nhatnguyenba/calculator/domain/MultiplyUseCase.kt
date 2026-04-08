package com.nhatnguyenba.calculator.domain

class MultiplyUseCase(private val repo: CalculatorRepository) {
    suspend operator fun invoke(a: Int, b: Int) = repo.multiply(a, b)
}