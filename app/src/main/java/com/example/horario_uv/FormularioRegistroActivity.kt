package com.example.horario_uv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.horario_uv.databinding.ActivityFormularioRegistroBinding
import com.example.horario_uv.databinding.ActivityInicioSesionBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class FormularioRegistroActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var  binding: ActivityFormularioRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioRegistroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = Firebase.database

        binding.btnRegistrar.setOnClickListener {
            if(verificarCampos()){
                var nombre = binding.etNombre.text.trim().toString()
                var apellidoPaterno = binding.etApellidoPaterno.text.trim().toString()
                var apellidoMaterno = binding.etApellidoMaterno.text.trim().toString()
                var correo = binding.etCorreo.text.trim().toString()
                var password = binding.etPassword.text.trim().toString()

                registrarUsuario(nombre, apellidoPaterno, apellidoMaterno, correo, password)
            }
        }

    }

    private fun verificarCampos(): Boolean{
        var esValido = true

        if (binding.etNombre.text.trim().toString().isEmpty()){
            binding.etNombre.error = "Campo obligatorio"
            esValido = false
        }
        if (binding.etApellidoPaterno.text.trim().toString().isEmpty()){
            binding.etApellidoPaterno.error = "Campo obligatorio"
            esValido = false
        }
        if (binding.etApellidoMaterno.text.trim().toString().isEmpty()){
            binding.etApellidoMaterno.error = "Campo obligatorio"
            esValido = false
        }
        if (binding.etCorreo.text.trim().toString().isEmpty()){
            binding.etCorreo.error = "Campo obligatorio"
            esValido = false
        }
        if (binding.etPassword.text.trim().toString().isEmpty()){
            binding.etPassword.error = "Campo obligatorio"
            esValido = false
        }

        return esValido
    }

    private fun registrarUsuario(nombre:String, apellidoPaterno:String, apellidoMaterno: String, correo: String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result?.user

                    guardarInformacion(user!!.uid, nombre, apellidoPaterno, apellidoMaterno)

                }else{
                    Toast.makeText(this@FormularioRegistroActivity, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this@FormularioRegistroActivity, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarInformacion(uid: String, nombre: String, apellidoPaterno: String, apellidoMaterno: String) {
        if(uid!= null){
            val usuarioRFC = database.reference.child("usuarios")

            val informacion = hashMapOf(
                "nombre" to nombre,
                "apellidoPaterno" to apellidoPaterno,
                "apellidoMaterno" to apellidoMaterno
            )

            usuarioRFC.child(uid)
                .setValue(informacion)
                .addOnSuccessListener {
                    Toast.makeText(this@FormularioRegistroActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@FormularioRegistroActivity, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}