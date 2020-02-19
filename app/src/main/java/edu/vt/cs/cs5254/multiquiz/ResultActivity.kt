package edu.vt.cs.cs5254.multiquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.view.*

class ResultActivity : AppCompatActivity() {
    private lateinit var totalQuestionTextView:TextView
    private lateinit var totalCorrectTextView:TextView
    private lateinit var hintUsedTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val intent = intent
        val score = intent.getStringExtra("correct")
        val hint = intent.getStringExtra("hint")
//        Toast.makeText(this,score,Toast.LENGTH_SHORT).show()
        totalQuestionTextView = findViewById(R.id.total_questions_value)
        totalCorrectTextView = findViewById(R.id.total_correct_value)
        hintUsedTextView = findViewById(R.id.hints_used_value)
        totalCorrectTextView.text = score
        hintUsedTextView.text = hint
        totalQuestionTextView.text = "4"


    }
}
