package com.nhatnguyenba.server.domain

class AddUseCase(private val repo: CalculatorRepository) {
    operator fun invoke(a: Int, b: Int) = repo.add(a, b)
}