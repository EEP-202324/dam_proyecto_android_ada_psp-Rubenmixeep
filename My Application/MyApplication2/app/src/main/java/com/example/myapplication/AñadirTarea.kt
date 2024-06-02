package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.RequestTask
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.json.JSONException

class AñadirTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id_usuario")
        val name = intent.getStringExtra("name")
        setContent {
            MaterialTheme {
                AñadirTareaScreen(id.orEmpty(), name.orEmpty(), this) {
                    // Esto se ejecutará cuando se haga clic en el botón de volver
                    onBackPressed()
                }
            }
        }
    }
}

@Composable
fun AñadirTareaScreen(id: String, name: String, context: Context, navigateBack: () -> Unit) {
    var description by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf<List<tasks>>(emptyList()) }

    LaunchedEffect(id) {
        ObtenerTareas(context, id) { loadedTasks ->
            tasks = loadedTasks
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido, $name")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción de la tarea") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                añadirTarea(context, id, description) {
                    ObtenerTareas(context, id) { loadedTasks ->
                        tasks = loadedTasks
                    }
                    Toast.makeText(context, "Tarea agregada", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Agregar tarea")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = navigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio de sesión")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de tareas:")
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEach { tarea -> //recorre las tareas y crea una interfaz grafica para cada tarea hasta que no encuentre mas tareas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Descripción: ${tarea.description}", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        EliminarTarea(context, tarea.id_tarea) {
                            ObtenerTareas(context, id) { loadedTasks ->
                                tasks = loadedTasks
                            }
                            Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Eliminar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun añadirTarea(context: Context, id_usuario: String, description: String, onTaskAdded: () -> Unit) {
    val url = "http://192.168.1.142:9124/tasks/NewTarea"

    val requestQueue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(
        Method.POST, url,
        Response.Listener<String> { response ->
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            onTaskAdded()
        },
        Response.ErrorListener { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    ) {
        override fun getParams(): Map<String, String> {
            val params = HashMap<String, String>()
            params["id_usuario"] = id_usuario
            params["description"] = description
            return params
        }
    }

    requestQueue.add(stringRequest)
}
data class tasks(val id_tarea: String, val id_usuario: String, val description: String)
fun ObtenerTareas(context: Context, id_usuario: String, onTaskLoaded: (List <tasks>) -> Unit){//he hecho un dataclass para almacenar la informacion de cada tarea y abajo con un for he ido rellenandolo y esta es el get porque tratara en la url la funcin del usuario
    val url = "http://192.168.1.142:9124/tasks/GetTarea/$id_usuario"

    val requestQueue = Volley.newRequestQueue(context)

     val jsonArrayRequest  = JsonArrayRequest(
         Request.Method.GET, url, null,
         { response ->
             val tasks = mutableListOf<tasks>()
          try {
              for (i in 0 until response.length()){
              val tareaJson = response.getJSONObject(i)
              val idTarea = tareaJson.optInt("id")
              val description = tareaJson.optString("description")
              val tarea = tasks(idTarea.toString(),id_usuario, description)
               tasks.add(tarea)
          }
    onTaskLoaded(tasks)

        }catch (e:JSONException){
              Toast.makeText(context,"error", Toast.LENGTH_LONG).show()
          }
    },
         {error ->

             Toast.makeText(context,error.toString(), Toast.LENGTH_LONG).show()
         })
    requestQueue.add(jsonArrayRequest)
}
fun EliminarTarea (context: Context, tarea_id:String?, onTaskDeleted:()-> Unit){
    tarea_id.let {
        taskId ->
        val url = "http://192.168.1.142:9124/tasks/BorrarTarea/$taskId"

    val requestQueue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.DELETE, url,
        { response ->
            // Manejar la respuesta del servidor
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            // Llama a la función proporcionada para indicar que se ha eliminado una tarea
            onTaskDeleted()
        },
        { error ->
            // Manejar errores
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    )

    requestQueue.add(stringRequest)
} ?: run {
    // Si el ID de la tarea es nulo o vacío, mostrar un mensaje de error o manejar la situación de otra manera
    Toast.makeText(context, "ID de tarea no válido", Toast.LENGTH_LONG).show()
}
}
