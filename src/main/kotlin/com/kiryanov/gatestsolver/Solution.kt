package com.kiryanov.gatestsolver

import com.kiryanov.gatestsolver.rating.RatingImpl
import com.kiryanov.gatestsolver.test_module.QuestionFileManager

const val QUESTION_FILE = "data/questions.txt"
const val ANSWER_FILE = "data/answers.txt"
const val QUESTION_BASE = "data/questions_base.txt"

fun main(args: Array<String>) {
    val questions = QuestionFileManager(QUESTION_FILE, 10).getQuestions()
    val rating = RatingImpl(ANSWER_FILE, questions).getRating()

    println("Rating: $rating")
}

class Solution {

}