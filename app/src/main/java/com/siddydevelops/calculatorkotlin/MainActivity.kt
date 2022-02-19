package com.siddydevelops.calculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var result : EditText
    private lateinit var newNumber : EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    // Variables to hold the operands and type calculations
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        val buttonEqual: Button = findViewById(R.id.buttonEqual)
        val buttonPlus: Button = findViewById(R.id.buttonPlus)
        val buttonDivide: Button = findViewById(R.id.buttonDivide)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val buttonMinus: Button = findViewById(R.id.buttonMinus)

        val listner = View.OnClickListener { v->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listner)
        button1.setOnClickListener(listner)
        button2.setOnClickListener(listner)
        button3.setOnClickListener(listner)
        button4.setOnClickListener(listner)
        button5.setOnClickListener(listner)
        button6.setOnClickListener(listner)
        button7.setOnClickListener(listner)
        button8.setOnClickListener(listner)
        button9.setOnClickListener(listner)
        buttonDot.setOnClickListener(listner)

        val opListner = View.OnClickListener { v->
            val op = (v as Button).text.toString()
            val value = newNumber.text.toString()
            if(value.isNotEmpty()) {
                performOperation(value,op)
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        buttonEqual.setOnClickListener(opListner)
        buttonDivide.setOnClickListener(opListner)
        buttonPlus.setOnClickListener(opListner)
        buttonMinus.setOnClickListener(opListner)
        buttonMultiply.setOnClickListener(opListner)

    }

    private fun performOperation(value: String, operation: String)
    {
        if(operand1 == null) {
            operand1 = value.toDouble()
        } else {
            operand2 = value.toDouble()
            if(pendingOperation == "=") {
                pendingOperation = operation
            }
            when(pendingOperation)
            {
                "=" -> operand1 = operand2
                "/" -> if(operand2 == 0.0){
                        operand1 = Double.NaN                // Handle division exception
                    } else {
                        operand1 = operand1!! / operand2
                    }
                "*" -> operand1 = operand1!! * operand2
                "-" -> operand1 = operand1!! - operand2
                "+" -> operand1 = operand1!! + operand2
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

}