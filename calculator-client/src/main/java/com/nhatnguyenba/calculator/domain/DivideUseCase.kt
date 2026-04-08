package com.nhatnguyenba.calculator.domain

class DivideUseCase(private val repo: CalculatorRepository) {
    suspend operator fun invoke(a: Int, b: Int) = repo.divide(a, b)
}