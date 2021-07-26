package com.example.projeto_integrador.common.domain.model.user

data class User(
    val id: String,
    val contact: Contact,
    ) {

    data class Contact(
        val email: String,
        val phone: String,
        val address: Address
    )

    data class Address(
        val city: String,
        val state: String,
        val country: String
    )
}