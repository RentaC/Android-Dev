package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currentNumber = StringBuilder()
    private var operand1: Double? = null
    private var operation: Char? = null
    private var decimalAdded = false
    private var isResultDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for number buttons
        with(binding) {
            btn0.setOnClickListener { appendNumber("0") }
            btn1.setOnClickListener { appendNumber("1") }
            btn2.setOnClickListener { appendNumber("2") }
            btn3.setOnClickListener { appendNumber("3") }
            btn4.setOnClickListener { appendNumber("4") }
            btn5.setOnClickListener { appendNumber("5") }
            btn6.setOnClickListener { appendNumber("6") }
            btn7.setOnClickListener { appendNumber("7") }
            btn8.setOnClickListener { appendNumber("8") }
            btn9.setOnClickListener { appendNumber("9") }
            btnPoint.setOnClickListener { appendDecimalPoint() }
        }

        // Set click listeners for operation buttons
        with(binding) {
            btnAdd.setOnClickListener { setOperation('+') }
            btnSubtract.setOnClickListener { setOperation('-') }
            btnMultiply.setOnClickListener { setOperation('*') }
            btnDivide.setOnClickListener { setOperation('/') }
        }

        // Set click listener for the equals button
        binding.btnEquals.setOnClickListener { calculateResult() }

        // Set click listener for the clear button
        binding.btnClear.setOnClickListener { clearAll() }

        // Set click listener for the delete button
        binding.btnDelete.setOnClickListener { deleteDigit() }
    }

    private fun appendNumber(number: String) {
        if (isResultDisplayed) {
            // Clear the result and start a new calculation
            clearAll()
            isResultDisplayed = false
        }

        if (number == "." && decimalAdded) {
            // Prevent adding multiple decimal points
            return
        }
        if (number == "." && currentNumber.isEmpty()) {
            // Automatically add a leading zero if decimal point is the first character
            currentNumber.append("0")
        }
        currentNumber.append(number)
        if (number == ".") {
            decimalAdded = true
        }
        updateDisplay()
    }

    private fun appendDecimalPoint() {
        appendNumber(".")
    }

    private fun setOperation(op: Char) {
        if (isResultDisplayed) {
            // Continue calculation using the previous result as operand1
            operand1 = currentNumber.toString().toDouble()
            operation = op
            currentNumber.clear()
            decimalAdded = false
            isResultDisplayed = false
        } else {
            if (operand1 == null) {
                if (currentNumber.isEmpty()) {
                    // Handle the case when the first thing pressed is an operation
                    return
                } else {
                    operand1 = currentNumber.toString().toDouble()
                    operation = op
                    currentNumber.clear()
                    decimalAdded = false
                }
            } else {
                // Handle the case when two operations are pressed back-to-back
                calculateResult()
                operation = op
            }
        }
    }

    private fun calculateResult() {
        if (operand1 == null || operation == null || currentNumber.isEmpty()) {
            // Handle the case when there is incomplete input
            return
        }
        val operand2 = currentNumber.toString().toDouble()
        when (operation) {
            '+' -> currentNumber.replace(0, currentNumber.length, (operand1!! + operand2).toString())
            '-' -> currentNumber.replace(0, currentNumber.length, (operand1!! - operand2).toString())
            '*' -> currentNumber.replace(0, currentNumber.length, (operand1!! * operand2).toString())
            '/' ->  if (operand2 == 0.0) {
                // Handle division by zero
                currentNumber.clear()
                currentNumber.append("Error")
            } else {
                currentNumber.replace(0, currentNumber.length, (operand1!! / operand2).toString())
            }
        }
        operand1 = null
        operation = null
        decimalAdded = currentNumber.contains(".")                 //false
        updateDisplay()
        isResultDisplayed = true
    }

    private fun clearAll() {
        currentNumber.clear()
        operand1 = null
        operation = null
        decimalAdded = false
        updateDisplay()
        isResultDisplayed = false
    }

    private fun deleteDigit() {
        if (currentNumber.isNotEmpty()) {
            currentNumber.deleteCharAt(currentNumber.length - 1)
            decimalAdded = currentNumber.contains(".")
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        binding.textViewResult.text = currentNumber.toString()
    }
}