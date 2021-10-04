package com.example.quizactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTrue;
    private Button btnFalse;
    private Button btnNext;
    private Button btnAnswer;
    private TextView txtQuestion;

    private Question[] listQuestion = new Question[]{
            new Question(R.string.question_text1, true),
            new Question(R.string.question_text2, false),
            new Question(R.string.question_text3, true),
            new Question(R.string.question_text4, true),
            new Question(R.string.question_text5, false),
            new Question(R.string.question_text6, true)
    };

    private int currentIndex = 0;
    private static final int REQUEST_CODE_ANSWER = 0;
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTION = "QUESTION";
    private static final String KEY_ANSWER = "ANSWER";
    private static final String KEY_ANSWER_SHOW = "ANSWER_SHOW";

    private boolean trampa;
    int bandera = 0 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }


        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        btnNext = findViewById(R.id.btnNext);
        btnAnswer = findViewById(R.id.btnAnswer);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuestion.setText(listQuestion[currentIndex].getTxtResId());

        btnAnswer.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
            intent.putExtra(KEY_QUESTION, listQuestion[currentIndex].getTxtResId());
            intent.putExtra(KEY_ANSWER, listQuestion[currentIndex].isAnswer());
            startActivityForResult(intent, REQUEST_CODE_ANSWER);
            //startActivity(intent);
        });

        btnTrue.setOnClickListener(view -> {
            checkAnswer(true);
        });

        btnFalse.setOnClickListener(view -> {
            checkAnswer(false);
        });

                btnNext.setOnClickListener(view -> {
                    if  (bandera == 0) {
                        Toast.makeText(MainActivity.this, "Debes contestar la pregunta actual para poder avanzar a la siguiente", Toast.LENGTH_SHORT).show();
                    } else if(currentIndex<=4) {
                        currentIndex = (currentIndex + 1) % listQuestion.length;
                        updateQuestion();
                        trampa = false;
                        bandera=0;
                    }else{
                        Toast.makeText(MainActivity.this, "Has finalizado el Quiz", Toast.LENGTH_SHORT).show();
                            finish();

                    }
        });}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ANSWER){
            if (data != null){
                trampa = data.getBooleanExtra(KEY_ANSWER_SHOW, false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt(KEY_INDEX, currentIndex);
        savedState.putBoolean("vartrampa",trampa);
        savedState.putInt("num",bandera);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        trampa = savedInstanceState.getBoolean("vartrampa");
        bandera = savedInstanceState.getInt("num");
    }

    private void updateQuestion(){
        int question = listQuestion[currentIndex].getTxtResId();
        txtQuestion.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answer = listQuestion[currentIndex].isAnswer();

        bandera++;
        int msgRId=0;
        if (trampa)  {
            msgRId = R.string.trampa_msg;
        }
        else if (userPressedTrue == answer){
            msgRId = R.string.true_msg;
        }
        else{
            msgRId = R.string.false_msg;
        }
        Toast.makeText(this, msgRId, Toast.LENGTH_SHORT).show();

    }

}