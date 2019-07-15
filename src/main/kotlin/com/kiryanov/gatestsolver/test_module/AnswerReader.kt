package com.kiryanov.gatestsolver.test_module

import java.io.File

class AnswerReader {

    companion object {
        private const val DIV = ":"

        fun readAnswersFile(fileName: String): HashMap<Int, Int> {
            val answers = HashMap<Int, Int>()
            val file = File(fileName)

            file.bufferedReader().use {
                var line: String? = it.readLine()

                while (line != null) {
                    val id = line.split(DIV)[0].toInt()
                    val answer = line.split(DIV)[1].toInt()

                    answers[id] = answer

                    line = it.readLine()
                }
            }

            return answers
        }
    }
}