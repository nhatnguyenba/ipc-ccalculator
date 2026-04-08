package com.nhatnguyenba.calculator.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.calculator.domain.AddUseCase
import com.nhatnguyenba.calculator.domain.DivideUseCase
import com.nhatnguyenba.calculator.domain.MultiplyUseCase
import com.nhatnguyenba.calculator.domain.SubtractUseCase
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val addUseCase: AddUseCase,
    private val subUseCase: SubtractUseCase,
    private val mulUseCase: MultiplyUseCase,
    private val divUseCase: DivideUseCase
) : ViewModel() {

    var display by mutableStateOf("0")
    var expression by mutableStateOf("")

    private var firstNumber: Int? = null
    private var operation: Operation? = null

    fun onNumberClick(number: String) {
        display = if (display == "0") number else display + number
    }

    fun onOperationClick(op: Operation) {
        firstNumber = display.toIntOrNull()
        operation = op
        expression = "$display ${op.symbol()}"
        display = "0"
    }

    fun onEqualClick() {
        val secondNumber = display.toIntOrNull() ?: return
        val first = firstNumber ?: return

        viewModelScope.launch {
            try {
                val result = when (operation) {
                    Operation.ADD -> addUseCase(first, secondNumber)
                    Operation.SUBTRACT -> subUseCase(first, secondNumber)
                    Operation.MULTIPLY -> mulUseCase(first, secondNumber)
                    Operation.DIVIDE -> divUseCase(first, secondNumber)
                    else -> 0
                }

                Log.d("NHAT", "firstNumber="+firstNumber)
                Log.d("NHAT", "secondNumber="+secondNumber)
                Log.d("NHAT", "result="+result)

                display = result.toString()
                expression = ""
                firstNumber = null
                operation = null

            } catch (e: Exception) {
                display = "Error"
            }
        }
    }

    fun onClear() {
        display = "0"
        expression = ""
        firstNumber = null
        operation = null
    }

    fun onDelete() {
        display = display.dropLast(1).ifEmpty { "0" }
    }
}

fun Operation.symbol(): String = when (this) {
    Operation.ADD -> "+"
    Operation.SUBTRACT -> "-"
    Operation.MULTIPLY -> "×"
    Operation.DIVIDE -> "÷"
}

enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}