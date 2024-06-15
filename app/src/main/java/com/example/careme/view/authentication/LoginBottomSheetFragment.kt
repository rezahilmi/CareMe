package com.example.careme.view.authentication

import android.content.Intent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.careme.databinding.BottomSheetLoginBinding
import com.example.careme.view.main.MainActivity

class LoginBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLoginBinding? = null
    private val binding get() = _binding!!
    private var isEmailValid = false
    private var isPasswordValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvRegister = binding.tvRegister
        val registerText = "Don't have an account? Register"
        val spannableString = SpannableString(registerText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Code to handle registration click
                dismiss()
                val registerFragment = RegisterBottomSheetFragment()
                registerFragment.show(parentFragmentManager, "registerFragment")
            }
        }

        spannableString.setSpan(clickableSpan, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvRegister.text = spannableString
        tvRegister.movementMethod = LinkMovementMethod.getInstance()

        binding.edLoginEmail.setValidityListener { isValid ->
            isEmailValid = isValid
            updateLoginButtonState()
        }

        binding.edLoginPassword.setValidityListener { isValid ->
            isPasswordValid = isValid
            updateLoginButtonState()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
//            Toast.makeText(activity, "Username: $email, Password: $password", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateLoginButtonState() {
        binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}