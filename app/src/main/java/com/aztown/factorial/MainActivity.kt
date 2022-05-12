package com.aztown.factorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aztown.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.progressBarLoading.visibility = View.GONE
            binding.buttonCalculate.isEnabled = true
            when (it) {
                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }
                is Error -> {
                    Toast.makeText(
                        this, "You didn't enter the value", Toast.LENGTH_SHORT
                    ).show()
                }
                is Result -> {
                    binding.textViewFactorial.text = it.factorial
                }
            }
        }
    }
}