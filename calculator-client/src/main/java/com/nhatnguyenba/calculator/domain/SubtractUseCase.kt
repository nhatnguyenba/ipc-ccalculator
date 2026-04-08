package com.nhatnguyenba.calculator.domain

class SubtractUseCase(private val repo: CalculatorRepository) {
    suspend operator fun invoke(a: Int, b: Int) = repo.subtract(a, b)
}