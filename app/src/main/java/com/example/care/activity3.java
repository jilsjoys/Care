package com.example.care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class activity3 extends AppCompatActivity {
    private Button buttonprev2,buttonnext3;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    RadioButton b1, b2;
    private  int per,perc1,perc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent();
        per = mIntent.getIntExtra("perc", 0);
        perc1 = mIntent.getIntExtra("perc1", 0);
        setContentView(R.layout.activity_activity3);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        Slidr.attach(this);

        buttonnext3 = (Button) findViewById(R.id.button_next3);

        radioGroup = (RadioGroup) findViewById(R.id.radio_button);
        b1 = (RadioButton) findViewById(R.id.yes);
        b2 = (RadioButton) findViewById(R.id.not);

        buttonnext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if(radioButton.equals(b1))
                {
                   perc2=20;


                }
                else
                {
                    perc2=0;
                }

                Intent next1 = new Intent(activity3.this,activity4.class);
                next1.putExtra("perc", per);
                next1.putExtra("perc1", perc1);
                next1.putExtra("perc2", perc2);
                startActivity(next1);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }

    public void escor(View view) {
    }

    public void nocoron(View view) {
    }
}