package com.diloo.Lookonusunnn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mail,sifre;
    private TextView kayitol,girisyap;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail=findViewById(R.id.mail);
        sifre=findViewById(R.id.sifre);
        kayitol=findViewById(R.id.kayitol);
        girisyap=findViewById(R.id.girisyap);
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayitekrani=new Intent(MainActivity.this,KayitEkrani.class);
                startActivity(kayitekrani);
            }
        });
        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    Toast.makeText(getApplicationContext(),"Once bilgileri gir! ",Toast.LENGTH_SHORT).show();
                    Intent ok=new Intent(MainActivity.this,MainActivity.class);

                    startActivity(ok);
                }else {
                    Loginuser();
                }

            }
        });
    }
    private void Loginuser() {
        String e_mail=mail.getText().toString();
        String password=sifre.getText().toString();
        if(TextUtils.isEmpty(e_mail)){
            Toast.makeText(getApplicationContext(),"Mail alanı bos ",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Şifre alanı bos ",Toast.LENGTH_LONG).show();
        }else {
            auth.signInWithEmailAndPassword(e_mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Giriş Başarılı ",Toast.LENGTH_SHORT).show();
                        Intent chat=new Intent(MainActivity.this,Chat_odalar.class);
                        startActivity(chat);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Giriş Başarısız :( ",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
