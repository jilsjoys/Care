package com.example.care;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public  class MainActivity extends AppCompatActivity   {


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
private RadioGroup radioGroup;
private RadioButton radioButton;
    ProgressBar mProgressBar, mProgressBar1;
    RadioButton b1, b2;
    private Button buttonStartTime, buttonStopTime,buttonnext1 ;


    private TextView textViewShowTime,areyou;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    private  int per=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Care");

        final ActionBar actionBar  =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


    drawerLayout=findViewById(R.id.reativelayout);
    navigationView=findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.hospitals:
                        Intent intent = new Intent(MainActivity.this, hospitals.class);
                        startActivity(intent);
                        break;

                    case R.id.tips:
                        Intent myintent = new Intent(MainActivity.this, tips.class);
                        startActivity(myintent);
                        break;

                    case R.id.track:
                       Intent yintent = new Intent(MainActivity.this, TrackingActivity.class);
                       startActivity(yintent);
                       break;
                    case R.id.logout:
                        Intent zintent = new Intent(MainActivity.this, LoginActivity.class);
                        zintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(zintent);
                        break;
                }

                return true;
            }







        });

        RootRef = FirebaseDatabase.getInstance().getReference();
        areyou = (TextView) findViewById(R.id.are_you);

        buttonStartTime = (Button) findViewById(R.id.button_timerview_start);
        buttonStopTime = (Button) findViewById(R.id.button_timerview_stop);
        buttonnext1 = (Button) findViewById(R.id.button_next);
        radioGroup = (RadioGroup) findViewById(R.id.radio_button);
        b1 = (RadioButton) findViewById(R.id.yes);
        b2 = (RadioButton) findViewById(R.id.not);


buttonnext1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
    if(radioButton.equals(b1))
    {
        per=per+20;

    }
    else{
        per=0;
    }





       Intent next1 = new Intent(MainActivity.this, activity2.class);
        next1.putExtra("perc", per);
        startActivity(next1);




    }
});
        textViewShowTime = (TextView)
                findViewById(R.id.textView_timerview_time);




        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_timerview);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressbar1_timerview);





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.location,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
                case  R.id.location:

                    {
                        Intent yintent = new Intent(MainActivity.this, location.class);
                        startActivity(yintent);
                }
            }




        return super.onOptionsItemSelected(item);
    }

    public void start(View view) {


        setTimer();

        buttonStartTime.setVisibility(View.INVISIBLE);
        buttonStopTime.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        buttonnext1.setVisibility(View.INVISIBLE);
        startTimer();
        mProgressBar1.setVisibility(View.VISIBLE);
    }
    public void stop(View view) {

            countDownTimer.cancel();
            countDownTimer.onFinish();
            mProgressBar1.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

            buttonStartTime.setVisibility(View.VISIBLE);
            buttonStopTime.setVisibility(View.INVISIBLE);
           areyou.setVisibility(View.VISIBLE);
           buttonnext1.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);

        }

    private void setTimer(){


        totalTimeCountInMilliseconds =  10 * 1000;
        mProgressBar1.setMax( 10 * 1000);
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                mProgressBar1.setProgress((int) (leftTimeInMilliseconds));

                textViewShowTime.setText(String.format("%01d", seconds % 60));
                buttonnext1.setVisibility(View.INVISIBLE);
                areyou.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onFinish() {
                textViewShowTime.setText("10");
                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar1.setVisibility(View.GONE);
                areyou.setVisibility(View.VISIBLE);
                buttonnext1.setVisibility(View.VISIBLE);
               radioGroup.setVisibility(View.VISIBLE);


            }
        }.start();
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        currentUser = mAuth.getCurrentUser();
        super.onStart();
        if (currentUser == null) {
            SendUserToLoginActivity();
        } else {

            VerifyUserExistence();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }


    private void VerifyUserExistence() {
        String CurrentUserId = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("name").exists())) {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

public  void checkButton(View v)
{
    int radioid=radioGroup.getCheckedRadioButtonId();
    radioButton=findViewById(radioid);

}

}
