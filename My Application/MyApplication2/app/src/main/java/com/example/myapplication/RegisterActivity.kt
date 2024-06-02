package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.ui.theme.MyApplicationTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserRegisterScreen()
                }
            }
        }
    }
}

@Composable
fun UserRegisterScreen() {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = rol,
            onValueChange = { rol = it },
            label = { Text("Rol") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                UserRegister(
                    context = context,
                    name = name,
                    last_name = lastName,
                    email = email,
                    rol = rol,
                    password = password,
                    phone = phone
                )
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
        Text(text = "¿Ya tienes cuenta?",
            modifier = Modifier.padding(top = 30.dp))
        Button(
            onClick = {
                val intent = Intent(context,MainActivity::class.java)
                context.startActivity(intent)


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

    }
        }


fun UserRegister(context: android.content.Context, name: String, last_name: String, email: String, rol: String, password: String, phone: String) {
    val url = "http://192.168.1.142:9124/main/userregister"
    val Rqueue = Volley.newRequestQueue(context)
    val StringRequest = object : StringRequest(
        Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
        },
        Response.ErrorListener { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
        }
    ) {
        override fun getParams(): Map<String, String> {
            return hashMapOf(
                "name" to name,
                "last_name" to last_name,
                "email" to email,
                "rol" to rol,
                "password" to password,
                "phone" to phone
            )
        }
    }
    Rqueue.add(StringRequest)
}

@Preview(showBackground = true)
@Composable
fun UserRegisterScreenPreview() {
    MyApplicationTheme {
        UserRegisterScreen()
    }
}