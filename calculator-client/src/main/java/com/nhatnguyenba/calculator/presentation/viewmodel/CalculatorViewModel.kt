package com.nhatnguyenba.calculator.presentation.viewmodel

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

    private var lastResult: Double? = null
    private var justCalculated = false

    private var operation: Operation? = null

    fun onNumberClick(number: String) {
        if (justCalculated) {
            expression = ""
            display = number
            justCalculated = false
        } else {
            display = if (display == "0") number else display + number
        }
    }

    fun onOperationClick(op: Operation) {
        if (justCalculated) {
            expression = display + " ${op.symbol} "
            justCalculated = false
            display = "0"
            return
        }

        expression += display + " ${op.symbol} "
        display = "0"
    }

    fun onEqualClick() {
        val fullExpression = expression + display

        viewModelScope.launch {
            try {
                val result = calculateExpression(fullExpression)
                display = result.toString()
                expression = ""
                lastResult = result
                justCalculated = true

            } catch (e: Exception) {
                display = "Error"
            }
        }
    }

    private suspend fun calculateExpression(expr: String): Double {
        val tokens = expr.split(" ").toMutableList()

        // PART 1: xử lý precedence cao (× ÷)
        var i = 0
        while (i < tokens.size) {
            val op = Operation.fromSymbol(tokens[i])

            if (op != null && op.precedence == 2) {
                val left = tokens[i - 1].toDouble()
                val right = tokens[i + 1].toDouble()

                val result = applyOperation(op, left, right)

                tokens[i - 1] = result.toString()
                tokens.removeAt(i)
                tokens.removeAt(i)

                i--
            } else {
                i++
            }
        }

        // PART 2: xử lý + -
        var result = tokens[0].toDouble()
        i = 1

        while (i < tokens.size) {
            val op = Operation.fromSymbol(tokens[i])
            val next = tokens[i + 1].toDouble()

            if (op != null) {
                result = applyOperation(op, result, next)
            }

            i += 2
        }

        return result
    }

    private suspend fun applyOperation(
        op: Operation,
        a: Double,
        b: Double
    ): Double {
        return when (op) {
            Operation.ADD -> addUseCase(a.toInt(), b.toInt()).toDouble()
            Operation.SUB -> subUseCase(a.toInt(), b.toInt()).toDouble()
            Operation.MUL -> mulUseCase(a.toInt(), b.toInt()).toDouble()
            Operation.DIV -> {
                if (b == 0.0) throw IllegalArgumentException("Divide by zero")
                divUseCase(a.toInt(), b.toInt())
            }
        }
    }

    fun onClear() {
        display = "0"
        expression = ""
        operation = null
    }

    fun onDelete() {
        display = display.dropLast(1).ifEmpty { "0" }
    }
}

enum class Operation(val symbol: String, val precedence: Int) {
    ADD("+", 1),
    SUB("-", 1),
    MUL("×", 2),
    DIV("÷", 2);

    companion object {
        fun fromSymbol(symbol: String): Operation? {
            return values().find { it.symbol == symbol }
        }
    }
}