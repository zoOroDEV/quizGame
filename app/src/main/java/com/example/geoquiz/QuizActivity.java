package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private TextView countTextView;
    private boolean[] checkButtonTrue = new boolean[6];
    private boolean[] checkButtonFalse = new boolean[6];

    Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_netherlands, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private int mCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
                checkButtonTrue[mCurrentIndex]=true;
                checkButton();
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButtonFalse[mCurrentIndex]=true;
                checkAnswer(false);
                updateQuestion();
                checkButton();
            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                checkButton();
                if (mCurrentIndex > 0 ){
                    mPrevButton.setVisibility(View.VISIBLE);
                }
                if (mCurrentIndex == 5 ){
                    mNextButton.setVisibility(View.GONE);
                }
            }
        });
        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex--;
                updateQuestion();
                checkButton();
                if (mCurrentIndex == 0 ){
                    mPrevButton.setVisibility(View.GONE);
                }
                if (mCurrentIndex < 5 ){
                    mNextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        countTextView= findViewById(R.id.textView);
        countTextView.setText(MessageFormat.format("Correct answer: {0}/6", mCounter));

    }
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        if (userAnswer == answerIsTrue) {
            mCounter+=1;
            countTextView.setText(MessageFormat.format("Correct answer: {0}/6", mCounter));
            Toast.makeText(QuizActivity.this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(QuizActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkButton(){
        if (checkButtonTrue[mCurrentIndex]) {
            mTrueButton.setEnabled(false);
            mFalseButton.setVisibility(View.GONE);
        }else if (!checkButtonTrue[mCurrentIndex]){
            mTrueButton.setEnabled(true);
            mFalseButton.setVisibility(View.VISIBLE);
        }
        if (checkButtonFalse[mCurrentIndex]) {
            mFalseButton.setEnabled(false);
            mTrueButton.setVisibility(View.GONE);
        }else if (!checkButtonFalse[mCurrentIndex]){
            mFalseButton.setEnabled(true);
            mTrueButton.setVisibility(View.VISIBLE);
        }
    }
}