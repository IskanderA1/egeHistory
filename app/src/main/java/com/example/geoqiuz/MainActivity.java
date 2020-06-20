package com.example.geoqiuz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;
    private long userId=0;
    SimpleCursorAdapter userAdapter;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_1,false),
            new Question(R.string.question_2,true),
            new Question(R.string.question_3,false),
            new Question(R.string.question_4,false),
            new Question(R.string.question_5,false),
            new Question(R.string.question_6,true),
            new Question(R.string.question_7,true),
            new Question(R.string.question_8,false),
            new Question(R.string.question_9,true),
            new Question(R.string.question_10,false),
            new Question(R.string.question_11,false),
    };

    private int mCurrentIndex = 0;

    private int[] mStackQuestion = new int[mQuestionBank.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillingOutStackQuestion();
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        //updateQuestion();
        //===================
        dbHelper = new DBHelper(this);



        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = dbHelper.open();
                Cursor cursor = database.query(DBHelper.MONARCH_TABLE, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_DATE_START);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", dataStart = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();

            }
        });

        dbHelper.close();
        //===================

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
/*        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBank.length;
                updateQuestion();
            }
        });*/
    }



    private void updateQuestion(){
        int question = mQuestionBank[mStackQuestion[mCurrentIndex]].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mStackQuestion[mCurrentIndex]].isAnswerTrue();
        int messageResId = 0;
        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }
        Toast toast = Toast.makeText(this, messageResId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,130);
        toast.show();
    }
    private void fillingOutStackQuestion(){
        for(int i=0;i<mStackQuestion.length;i++){
            mStackQuestion[i]=i;
        }
        shuffleArray(mStackQuestion);
    }
    public static void shuffleArray(int[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int temp = a[i];
        a[i] = a[change];
        a[change] = temp;
    }
}