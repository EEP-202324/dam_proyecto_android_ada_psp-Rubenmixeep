

package com.example.appempresa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : ComponentActivity() {

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
    fun CheckUser (context: Context, email:String, password:String){
        Text(text = )
        val url="http://127.0.0.1:9124/main/userlogin"
        val rqueue = Volley.newRequestQueue(context)
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)
        val request = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                if (response.has("id") && response.has("name")) {
                    val id = response.getString("id")
                    val nombre = response.getString("name")
                    val intent = Intent(context, TareaActivity::class.java)
                    // Puedes pasar datos adicionales al intent si es necesario
                    intent.putExtra("id", id)
                    intent.putExtra("nombre", nombre)
                    context.startActivity(intent)

                } else {
                    Toast.makeText(context, "No 'id' field found in JSON response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        rqueue.add(request)
    }