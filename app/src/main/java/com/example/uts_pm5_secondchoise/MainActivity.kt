package com.example.uts_pm5_secondchoise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_secondchoise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    Make Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.awalan1Btn.setOnClickListener{
            try {
                val intent = Intent(this, MainCourse::class.java)
                startActivity(intent)
                Toast.makeText(this, "Selamat Mengerjakan...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(this, "Gagal Membuka Ujian", Toast.LENGTH_SHORT).show()
            }
        }

        binding.awalan2Btn.setOnClickListener{
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUJcmljayByb2xs"))
                startActivity(intent)
                Toast.makeText(this, "Selamat Menonton...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(this, "Gagal Menonton Video", Toast.LENGTH_SHORT).show()
            }
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}