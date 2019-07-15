package com.kiryanov.gatestsolver.test_module

import com.kiryanov.gatestsolver.model.Question
import java.io.File
import java.util.concurrent.ThreadLocalRandom

class QuestionFileManager(
        private val fileName: String,
        private val questionCount: Int,
        private val random: Boolean = false
) : QuestionReader {

    companion object {
        private const val QUESTION_PREFIX = "###"
        private const val ANSWER_DIV = " - "
    }

    override fun getQuestions(): ArrayList<Question> = getQuestionsFromFile(File(fileName))
            .toSize(questionCount)

    fun saveQuestions(questions: ArrayList<Question>, fileName: String) {
        val file = File(fileName)
        if (!file.exists()) file.createNewFile()

        val newQuestions = getQuestionsFromFile(file)
                .toHashSet()
                .apply { addAll(questions) }
                .toMutableList()
                .apply { sortBy { it.id } }

        file.bufferedWriter().use { writer ->
            writer.write("")
            newQuestions.forEach { question ->
                writer.append("$QUESTION_PREFIX${question.text}")
                repeat(question.answers.size) { i ->
                    val text = question.answers[i]
                    writer.newLine()
                    writer.append("${i + 1}$ANSWER_DIV$text")
                }
                writer.newLine()
            }
        }
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
            val index = if (random) ThreadLocalRandom.current().nextInt(0, this.size) else this.size - 1
            this.removeAt(index)
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