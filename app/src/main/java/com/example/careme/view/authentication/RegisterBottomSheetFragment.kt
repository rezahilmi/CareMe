package com.example.careme.view.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.careme.R
import com.example.careme.data.network.dataModel.RegisterRequest
import com.example.careme.databinding.BottomSheetRegisterBinding
import com.example.careme.view.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterBottomSheetFragment : BottomSheetDialogFragment()  {
    private var _binding: BottomSheetRegisterBinding? = null
    private val binding get() = _binding!!
    private var isNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        authenticationViewModel = ViewModelProvider(this, factory)[AuthenticationViewModel::class.java]

        binding.edRegisterName.setValidityListener { isValid ->
            isNameValid = isValid
            updateRegisterButtonState()
        }

        binding.edRegisterEmail.setValidityListener { isValid ->
            isEmailValid = isValid
            updateRegisterButtonState()
        }

        binding.edRegisterPassword.setValidityListener { isValid ->
            isPasswordValid = isValid
            updateRegisterButtonState()
        }

        authenticationViewModel.registerResult.observe(viewLifecycleOwner) { response ->
            val alertDialog = MaterialAlertDialogBuilder(requireContext())
                .setMessage(response.message)
                .setPositiveButton(getString(R.string.next)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            if (response.status == "success") {
                dismiss()
                alertDialog.show()
            } else {
                (activity as AuthenticationActivity).showToast(response.message ?: getString(R.string.failed_register))
            }
        }

        authenticationViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as AuthenticationActivity).showLoading(isLoading)
        }

        setupAction()
    }
        private fun updateRegisterButtonState() {
            binding.btnRegister.isEnabled = isNameValid && isEmailValid && isPasswordValid
        }

        private fun setupAction() {
            binding.btnRegister.setOnClickListener {
                val inputMethodManager = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                register(name, email, password)

            }
        }

        private fun register(name: String, email: String, password: String) {
            val registerRequest = RegisterRequest(name, email, password)
            authenticationViewModel.register(registerRequest)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}