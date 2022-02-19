package com.siddydevelops.calculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

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

        val buttonNeg: Button = findViewById(R.id.buttonNeg)
        val buttonClear: Button = findViewById(R.id.buttonClear)

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

        buttonClear.setOnClickListener {
            newNumber.setText("")
            result.setText("")
            displayOperation.text = ""
        }

        buttonNeg.setOnClickListener {
            val value = newNumber.text.toString()
            if(value.isEmpty()) {
                newNumber.setText("-")
            } else {
                try {
                    var doublevalue: Double = value.toDouble()
                    doublevalue *= -1
                    newNumber.setText(doublevalue.toString())
                }catch (e: NumberFormatException) {
                    // newNumber was "-" or ".", so clear it
                    newNumber.setText("")
                }
            }
        }

        val opListner = View.OnClickListener { v->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value,op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
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

    private fun performOperation(value: Double, operation: String)
    {
        if(operand1 == null) {
            operand1 = value
        } else {
            operand2 = value
            if(pendingOperation == "=") {
                pendingOperation = operation
            }
            when(pendingOperation)
            {
                "=" -> operand1 = operand2
                "/" -> operand1 = if(operand2 == 0.0){
                                    Double.NaN                // Handle division exception
                                } else {
                                    operand1!! / operand2
                                }
                "*" -> operand1 = operand1!! * operand2
                "-" -> operand1 = operand1!! - operand2
                "+" -> operand1 = operand1!! + operand2
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1 != null) {
            outState.putDouble(STATE_OPERAND1,operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED,true)
        }
        outState.putString(STATE_PENDING_OPERATION,pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(STATE_OPERAND1_STORED,false)){
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
        displayOperation.text = pendingOperation
    }
}