

package com.example.appempresa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appempresa.ui.theme.AppEmpresaTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val buttonRegister = findViewById<Button>(R.id.button3)
        buttonRegister.setOnClickListener {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivities(arrayOf(intent))

        }
    }
}