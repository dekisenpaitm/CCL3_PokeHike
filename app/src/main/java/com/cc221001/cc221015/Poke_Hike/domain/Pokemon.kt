package com.cc221001.cc221015.Poke_Hike.domain

// Represents a Pokemon entity with various attributes.
data class Pokemon (
	val number: Int,  // The unique identification number of the Pokemon.
	val name: String,  // The name of the Pokemon.
	val type0: String,  // The primary type of the Pokemon (e.g., Water, Fire).
	val type1: String,  // The secondary type of the Pokemon (e.g., Flying, Psychic).
	var imageUrl: String = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/001.png",
	// The URL of the Pokemon's image. Defaults to a standard Pokemon sprite URL.
	val liked: String = "false",  // Indicates whether the Pokemon is liked by the user (defaults to "false").
	val owned: String = "false"  // Indicates whether the Pokemon is liked by the user (defaults to "false").
){
	init{
		println(number)
		if(number<10) {
			this.imageUrl = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00$number.png"
		} else if(number >= 10 && number < 100) {
			this.imageUrl = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0$number.png"
		} else if(number >= 100){
			this.imageUrl = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$number.png"
		}
		println(imageUrl)
	}

}
