package com.karbyshev.friendsfinder.model

data class User (val name: String, val lat: String, val lng: String) {

    constructor() : this("", "", "")
}