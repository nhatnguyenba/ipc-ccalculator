package com.nhatnguyenba.calculator.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nhatnguyenba.calculator.data.CalculatorRepositoryImpl
import com.nhatnguyenba.calculator.domain.AddUseCase
import com.nhatnguyenba.calculator.domain.DivideUseCase
import com.nhatnguyenba.calculator.domain.MultiplyUseCase
import com.nhatnguyenba.calculator.domain.SubtractUseCase
import com.nhatnguyenba.calculator.presentation.viewmodel.CalculatorViewModel
import com.nhatnguyenba.common.ICalculatorService

class MainActivity : ComponentActivity() {

    private var calculatorService: ICalculatorService? = null

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            calculatorService = ICalculatorService.Stub.asInterface(service)
            Log.d("NHAT", "onServiceConnected, calculatorService="+calculatorService)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            calculatorService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindCalculatorService()

        setContent {
            AppContent()
        }
    }

    private fun bindCalculatorService() {
        val intent = Intent("com.nhatnguyenba.calculator.SERVICE").apply {
            setPackage("com.nhatnguyenba.server")
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    @Composable
    fun AppContent() {

        // observe service to recomposition
        val serviceState = remember { mutableStateOf(calculatorService) }

        // update when service connect
        LaunchedEffect(calculatorService) {
            serviceState.value = calculatorService
        }

        val viewModel = remember(serviceState.value) {

            val repo = CalculatorRepositoryImpl(serviceState.value)

            CalculatorViewModel(
                AddUseCase(repo),
                SubtractUseCase(repo),
                MultiplyUseCase(repo),
                DivideUseCase(repo)
            )
        }

        if (serviceState.value == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Connecting to calculator service...")
            }
        } else {
            CalculatorScreen(viewModel)
        }
    }
}