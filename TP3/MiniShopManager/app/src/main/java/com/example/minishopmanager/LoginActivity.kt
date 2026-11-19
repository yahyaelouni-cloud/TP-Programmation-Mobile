package com.example.minishopmanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        prefs = getSharedPreferences("MiniShopPrefs", MODE_PRIVATE)

        // Reconnexion : le champ utilisateur est prérempli si "Se souvenir de moi" était coché
        etUsername.setText(prefs.getString(KEY_USER, ""))
        cbRemember.isChecked = prefs.getBoolean(KEY_REMEMBER, false)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            if (username.isEmpty() || etPassword.text.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val editor = prefs.edit()
            if (cbRemember.isChecked) {
                editor.putString(KEY_USER, username)
                editor.putBoolean(KEY_REMEMBER, true)
            } else {
                editor.clear()
            }
            editor.apply()

            val intent = Intent(this, CatalogueActivity::class.java)
            intent.putExtra(CatalogueActivity.EXTRA_USER, username)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val KEY_USER = "USERNAME"
        const val KEY_REMEMBER = "REMEMBER"
    }
}
