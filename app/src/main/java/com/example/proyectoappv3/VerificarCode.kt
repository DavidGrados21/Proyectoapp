package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.alumnop.RegistroE
import com.example.proyectoappv3.profesor.Registro

class VerificarCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificar_code)

        // Recupera los extras
        val email = intent.getStringExtra("email")
        val verificationCode = intent.getStringExtra("verification_code")
        val tipo = intent.getStringExtra("tipo")

        val code1 = findViewById<EditText>(R.id.code1)
        val code2 = findViewById<EditText>(R.id.code2)
        val code3 = findViewById<EditText>(R.id.code3)
        val code4 = findViewById<EditText>(R.id.code4)
        val code5 = findViewById<EditText>(R.id.code5)
        val code6 = findViewById<EditText>(R.id.code6)
        val btnverificar = findViewById<Button>(R.id.btnRegistrar)

        setupOtpInputs(code1, code2, code3, code4, code5, code6)

        btnverificar.setOnClickListener {
            val codigo = "${code1.text}${code2.text}${code3.text}${code4.text}${code5.text}${code6.text}"
            if (codigo.length == 6 && codigo == verificationCode) {
                Toast.makeText(this, "Código correcto", Toast.LENGTH_SHORT).show()
                if (tipo  == "E") {
                    val intent = Intent(this, RegistroE::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                else if (tipo == "P") {
                    val intent = Intent(this, Registro::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }


            } else {
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOtpInputs(code1: EditText, code2: EditText, code3: EditText, code4: EditText, code5: EditText, code6: EditText) {
        val editTexts = listOf(code1, code2, code3, code4, code5, code6)

        editTexts.forEachIndexed { index, editText ->
            editText.filters = arrayOf(InputFilter.LengthFilter(1)) // Limita a un solo carácter
            editText.inputType = InputType.TYPE_CLASS_NUMBER // Solo permite números

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        // Mueve el foco al siguiente campo si no es el último
                        if (index < editTexts.size - 1) {
                            editTexts[index + 1].requestFocus()
                        }
                    } else if (s?.isEmpty() == true) {
                        if (index > 0) {
                            editTexts[index - 1].requestFocus()
                        }
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}
