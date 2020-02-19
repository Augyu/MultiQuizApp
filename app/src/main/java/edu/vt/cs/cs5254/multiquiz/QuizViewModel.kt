package edu.vt.cs.cs5254.multiquiz


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {

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
}