package com.example.careme.view.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.careme.databinding.BottomSheetRegisterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterBottomSheetFragment : BottomSheetDialogFragment()  {
    private var _binding: BottomSheetRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegister.setOnClickListener {
            val user = binding.edRegisterEmail.text.toString()
            val pass = binding.edRegisterPassword.text.toString()

            // Handle login logic here
            Toast.makeText(activity, "Username: $user, Password: $pass", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}