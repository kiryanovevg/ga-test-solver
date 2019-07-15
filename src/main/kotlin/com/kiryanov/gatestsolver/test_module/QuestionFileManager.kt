package com.kiryanov.gatestsolver.test_module

import com.kiryanov.gatestsolver.model.Question
import java.io.File
import java.util.concurrent.ThreadLocalRandom

class QuestionFileManager(
        private val fileName: String,
        private val questionCount: Int
) : QuestionReader {

    companion object {
        private const val QUESTION_PREFIX = "###"
        private const val ANSWER_DIV = " - "
//        private const val COUNT = 10
    }

    override fun getQuestions(): ArrayList<Question> = getQuestionsFromFile(File(fileName))
            .toSize(questionCount)

    fun saveQuestions(questions: ArrayList<Question>, fileName: String) {
        val newQuestions = getQuestionsFromFile(File(fileName))
                .toHashSet()
                .apply { addAll(questions) }
                .toMutableList()
                .sortBy { it.id }

        
    }

    private fun getQuestionsFromFile(file: File): ArrayList<Question> {
        val questions = ArrayList<Question>()

        file.bufferedReader().use {
            var line: String? = it.readLine()
            while (line != null) {
                when {
                    line.contains(QUESTION_PREFIX) -> {
                        questions.add(Question(
                                readQuestionId(line), readQuestion(line), ArrayList()
                        ))
                    }
                    line.contains(ANSWER_DIV) -> {
                        if (questions.isNotEmpty()) questions[questions.size - 1]
                                .answers.add(readAnswer(line))
                    }
                }

                line = it.readLine()
            }
        }

        return questions
    }

    private fun ArrayList<Question>.toSize(size: Int): ArrayList<Question> {
        while (this.size != size) {
            val r = ThreadLocalRandom.current().nextInt(0, this.size)
            this.removeAt(r)
        }

        return this
    }

    private fun readAnswer(line: String) = line.split(ANSWER_DIV)[1]

    private fun readQuestion(line: String) = line.removePrefix(QUESTION_PREFIX)

    private fun readQuestionId(line: String): Int {
        val split = line.split(" ")
        return split[split.size - 1].toInt()
    }
}