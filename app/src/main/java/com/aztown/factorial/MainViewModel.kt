package com.aztown.factorial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {
    val coroutineScope = CoroutineScope(Dispatchers.Main + CoroutineName("My coroutine scope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        coroutineScope.launch {
            val result = factorial(value.toLong())
            _state.value = Factorial(result)
            Log.d("MainViewModel", coroutineContext.toString())
        }
    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number)
            result = result.multiply(BigInteger.valueOf(i))
        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number)
//                    result = result.multiply(BigInteger.valueOf(i))
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }
}