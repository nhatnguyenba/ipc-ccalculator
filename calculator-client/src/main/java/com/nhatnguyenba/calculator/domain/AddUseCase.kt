package com.nhatnguyenba.calculator.domain

class AddUseCase(private val repo: CalculatorRepository) {
    suspend operator fun invoke(a: Int, b: Int) = repo.add(a, b)
}