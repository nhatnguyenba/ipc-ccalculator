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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.nhatnguyenba.calculator.data.CalculatorRepositoryImpl
import com.nhatnguyenba.calculator.domain.AddUseCase
import com.nhatnguyenba.calculator.domain.CalculatorRepository
import com.nhatnguyenba.calculator.domain.DivideUseCase
import com.nhatnguyenba.calculator.domain.MultiplyUseCase
import com.nhatnguyenba.calculator.domain.SubtractUseCase
import com.nhatnguyenba.calculator.presentation.viewmodel.CalculatorViewModel
import com.nhatnguyenba.calculator.presentation.viewmodel.CalculatorViewModelFactory
import com.nhatnguyenba.common.ICalculatorService

class MainActivity : ComponentActivity() {

    private var calculatorService: ICalculatorService? = null
    private val serviceState = mutableStateOf<ICalculatorService?>(null)

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            calculatorService = ICalculatorService.Stub.asInterface(service)
            serviceState.value = calculatorService
            Log.d("NHAT", "onServiceConnected, calculatorService=" + calculatorService)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            calculatorService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindCalculatorService()

        setContent {
            val service = serviceState.value

            if (service == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Connecting to service...")
                }
            } else {
                val viewModel = remember(service) {
                    val repo: CalculatorRepository = CalculatorRepositoryImpl(service)

                    val factory = CalculatorViewModelFactory(
                        AddUseCase(repo),
                        SubtractUseCase(repo),
                        MultiplyUseCase(repo),
                        DivideUseCase(repo)
                    )

                    ViewModelProvider(this, factory)
                        .get(CalculatorViewModel::class.java)
                }

                CalculatorScreen(viewModel)
            }
        }
    }

    private fun bindCalculatorService() {
        val intent = Intent()
        intent.setClassName(
            "com.nhatnguyenba.server",
            "com.nhatnguyenba.server.service.CalculatorService"
        )

        val success = bindService(intent, connection, Context.BIND_AUTO_CREATE)
        Log.d("NHAT", "bind result = $success")
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}