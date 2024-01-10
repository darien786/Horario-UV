package com.example.horario_uv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.horario_uv.databinding.ActivityInicioSesionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var fireBase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var binding: ActivityInicioSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fireBase = FirebaseDatabase.getInstance()


        binding.btnIniciarSesion.setOnClickListener {
            if (validarCampos()){
                var correo = binding.etCorreo
                irPantallaMenu()
            }
        }

        binding.tvRegistrar.setOnClickListener {
            irPantallaRegistrar()
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


    private fun irPantallaRegistrar(){
        val intent = Intent(this@InicioSesionActivity, FormularioRegistroActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irPantallaMenu(){
        val intent = Intent(this@InicioSesionActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun verificarSesion(){
        reference = fireBase.getReference("usuarios")
    }
}