package com.kiryanov.gatestsolver.rating

import com.kiryanov.gatestsolver.model.Question
import com.kiryanov.gatestsolver.test_module.AnswerReader

class RatingImpl(
        fileName: String,
        private val questions: List<Question>
): Rating {

    companion object {
        private const val MAX_RATING = 5.0
    }

    private var answers: HashMap<Int, Int> = AnswerReader.readAnswersFile(fileName)

    override fun getRating(): Double {
        var count = questions.size
        questions.forEach { if (it.selected != answers[it.id]) count -= 1 }

        return MAX_RATING * count / questions.size
    }
}