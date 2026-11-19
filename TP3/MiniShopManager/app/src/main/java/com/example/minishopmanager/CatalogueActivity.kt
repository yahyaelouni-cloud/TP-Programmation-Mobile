package com.example.minishopmanager

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class CatalogueActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        dbHelper = DBHelper(this)

        val user = intent.getStringExtra(EXTRA_USER) ?: ""
        findViewById<TextView>(R.id.tvBienvenue).text = "Bienvenue $user"

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val produits = lireProduitsDepuisJSON()
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, produits)

        // Clic sur un produit : Toast + insertion d'une ligne dans la table panier
        listView.setOnItemClickListener { _, _, position, _ ->
            val produit = produits[position]
            val id = dbHelper.ajouterAuPanier(produit)
            if (id != -1L) {
                Toast.makeText(this, "${produit.nom} ajouté au panier", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnPanier).setOnClickListener {
            startActivity(Intent(this, PanierActivity::class.java))
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun lireProduitsDepuisJSON(): ArrayList<Produit> {
        val produits = ArrayList<Produit>()
        val jsonString = assets.open("produits.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONObject(jsonString).getJSONArray("produits")
        for (i in 0 until jsonArray.length()) {
            val p = jsonArray.getJSONObject(i)
            produits.add(
                Produit(
                    id = p.getInt("id"),
                    nom = p.getString("nom"),
                    categorie = p.getString("categorie"),
                    prix = p.getDouble("prix")
                )
            )
        }
        return produits
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_USER = "USER"
    }
}
