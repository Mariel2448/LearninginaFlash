package com.codepath.mariel.learninginaflash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {

                                                                     @Override
                                                                     public void onClick(View v) {
                                                                         findViewById(R.id.flashcard_question).setCameraDistance(25000);

                                                                         final View questionSideView = findViewById(R.id.flashcard_question);

                                                                         questionSideView.animate()
                                                                                 .rotation(90)
                                                                                 .setDuration(200)
                                                                                 .withEndAction(
                                                                                         new Runnable() {
                                                                                             @Override
                                                                                             public void run() {
                                                                                                 questionSideView.setVisibility(View.INVISIBLE);
                                                                                                 findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);

                                                                                                 findViewById(R.id.flashcard_answer).setRotation(-90);
                                                                                                 findViewById(R.id.flashcard_answer).animate()
                                                                                                         .rotation(0)
                                                                                                         .setDuration(200)
                                                                                                         .start();
                                                                                             }
                                                                                         }
                                                                                 ).start();
                                                                         //          findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                                                                         //          findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);

                                                                     }
                                                                 });
        



        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);

            }
        });
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.addicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);

            }
        });

        findViewById(R.id.nexticon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation RightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.flashcard_question).startAnimation(RightInAnim);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                currentCardDisplayedIndex++;
                findViewById(R.id.flashcard_question).startAnimation(RightInAnim);

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 &&resultCode == RESULT_OK) {
            String editQuestion = data.getExtras().getString("editQuestion");
            String editAnswer = data.getExtras().getString("editAnswer");


            ((TextView) findViewById(R.id.flashcard_question)).setText(editQuestion);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(editAnswer);

            flashcardDatabase.insertCard(new Flashcard(editQuestion, editAnswer));
            allFlashcards = flashcardDatabase.getAllCards();

        }





    }
}
