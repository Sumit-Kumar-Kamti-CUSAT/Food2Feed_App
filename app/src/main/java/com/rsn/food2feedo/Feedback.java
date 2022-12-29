package com.rsn.food2feedo;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {
    TextView tvFeedback;
    RatingBar rbStars;
    Button send;
    EditText feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feed=findViewById(R.id.feedtext);
        tvFeedback=findViewById(R.id.tvFeedback);
        rbStars=findViewById(R.id.rbStars);
        send=findViewById(R.id.btnSend);
        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==1)
                {
                    tvFeedback.setText("Very Dissatisfied");
                }
                else if(rating==2)
                {
                    tvFeedback.setText("Dissatisfied");
                }
                else if(rating==3 )
                {
                    tvFeedback.setText("Good");
                }

                else if(rating==4)
                {
                    tvFeedback.setText("Satisfied");
                }
                else if(rating ==5)
                {
                    tvFeedback.setText("Very Satisfied");
                }

            }

        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st=feed.getText().toString();
                AlertDialog.Builder exitDialog=new AlertDialog.Builder(Feedback.this);
                exitDialog.setTitle("Thank You");
                exitDialog.setMessage("Thank You for valuable feedback");

                exitDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                exitDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                exitDialog.show();
            }
        });
    }
}