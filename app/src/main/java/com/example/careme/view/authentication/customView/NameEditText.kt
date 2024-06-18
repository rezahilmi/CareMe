package com.example.careme.view.authentication.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.careme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NameEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var listener: ((Boolean) -> Unit)? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nameLayout = parent.parent as TextInputLayout
                val isValid = isNameValid(s.toString())
                if (s.isNullOrEmpty()) {
                    nameLayout.error = context.getString(R.string.name_required)
                } else {
                    nameLayout.error = null
                }
                listener?.invoke(isValid)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isNameValid(password: String): Boolean {
        return password.isNotEmpty()
    }
    fun setValidityListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }
}