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

public class KayitEkrani extends AppCompatActivity {
    private EditText kullanici_adi,mail,sifre;
    private TextView kayitol,kayitol2;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitekrani);
        kullanici_adi=findViewById(R.id.kullanici_adi);
        mail=findViewById(R.id.mail);
        sifre=findViewById(R.id.sifre);
        kayitol=findViewById(R.id.kayitol);
        kayitol2=findViewById(R.id.kayitol2);
        kayitol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecisgiris=new Intent(KayitEkrani.this,MainActivity.class);
                startActivity(gecisgiris);
            }
        });
        auth=FirebaseAuth.getInstance();
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewaccount();
            }
        });
    }

    private void createNewaccount() {
        final String kullaniciadi=kullanici_adi.getText().toString();
        String email=mail.getText().toString();
        String password=sifre.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"E-mail alanı boş olamaz...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Bos sifre kabul etmiyoruz...",Toast.LENGTH_SHORT).show();
        }else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(KayitEkrani.this,"Kayıt başarılı Hosgeldin "+kullaniciadi,Toast.LENGTH_LONG).show();
                        Intent loginolundu=new Intent(KayitEkrani.this,MainActivity.class);
                        startActivity(loginolundu);
                        finish();
                    }else {
                        Toast.makeText(KayitEkrani.this,"Hata olustu :( Lutfen Dilan'a bildiriniz ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
