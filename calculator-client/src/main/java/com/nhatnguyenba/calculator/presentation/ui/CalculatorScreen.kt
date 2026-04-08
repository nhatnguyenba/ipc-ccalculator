package com.nhatnguyenba.calculator.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nhatnguyenba.calculator.presentation.viewmodel.CalculatorViewModel
import com.nhatnguyenba.calculator.presentation.viewmodel.Operation

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Display
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = viewModel.expression,
                fontSize = 24.sp,
                color = Color.Gray
            )

            Text(
                text = viewModel.display,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Buttons
        CalculatorButtons(viewModel)
    }
}

@Composable
fun CalculatorButtons(viewModel: CalculatorViewModel) {

    val buttons = listOf(
        listOf("C", "⌫", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", "=")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        modifier = Modifier.weight(1f),
                        onClick = { handleClick(label, viewModel) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = label, fontSize = 20.sp)
    }
}

fun handleClick(label: String, vm: CalculatorViewModel) {
    when (label) {
        "C" -> vm.onClear()
        "⌫" -> vm.onDelete()
        "+" -> vm.onOperationClick(Operation.ADD)
        "-" -> vm.onOperationClick(Operation.SUBTRACT)
        "×" -> vm.onOperationClick(Operation.MULTIPLY)
        "÷" -> vm.onOperationClick(Operation.DIVIDE)
        "=" -> vm.onEqualClick()
        else -> vm.onNumberClick(label)
    }
}