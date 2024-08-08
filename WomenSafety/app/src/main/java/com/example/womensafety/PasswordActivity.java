package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private TextView note;
    private EditText email;
    private Button send;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        firebaseAuth=FirebaseAuth.getInstance();
        note=(TextView)findViewById(R.id.note1);
        email=(EditText)findViewById(R.id.emailidet);
        send=(Button)findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml=email.getText().toString();
                if(TextUtils.isEmpty(eml)){
                    Toast.makeText(PasswordActivity.this,"Please Enter your valid Email",Toast.LENGTH_LONG).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(eml).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this,"Please check your email account to reset your password",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }
                            else {
                                String message=task.getException().getMessage();
                                Toast.makeText(PasswordActivity.this,"Error Occured"+message,Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
