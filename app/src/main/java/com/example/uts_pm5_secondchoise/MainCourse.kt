package com.example.uts_pm5_secondchoise

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_secondchoise.databinding.ActivityMainBinding
import com.example.uts_pm5_secondchoise.databinding.ActivityMainCourseBinding

class MainCourse : AppCompatActivity() {
    private lateinit var binding: ActivityMainCourseBinding

    // Pertanyaan
    val questions = listOf(
        "1. Apa yang paling ditakuti oleh seorang programmer Kotlin?",
        "2. Kalau aplikasi crash, siapa yang paling sedih?",
        "3. Kenapa Kotlin dan Android itu serasi banget?",
        "4. Apa yang harus dikatakan ke bug yang nggak mau pergi?",
        "5. Kalau kamu jadi variabel, kamu bakal jadi apa buat aku?"
    )

    // Opsi Jawaban
    val options = listOf(
        listOf(
            "A. NullPointerException 😱",
            "B. Aplikasi berjalan lancar tanpa bug 🤭",
            "C. Deadline mendadak maju 📅",
            "D. Pengumuman update Android terbaru 😅"
        ),
        listOf(
            "A. Pengguna, karena aplikasi nggak bisa dibuka 😭",
            "B. Programmer, karena harus cari bug 🕵️",
            "C. Android, karena dia ngambek 😔",
            "D. Laptop, karena sering dipukul-pukul 😆"
        ),
        listOf(
            "A. Karena mereka saling memahami 💕",
            "B. Karena Kotlin selalu siap mendukung Android 💪",
            "C. Karena Kotlin senang 'Null Safety' 🥰",
            "D. Karena Android sudah capek ditinggal Java 😅"
        ),
        listOf(
            "A. 'Pergilah jauh-jauh dari kodinganku!' 🚫",
            "B. 'Kapan kamu akan pergi selamanya?' 😩",
            "C. 'Please, fix yourself ya, biar kita happy ending.' 💔",
            "D. 'Kalau kamu pergi, aku janji nggak bakal nyari lagi.' 😆"
        ),
        listOf(
            "A. Nullable, biar kamu jadi null safety-ku. 🥰",
            "B. Boolean, biar aku bisa jadi true buat kamu ❤️",
            "C. Integer, biar aku bisa count on you selalu 🧮",
            "D. String, biar aku bisa concatenate sama kamu 😘"
        )
    )

    private var currentQuestionIndex = 0
    private val userAnswers = mutableListOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repeat(questions.size) {
            userAnswers.add(null)
        }

        displayQuestion()

        // Tombol Cancel
        binding.radioGrup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                userAnswers[currentQuestionIndex] = selectedRadioButton.text.toString()
                binding.btnCancel.visibility = View.VISIBLE
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.radioGrup.clearCheck()
            userAnswers[currentQuestionIndex] = null
            binding.btnCancel.visibility = View.GONE
        }

        // Tombol Next
        binding.btnNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            }
        }

        // Tombol Back
        binding.btnBack.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun displayQuestion(){
        try {
            // Set teks soal berdasarkan indeks soal saat ini
            binding.soal.text = questions[currentQuestionIndex]
            val optionsForCurrentQuestion = options[currentQuestionIndex]

            // Bersihkan semua pilihan sebelumnya
            binding.radioGrup.clearCheck()

            // Set teks untuk setiap RadioButton berdasarkan opsi yang tersedia
            binding.opsi1.text = optionsForCurrentQuestion[0]
            binding.opsi2.text = optionsForCurrentQuestion[1]
            binding.opsi3.text = optionsForCurrentQuestion[2]
            binding.opsi4.text = optionsForCurrentQuestion[3]

            // Menampilkan jawaban yang sudah dipilih sebelumnya (jika ada)
            val userAnswer = userAnswers[currentQuestionIndex]
            if (userAnswer != null) {
                when (userAnswer) {
                    optionsForCurrentQuestion[0] -> binding.opsi1.isChecked = true
                    optionsForCurrentQuestion[1] -> binding.opsi2.isChecked = true
                    optionsForCurrentQuestion[2] -> binding.opsi3.isChecked = true
                    optionsForCurrentQuestion[3] -> binding.opsi4.isChecked = true
                }
                binding.btnCancel.visibility = View.VISIBLE // Tampilkan tombol Cancel jika ada jawaban
            } else {
                binding.btnCancel.visibility = View.GONE // Sembunyikan tombol Cancel jika tidak ada jawaban
            }

            // Atur visibilitas tombol Back
            binding.btnBack.visibility = if (currentQuestionIndex == 0) View.GONE else View.VISIBLE

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: Indeks di luar batas.", Toast.LENGTH_SHORT).show()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: Tipe data tidak sesuai.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}