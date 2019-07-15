package com.kiryanov.gatestsolver.test_module

import com.kiryanov.gatestsolver.model.Question

interface QuestionReader {

    fun getQuestions(): ArrayList<Question>
}