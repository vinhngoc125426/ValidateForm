package com.example.validateform

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.validateform.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val msv = intent.getStringExtra("msv")
        val lop = intent.getStringExtra("lop")

        val empty = "(chưa có)"
        binding.tvName.text = "Họ tên: ${name ?: empty}"
        binding.tvEmail.text = "Email: ${email ?: empty}"
        binding.tvPhone.text = "SĐT: ${phone ?: empty}"
        binding.tvMsv.text = "MSV: ${msv ?: empty}"
        binding.tvClass.text = "Lớp: ${lop ?: empty}"

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }
}