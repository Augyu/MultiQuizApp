package edu.vt.cs.cs5254.multiquiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private val defaultButtonColor = "#00a2ff"
    private val selectedButtonColor = "#cb297b"

    private lateinit var questionTextView: TextView
    private lateinit var answerButtonList: List<Button>
    private lateinit var hintButton: Button
    private lateinit var submitButton: Button

    private val answerList = listOf(
        Answer(R.string.australia_answer_brisbane, false),
        Answer(R.string.australia_answer_canberra, true),
        Answer(R.string.australia_answer_perth, false),
        Answer(R.string.australia_answer_sidney, false)
    )

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

        // set text in answer button views
        // TODO use pairs and zipped lists
        // TODO eventually move this to refreshView
//        for (index in 0..3) {
//            answerButtonList[index].setText(answerList[index].textResId)
//        }

        // set text for disable and reset buttons
        hintButton.setText("Hint")
//        submitButton.setText("Submit")

        // attach listeners to answer buttons
        // TODO use pairs and zipped lists
        // TODO eventually get rid of refs to answerList
        // TODO eventually change pABC so it takes an index
//        for (index in 0..3) {
//            answerButtonList[index].setOnClickListener {
//                processAnswerButtonClick(answerList[index])
//            }
//        }
        for ((answer, button) in answerList.zip(answerButtonList)){
            button.setText((answer.textResId))
            button.setOnClickListener{
                processAnswerButtonClick(answer)
            }
        }
        // attach listeners to disable button and reset button
        hintButton.setOnClickListener {
            processHintButtonClick()
        }
        submitButton.setOnClickListener {
            processSubmitButtonClick()
        }

        refreshView()
    }

    private fun processAnswerButtonClick(answer: Answer) {

//        val origIsSelected = answer.isSelected

        // deselect all answers
        // TODO deselect only non-clicked answers (use minus)
//        for (a in answerList.minus(answer)) {
//            a.isSelected = false
//        }
        answerList.minus(answer).map { it.isSelected = false }
        // toggle clicked button
        // TODO toggle based on current value and remove local val
        answer.isSelected = !answer.isSelected
        // Toast.makeText(this, answer.isSelected.toString(), Toast.LENGTH_SHORT).show()
        // refresh the view
        refreshView()
    }

    private fun processHintButtonClick() {
        // disable the first two incorrect answers (even if one is selected)
        // TODO use lambda functions filter & take to get first 2 incorrect answers,
        // TODO and then forEach to disable them
//        var count = 0
//        for (answer in answerList) {
//            if (!answer.isCorrect) {
//                answer.isEnabled = false
//                answer.isSelected = false // deselect when answer is disabled
//                count++
//                if (count == 2) {
//                    break
//                }
//            }
//        }
        answerList
            .filterNot{ e -> e.isCorrect }
            .filterNot { e -> !e.isEnabled }
            .take(1)
            .forEach { a ->
                a.isEnabled = false
                a.isSelected = false
            }
        refreshView()
    }

    private fun processSubmitButtonClick() {

        // TODO use forEach instead of for loop
        for (answer in answerList) {
            answer.isEnabled = true
            answer.isSelected = false
        }
        refreshView()
    }

    private fun refreshView() {

        hintButton.isEnabled = true

        // TODO use forEach with (answer, button) pair and zipped lists
        for ((answer, button) in answerList.zip(answerButtonList)) {
//            val answer = answerList[index]
//            val button = answerButtonList[index]
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
        hintButton.isEnabled = answerList.filterNot{ e -> e.isCorrect }.any{ a-> a.isEnabled } //return true if none of the elements match the given predicate.

    }

    private fun setButtonColor(button: Button, colorString: String) {
        button.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(colorString))
        button.setTextColor(Color.WHITE)
        button.alpha = 1f
    }
}
