package com.example.validateform

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.validateform.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var name: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var msv: String? = null
    private var lop: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clearErrorWhenTyping()

        // Nút Đăng ký
        binding.btnRegister.setOnClickListener {
            if (validateAll()) {
                name = binding.edtName.text.toString().trim()
                email = binding.edtEmail.text.toString().trim()
                phone = binding.edtPhone.text.toString().trim()
                msv = binding.edtMsv.text.toString().trim()
                lop = binding.edtClass.text.toString().trim()

                Snackbar.make(binding.root, "Đăng ký thành công!", Snackbar.LENGTH_LONG)
                    .setDuration(7000)
                    .setAction("Xem chi tiết") { goToDetail() }
                    .show()
            }
        }

        binding.btnGoDetail.setOnClickListener { goToDetail() }
    }

    private fun goToDetail() {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("name", name)
            putExtra("email", email)
            putExtra("phone", phone)
            putExtra("msv", msv)
            putExtra("lop", lop)
        }
        startActivity(intent)
    }

    private fun validateAll(): Boolean {
        val results = listOf(
            validateName(),
            validateEmail(),
            validatePhone(),
            validateMsv(),
            validateClass()
        )
        return results.all { it }
    }

    private fun validateName(): Boolean {
        val value = binding.edtName.text.toString().trim()
        return when {
            value.isEmpty() -> setError(binding.tilName, "Vui lòng nhập họ tên")
            value.length < 2 -> setError(binding.tilName, "Họ tên phải có ít nhất 2 ký tự")
            !value.all { it.isLetter() || it.isWhitespace() } ->
                setError(binding.tilName, "Họ tên chỉ được chứa chữ cái")
            else -> true
        }
    }

    private fun validateEmail(): Boolean {
        val value = binding.edtEmail.text.toString().trim()
        return when {
            value.isEmpty() -> setError(binding.tilEmail, "Vui lòng nhập email")
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() ->
                setError(binding.tilEmail, "Email không đúng định dạng (vd: abc@gmail.com)")
            else -> true
        }
    }

    private fun validatePhone(): Boolean {
        val value = binding.edtPhone.text.toString().trim()
        val phoneRegex = Regex("^(0|\\+84)(3|5|7|8|9)\\d{8}$")
        return when {
            value.isEmpty() -> setError(binding.tilPhone, "Vui lòng nhập số điện thoại")
            !phoneRegex.matches(value) ->
                setError(binding.tilPhone, "SĐT không hợp lệ (vd: 0912345678)")
            else -> true
        }
    }

    private fun validateMsv(): Boolean {
        val value = binding.edtMsv.text.toString().trim()
        return when {
            value.isEmpty() -> setError(binding.tilMsv, "Vui lòng nhập mã sinh viên")
            !value.all { it.isDigit() } ->
                setError(binding.tilMsv, "Mã sinh viên chỉ gồm chữ số")
            value.length != 13 ->
                setError(binding.tilMsv, "Mã sinh viên phải đủ 13 chữ số (hiện có ${value.length})")
            else -> true
        }
    }

    private fun validateClass(): Boolean {
        val value = binding.edtClass.text.toString().trim()
        return when {
            value.isEmpty() -> setError(binding.tilClass, "Vui lòng nhập lớp")
            !Regex("^[\\p{L}0-9 ._-]+$").matches(value) ->
                setError(binding.tilClass, "Lớp chỉ gồm chữ, số và các ký tự . _ -")
            else -> true
        }
    }

    private fun setError(layout: com.google.android.material.textfield.TextInputLayout, msg: String): Boolean {
        layout.error = msg
        return false
    }

    private fun clearErrorWhenTyping() {
        binding.edtName.doAfterTextChanged { binding.tilName.error = null }
        binding.edtEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.edtPhone.doAfterTextChanged { binding.tilPhone.error = null }
        binding.edtMsv.doAfterTextChanged { binding.tilMsv.error = null }
        binding.edtClass.doAfterTextChanged { binding.tilClass.error = null }
    }
}