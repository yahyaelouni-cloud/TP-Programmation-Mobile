package com.example.minishopmanager

data class Produit(
    val id: Int,
    val nom: String,
    val categorie: String,
    val prix: Double
) {
    // Texte affiché dans la ListView (ArrayAdapter utilise toString())
    override fun toString(): String = "$nom\n$categorie — %.2f DT".format(prix)
}
