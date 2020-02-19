package edu.vt.cs.cs5254.multiquiz

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class QuizActivity : AppCompatActivity() {

    private val defaultButtonColor = "#5cb85c"
    private val selectedButtonColor = "#d9534f"

    private lateinit var questionTextView: TextView
    private lateinit var answerButtonList: List<Button>
    private lateinit var hintButton: Button
    private lateinit var submitButton: Button


    private val questionList = listOf(
        Question(
            R.string.australia_question, listOf(
                Answer(R.string.australia_answer_canberra, true),
                Answer(R.string.australia_answer_brisbane, false),
                Answer(R.string.australia_answer_perth, false),
                Answer(R.string.australia_answer_sydney, false)
            )
        ),
        Question(
            R.string.math_question, listOf(
                Answer(R.string.math_answer_1, false),
                Answer(R.string.math_answer_2, true),
                Answer(R.string.math_answer_3, false),
                Answer(R.string.math_answer_4, false)
            )
        ),
        Question(
            R.string.taiwan_question, listOf(
                Answer(R.string.taiwan_answer_keelung, false),
                Answer(R.string.taiwan_answer_tainan, false),
                Answer(R.string.taiwan_answer_taipei, true),
                Answer(R.string.taiwan_answer_taichung, false)
            )
        ),
        Question(
            R.string.china_question, listOf(
                Answer(R.string.china_answer_shanghai, false),
                Answer(R.string.china_answer_hongkong, false),
                Answer(R.string.china_answer_wuhan, false),
                Answer(R.string.china_answer_beijing, true)
            )
        )
    )
    var currentQuestionIndex = 0
    var question = questionList[currentQuestionIndex]
    var answerList = question.answers
    var score = 0
    var hintUsed = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(quizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        questionTextView = findViewById(R.id.question_text_view)
        answerButtonList = listOf(
            findViewById(R.id.answer_0_button),
            findViewById(R.id.answer_1_button),
            findViewById(R.id.answer_2_button),
            findViewById(R.id.answer_3_button)
        )
        hintButton = findViewById(R.id.hint_button)
        submitButton = findViewById(R.id.submit_button)

        // set question text
        questionTextView.setText(R.string.australia_question)
        for (index in answerButtonList.indices){
            answerButtonList[index].setOnClickListener{
                processAnswerButtonClick(index)
            }
        }
        // set text in answer button views

        // set text for disable and reset buttons
        hintButton.setText("Hint")
        submitButton.setText("Submit")

        // attach listeners to answer buttons
        // TODO move this info refreshView
        questionTextView.setText(question.textResId)
//        for ((answer, button) in answerList.zip(answerButtonList)) {
//            button.setText((answer.textResId))
//            button.setOnClickListener {
//                processAnswerButtonClick(answer)
//            }
//        }
        // attach listeners to disable button and reset button
        hintButton.setOnClickListener {
            processHintButtonClick()
        }
        submitButton.setOnClickListener {
            processSubmitButtonClick()
        }
        refreshView()
    }

    private fun processAnswerButtonClick(answerIndex: Int) {
        val answer = answerList[answerIndex]
        answerList.minus(answer).map { it.isSelected = false }
        // toggle clicked button
        answer.isSelected = !answer.isSelected
//        Toast.makeText(this, answer.isSelected.toString(), Toast.LENGTH_SHORT).show()
        // refresh the view
        refreshView()
    }

    private fun processHintButtonClick() {
        // disable the first two incorrect answers (even if one is selected)
        answerList
            .filterNot { e -> e.isCorrect || !e.isEnabled }
//            .filterNot { e -> !e.isEnabled }
            .take(1) //TODO could randomly choose (or not)
            .forEach { a ->
                a.isEnabled = false
                a.isSelected = false
            }
        hintUsed ++
        refreshView()
    }

    private fun processSubmitButtonClick() {
        val selectedAnswer: Answer = answerList.first { it.isSelected }
        if (selectedAnswer.isCorrect) { score++ }
//            Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "incorrect", Toast.LENGTH_SHORT).show()
//        }
        // TODO use forEach instead of for loop
        if (currentQuestionIndex ==3){
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("correct", score.toString())
            intent.putExtra("hint",hintUsed.toString())
            startActivity(intent)
        }
        currentQuestionIndex = (currentQuestionIndex+1) %4
        question = questionList[currentQuestionIndex]
        answerList = question.answers
        for (answer in answerList) {
            answer.isEnabled = true
            answer.isSelected = false
        }

        refreshView()
    }

    private fun refreshView() {
        questionTextView.setText(question.textResId)
        for ((answer,button)in answerList.zip(answerButtonList)){
            button.setText(answer.textResId)
        }
        hintButton.setText(R.string.hint_button_text)
        submitButton.setText(R.string.submit_button_text)
        // TODO use forEach with (answer, button) pair and zipped lists
        for ((answer, button) in answerList.zip(answerButtonList)) {
            button.isEnabled = answer.isEnabled
            button.isSelected = answer.isSelected
            if (answer.isSelected) {
                setButtonColor(button, selectedButtonColor)
            } else {
                setButtonColor(button, defaultButtonColor)
            }
            if (!answer.isEnabled) {
                button.alpha = .5f
                // TODO put on its own line after the main loop (use any)
//                disableButton.isEnabled = false // disable if any answers are disabled
            }
        }
        hintButton.isEnabled = answerList.any{it.isEnabled && !it.isCorrect} //return true if none of the elements match the given predicate.
        submitButton.isEnabled = answerList.any{it.isSelected}
    }

    private fun setButtonColor(button: Button, colorString: String) {
        button.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(colorString))
        button.setTextColor(Color.WHITE)
        button.alpha = 1f
    }
}
