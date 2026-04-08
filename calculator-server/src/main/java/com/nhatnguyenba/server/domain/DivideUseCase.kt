package com.nhatnguyenba.server.domain

class DivideUseCase(private val repo: CalculatorRepository) {
    operator fun invoke(a: Int, b: Int) = repo.divide(a, b)
}