package com.example.minishopmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class LignePanier(val id: Long, val produitId: Int, val nom: String, val prix: Double)

class DBHelper(context: Context) : SQLiteOpenHelper(context, "minishop.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE panier(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                produit_id INTEGER,
                nom TEXT,
                prix REAL,
                date_ajout TEXT DEFAULT CURRENT_TIMESTAMP
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS panier")
        onCreate(db)
    }

    fun ajouterAuPanier(produit: Produit): Long {
        val values = ContentValues().apply {
            put("produit_id", produit.id)
            put("nom", produit.nom)
            put("prix", produit.prix)
        }
        return writableDatabase.insert("panier", null, values)
    }

    fun lirePanier(): List<LignePanier> {
        val lignes = mutableListOf<LignePanier>()
        readableDatabase.rawQuery(
            "SELECT id, produit_id, nom, prix FROM panier ORDER BY id", null
        ).use { c ->
            while (c.moveToNext()) {
                lignes.add(LignePanier(c.getLong(0), c.getInt(1), c.getString(2), c.getDouble(3)))
            }
        }
        return lignes
    }

    fun viderPanier() {
        writableDatabase.delete("panier", null, null)
    }
}
