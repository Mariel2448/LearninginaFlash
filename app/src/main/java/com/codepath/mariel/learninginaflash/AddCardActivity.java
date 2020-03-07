package com.codepath.mariel.learninginaflash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.deleteicon) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent(AddCardActivity.this, MainActivity.class);
                finish();
            }
        });

        findViewById(R.id.saveicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent(AddCardActivity.this, MainActivity.class);

                EditText editQuestion = findViewById(R.id.editQuestion);
                EditText editAnswer = findViewById(R.id.editAnswer);

                Intent data = new Intent();
                data.putExtra("editQuestion", editQuestion.getText().toString());
                data.putExtra("editAnswer", editAnswer.getText().toString());

                setResult(RESULT_OK, data);

                finish();

            }
        });
    }

}
