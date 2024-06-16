package com.example.careme.view.authentication

import android.app.AlertDialog
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
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.careme.R
import com.example.careme.databinding.BottomSheetLoginBinding
import com.example.careme.view.ViewModelFactory
import com.example.careme.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class LoginBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLoginBinding? = null
    private val binding get() = _binding!!
    private var isEmailValid = false
    private var isPasswordValid = false
    private lateinit var authenticationViewModel: AuthenticationViewModel

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

        val factory = ViewModelFactory.getInstance(requireContext())
        authenticationViewModel = ViewModelProvider(this, factory)[AuthenticationViewModel::class.java]

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
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

        authenticationViewModel.loginResult.observe(viewLifecycleOwner) { response ->
            if (response.error == false) {
                AlertDialog.Builder(requireContext()).apply {
                    setMessage(response.message)
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    create()
                    show()
                }
            } else {
                (activity as AuthenticationActivity).showToast(response.message ?: "Gagal login")
            }
        }

        authenticationViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as AuthenticationActivity).showLoading(isLoading)
        }

        setupAction()

    }
    private fun updateLoginButtonState() {
        binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            dismiss()
            val inputMethodManager = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            login(email, password)

        }
    }

    private fun login(email: String, password: String) {
        authenticationViewModel.login(email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}