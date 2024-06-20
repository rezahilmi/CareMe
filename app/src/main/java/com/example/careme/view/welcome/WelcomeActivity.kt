package com.example.careme.view.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.careme.R
import com.example.careme.view.ViewModelFactory
import com.example.careme.view.authentication.AuthenticationActivity
import com.example.careme.view.main.MainActivity
import com.example.careme.view.main.MainViewModel


class WelcomeActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        viewModel.getSession().observe(this) { user ->
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = if (user.isLogin) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, AuthenticationActivity::class.java)
                }
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
}