package com.kiryanov.gatestsolver.model

data class Question(
        val id: Int,
        val text: String,
        val answers: ArrayList<String>,
        val selected: Int = -1
)