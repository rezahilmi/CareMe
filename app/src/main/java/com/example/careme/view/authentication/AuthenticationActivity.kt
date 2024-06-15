package com.example.careme.view.authentication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.careme.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.btnShowLogin.setOnClickListener {
            val loginBottomSheet = LoginBottomSheetFragment()
            loginBottomSheet.show(supportFragmentManager, "LoginBottomSheet")
        }
        binding.btnShowRegister.setOnClickListener {
            val registerFragment = RegisterBottomSheetFragment()
            registerFragment.show(supportFragmentManager, "registerFragment")
        }
    }
}