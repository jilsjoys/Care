package com.example.care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class result extends AppCompatActivity {
    private  int per,perc1,perc2,perc3,perc4,perc100;
    TextView corona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent mIntent = getIntent();
        per = mIntent.getIntExtra("perc", 0);
        perc1 = mIntent.getIntExtra("perc1", 0);
        perc2 = mIntent.getIntExtra("perc2", 0);
        perc3 = mIntent.getIntExtra("perc3", 0);
        perc4 = mIntent.getIntExtra("perc4", 0);
       corona=(TextView) findViewById(R.id.corona);
       perc100=per+perc1+perc2+perc3+perc4;

corona.setText("You have "+perc100+"%" +" chance of corona");
    }

    @Override
    public void onBackPressed() {

        Intent next1 = new Intent(result.this,MainActivity.class);
        startActivity(next1);
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}