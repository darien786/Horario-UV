package com.example.horario_uv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.horario_uv.databinding.ActivityInicioSesionBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivityInicioSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = Firebase.database

        binding.btnIniciarSesion.setOnClickListener {
            if (validarCampos()){
                var correo = binding.etCorreo.text.trim().toString()
                var password = binding.etPassword.text.trim().toString()

                verificarSesion(correo, password)

            }
        }

    }

    fun validarCampos(): Boolean{
        var esValido = true

        if(binding.etCorreo.text.isEmpty()){
            binding.etCorreo.error = "Campo no válido"
            esValido = false
        }

        if (binding.etPassword.text.isEmpty()){
            binding.etPassword.error = "Campo no válido"
            esValido = false
        }

        return esValido
    }


    fun irPantallaRegistrar(view: View){
        val intent = Intent(this@InicioSesionActivity, FormularioRegistroActivity::class.java)
        startActivity(intent)
    }

    private fun irPantallaMenu(){
        val intent = Intent(this@InicioSesionActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun verificarSesion(correo:String, password:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    irPantallaMenu()
                    Toast.makeText(this@InicioSesionActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@InicioSesionActivity, "Correo y/o password incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.d("TAG", it.message.toString())
            }
    }
}