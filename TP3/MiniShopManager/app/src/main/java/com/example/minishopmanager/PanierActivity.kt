package com.example.minishopmanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PanierActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panier)
        dbHelper = DBHelper(this)

        findViewById<Button>(R.id.btnVider).setOnClickListener {
            dbHelper.viderPanier()
            Toast.makeText(this, "Panier vidé", Toast.LENGTH_SHORT).show()
            afficherPanier()
        }
        afficherPanier()
    }

    // Lit les lignes de la table panier (SQLite) et les affiche
    private fun afficherPanier() {
        val lignes = dbHelper.lirePanier()
        val items = lignes.map { "#${it.id}  ${it.nom}  —  %.2f DT".format(it.prix) }
        findViewById<ListView>(R.id.listViewPanier).adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        findViewById<TextView>(R.id.tvTotal).text =
            "Articles : ${lignes.size}   |   Total : %.2f DT".format(lignes.sumOf { it.prix })
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
