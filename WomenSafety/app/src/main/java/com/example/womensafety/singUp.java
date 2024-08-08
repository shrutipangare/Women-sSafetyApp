package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class singUp extends AppCompatActivity {
    private EditText cpass,userpass,useremail;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        cpass=(EditText)findViewById(R.id.cpasswordet);
        useremail=(EditText)findViewById(R.id.emailet);
        userpass=(EditText)findViewById(R.id.passwordet);
        cpass=(EditText)findViewById(R.id.cpasswordet);
        useremail=(EditText)findViewById(R.id.emailet);
        userpass=(EditText)findViewById(R.id.passwordet);

        register=(Button)findViewById(R.id.registerbtn);
        progressDialog=new ProgressDialog(this);

        firebaseAuth=firebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res=Check();
                if(res==true){
                    String user_email=useremail.getText().toString().trim();
                    String user_pass=userpass.getText().toString().trim();
                    progressDialog.setMessage("Registering...");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(singUp.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(singUp.this,homepage.class));
                            }
                            else
                            {
                                Toast.makeText(singUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private Boolean Check() {
        String stremail = useremail.getText().toString();
        boolean val3 = Patterns.EMAIL_ADDRESS.matcher(stremail).matches();
        if (stremail.isEmpty()) {
            useremail.setError("Email cannot be empty!");
        } else if (val3 == false) {
            useremail.setError("Enter valid email address!");
        }

        String strpass1 = userpass.getText().toString();
        boolean val4 = PASSWORD_PATTERN.matcher(strpass1).matches();
        if (strpass1.isEmpty()) {
            userpass.setError("Password cannot be empty!");
        } else if (val4 == false) {
            userpass.setError("Weak password!");
        }
        String strpass2 = cpass.getText().toString();
        Boolean result = false;

        if (!strpass1.equals(strpass2)) {
            cpass.setError("Password does not match");
        } else if (val3 == true && val4 == true) {



            if (stremail.isEmpty() || strpass1.isEmpty() || strpass2.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                result = true;
            }

        }
        return result;
    }
}

