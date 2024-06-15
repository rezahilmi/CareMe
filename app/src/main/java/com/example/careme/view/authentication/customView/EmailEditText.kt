package com.example.careme.view.authentication.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.example.careme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var listener: ((Boolean) -> Unit)? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailLayout = parent.parent as TextInputLayout
                val isValid = isEmailValid(s.toString())
                if (!isValid) {
                    emailLayout.error = context.getString(R.string.invalid_email)
                } else {
                    emailLayout.error = null
                }
                listener?.invoke(isValid)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun setValidityListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }
}