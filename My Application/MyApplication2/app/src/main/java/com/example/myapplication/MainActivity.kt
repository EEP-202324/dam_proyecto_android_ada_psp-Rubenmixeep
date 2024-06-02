package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LoginScreen()
            }
        }
    }
}
fun login(context: Context, email: String, password: String) { //he pasado los parametros por post en forma de json
    //de ser correcto el usuario accedemos a la actividad añadirTarea y le pasamos el id y el nombre del usuario
    val url = "http://192.168.1.142:9124/main/userlogin"

    val jsonObject = JSONObject()
    jsonObject.put("email", email)
    jsonObject.put("password", password)

    val request = JsonObjectRequest(
        Request.Method.POST, url, jsonObject,
        { response ->
            if (response.has("id_user") && response.has("name")) {
                val id_user = response.getString("id_user")
                val name = response.getString("name")
                val intent = Intent(context, AñadirTarea::class.java)//esto lo he utilizado para pasar el id y el nombre a la otra actividad
                intent.putExtra("id_usuario", id_user)
                intent.putExtra("name", name)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No 'id' field found in JSON response", Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold( //esto es la parte de la interfaz grafica que lo he tratado en forma de una columna
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { login(context, email, password) }) {
                    Text("Login")
                }
                Text(
                    text = "¿No tienes cuenta?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 30.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val intent = Intent(context, RegisterActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Register")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        LoginScreen()
    }
}
