package com.nhatnguyenba.server.domain

class MultiplyUseCase(private val repo: CalculatorRepository) {
    operator fun invoke(a: Int, b: Int) = repo.multiply(a, b)
}