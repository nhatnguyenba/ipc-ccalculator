package com.nhatnguyenba.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhatnguyenba.calculator.domain.AddUseCase
import com.nhatnguyenba.calculator.domain.DivideUseCase
import com.nhatnguyenba.calculator.domain.MultiplyUseCase
import com.nhatnguyenba.calculator.domain.SubtractUseCase

class CalculatorViewModelFactory(
    private val addUseCase: AddUseCase,
    private val subUseCase: SubtractUseCase,
    private val mulUseCase: MultiplyUseCase,
    private val divUseCase: DivideUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalculatorViewModel(
            addUseCase,
            subUseCase,
            mulUseCase,
            divUseCase
        ) as T
    }
}