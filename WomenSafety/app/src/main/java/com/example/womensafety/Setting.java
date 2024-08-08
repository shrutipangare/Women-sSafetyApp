package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Setting extends AppCompatActivity {
    Button buse;
    EditText n1,n2;
    Button save,update;
    WomenSafety w;
    private DatabaseReference dbWomenSafety;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buse=(Button)findViewById(R.id.button2);
        n1=(EditText)findViewById(R.id.ph1);
        n2=(EditText)findViewById(R.id.ph2);
        save=(Button)findViewById(R.id.bsave);
        w=new WomenSafety();
        mauth=FirebaseAuth.getInstance();
        dbWomenSafety= FirebaseDatabase.getInstance().getReference(mauth.getUid());

        buse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=validate(new EditText[]{n1,n2});
                if(res==false){
                    Toast.makeText(Setting.this,"Enter a phone number",Toast.LENGTH_LONG).show();
                }
                else{
                    w.setPhno1(n1.getText().toString().trim());
                    w.setPhno2(n2.getText().toString().trim());
                    dbWomenSafety.child("setting").setValue(w);
                    Toast.makeText(Setting.this,"Phone number added",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void openDialog(){
        InfoDialog infoDialog=new InfoDialog();
        infoDialog.show(getSupportFragmentManager(),"How to use ?");

    }
    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }

}
