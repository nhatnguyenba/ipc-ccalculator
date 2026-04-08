package com.nhatnguyenba.server.domain

class SubtractUseCase(private val repo: CalculatorRepository) {
    operator fun invoke(a: Int, b: Int) = repo.subtract(a, b)
}