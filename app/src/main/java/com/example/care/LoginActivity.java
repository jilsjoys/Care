package com.example.care;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {


    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        InitializeFields();
        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
       LoginButton.setOnClickListener(new View.OnClickListener() {
           @Override
        public void onClick(View view)
           {
        AllowUserToLogin();
          }
        });

        }

    private void AllowUserToLogin()
    {
        String email=UserEmail.getText().toString();
        String password=UserPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"please enter email address.", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please enter password", Toast.LENGTH_SHORT).show();
        }else
        {
            loadingbar.setTitle("Sign in");
            loadingbar.setMessage("Please wait..");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();

           mAuth.signInWithEmailAndPassword(email,password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               if (task.isSuccessful()) {


                                   SendUserToMainActivity();
                                   Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                   loadingbar.dismiss();

                               }


                           }

                                               else {

                                                   String message = task.getException().toString();
                                                   Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                                   loadingbar.dismiss();

                                               }

                                           }
                                       });
                           }
                       }

    private void InitializeFields()
    {
        LoginButton=(Button)findViewById(R.id.login_button);
   //     PhoneLoginButton=(Button)findViewById(R.id.phone_login_button);
        UserEmail=(EditText) findViewById(R.id.login_email);
        UserPassword=(EditText) findViewById(R.id.login_password);
        NeedNewAccountLink=(TextView)findViewById(R.id.need_new_account_link);
   //     ForgetPasswordLink=(TextView)findViewById(R.id.forget_password_link);
        loadingbar=new ProgressDialog(this);
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent =new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
