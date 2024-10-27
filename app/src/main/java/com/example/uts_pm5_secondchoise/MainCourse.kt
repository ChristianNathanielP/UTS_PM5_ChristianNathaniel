package com.example.uts_pm5_secondchoise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            "A. Biar nggak ada kejutan NullPointerException 🥲",
            "B. Biar kode bisa dibaca sama semua orang",
            "C. Biar cepat compile-nya",
            "D. Biar nggak ada error sama sekali (ciee)"
        ),
        listOf(
            "A. onStart()",
            "B. onCreate()",
            "C. onResume()",
            "D. onPause()"
        ),
        listOf(
            "A. Karena Kotlin setia sama Android 💕",
            "B. Karena Kotlin anti-null",
            "C. Karena Kotlin lebih populer dari bahasa lain",
            "D. Karena Kotlin bikin Android ngga galau"
        ),
        listOf(
            "A. Bagian dari layar yang bisa disentuh atau dilihat",
            "B. Struktur data untuk simpan data pengguna",
            "C. Kumpulan library Android",
            "D. Sistem untuk mengelola aktivitas aplikasi"
        ),
        listOf(
            "A. val itu tetap, var bisa berubah",
            "B. val buat variabel wajib, var buat opsional",
            "C. val itu variabel sementara, var untuk selamanya",
            "D. val buat integer, var buat string"
        )
    )

    // Jawaban
    val correctAnswers = listOf(
        "A. Biar nggak ada kejutan NullPointerException 🥲",
        "B. onCreate()",
        "A. Karena Kotlin setia sama Android 💕",
        "A. Bagian dari layar yang bisa disentuh atau dilihat",
        "A. val itu tetap, var bisa berubah"
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

        displayQuestion() // Menjalankan Fungsi DisplayQuestion di bawah

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
            if (currentQuestionIndex == questions.size - 1) {
                showConfirmationDialog()
            } else {
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
            // Remove listener temporarily to avoid triggering clear when setting saved answers
            binding.radioGrup.setOnCheckedChangeListener(null)

            // Tampilkan teks pertanyaan sesuai dengan nomor pertanyaan saat ini
            binding.soal.text = questions[currentQuestionIndex]
            val optionsForCurrentQuestion = options[currentQuestionIndex]

            // Tampilkan pilihan jawaban untuk pertanyaan saat ini
            binding.opsi1.text = optionsForCurrentQuestion[0]
            binding.opsi2.text = optionsForCurrentQuestion[1]
            binding.opsi3.text = optionsForCurrentQuestion[2]
            binding.opsi4.text = optionsForCurrentQuestion[3]

            // Cek apakah sudah ada jawaban sebelumnya untuk pertanyaan ini, jika ada maka tampilkan jawaban tersebut
            val userAnswer = userAnswers[currentQuestionIndex]
            if (userAnswer != null) {
                // Tandai pilihan jawaban yang sudah dipilih sebelumnya
                when (userAnswer) {
                    optionsForCurrentQuestion[0] -> binding.opsi1.isChecked = true
                    optionsForCurrentQuestion[1] -> binding.opsi2.isChecked = true
                    optionsForCurrentQuestion[2] -> binding.opsi3.isChecked = true
                    optionsForCurrentQuestion[3] -> binding.opsi4.isChecked = true
                }
                binding.btnCancel.visibility = View.VISIBLE
            } else {
                binding.radioGrup.clearCheck()
                binding.btnCancel.visibility = View.GONE
            }

            // Re-enable listener for saving answers
            binding.radioGrup.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId != -1) {
                    // Simpan jawaban yang baru dipilih pengguna
                    val selectedRadioButton = findViewById<RadioButton>(checkedId)
                    userAnswers[currentQuestionIndex] = selectedRadioButton.text.toString()
                    binding.btnCancel.visibility = View.VISIBLE
                }
            }

            binding.btnBack.visibility = if (currentQuestionIndex == 0) View.GONE else View.VISIBLE

            if (currentQuestionIndex == questions.size - 1) {
                binding.btnNext.text = "Submit" // Soal Terakhir berubah menjadi Submit
            } else {
                binding.btnNext.text = "Next"
            }

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: Index out of bounds.", Toast.LENGTH_SHORT).show()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: Data type mismatch.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Kirim Jawaban?")
        builder.setMessage("Apakah Kamu yakin untuk mengirim jawaban?")

        builder.setPositiveButton("Yakin") { _, _ ->
            submitAnswers()
        }
        builder.setNegativeButton("Belum Yakin") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun submitAnswers() {
        var score = 0

        // Menghitung banyaknya jawaban benar
        for (i in userAnswers.indices) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++
            }
        }

        // Kirim Data score dan banyak question ke Answer Activity
        val intent = Intent(this, AnswerActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("totalQuestions", questions.size)
        startActivity(intent)
    }
}