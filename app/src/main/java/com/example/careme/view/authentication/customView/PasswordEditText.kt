package com.example.careme.view.authentication.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.careme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var listener: ((Boolean) -> Unit)? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordLayout = parent.parent as TextInputLayout
                val isValid = isPasswordValid(s.toString())
                if (s.isNullOrEmpty()) {
                    passwordLayout.error = context.getString(R.string.password_required)
                } else if (s.length < 8) {
                    passwordLayout.error = context.getString(R.string.password_8_character)
                } else {
                    passwordLayout.error = null
                }
                listener?.invoke(isValid)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }

    fun setValidityListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }
}