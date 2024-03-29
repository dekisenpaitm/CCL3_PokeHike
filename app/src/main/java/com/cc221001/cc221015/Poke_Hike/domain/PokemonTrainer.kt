package com.cc221001.cc221015.Poke_Hike.domain

// Represents a Pokemon trainer with optional ID, name, gender, and sprite.
data class PokemonTrainer(
    val id: Int? = 0,       // Optional ID for identifying the trainer.
    val name: String,       // The name of the trainer.
    val hometown: String,     // The gender of the trainer.
    val sprite: String?)   // Optional URL or reference to the trainer's sprite or image.
