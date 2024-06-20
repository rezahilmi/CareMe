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
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.example.careme.R
import com.example.careme.data.network.dataModel.LoginRequest
import com.example.careme.databinding.BottomSheetLoginBinding
import com.example.careme.view.ViewModelFactory
import com.example.careme.view.main.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
            val alertDialog = MaterialAlertDialogBuilder(requireContext())
                .setMessage("login success")
                .setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .create()

            if (response.status == "success") {
                alertDialog.setOnDismissListener {
                    dismiss()
                }
                alertDialog.show()
            } else {
                (activity as AuthenticationActivity).showToast(response.message ?: getString(R.string.failed_login))
            }
        }

        authenticationViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as AuthenticationActivity).showLoading(isLoading)
            showLoading(isLoading)
        }

        setupAction()

    }
    private fun updateLoginButtonState() {
        binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val inputMethodManager = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            login(email, password)

        }
    }

    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        authenticationViewModel.login(loginRequest)
    }

    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.btnLogin.isEnabled = false
            binding.emailEditTextLayout.isEnabled = false
            binding.passwordEditTextLayout.isEnabled = false
            binding.progressBarLogin.visibility = View.VISIBLE
            binding.dimmingViewLogin.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
            binding.emailEditTextLayout.isEnabled = true
            binding.passwordEditTextLayout.isEnabled = true
            binding.progressBarLogin.visibility = View.GONE
            binding.dimmingViewLogin.visibility = View.GONE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}