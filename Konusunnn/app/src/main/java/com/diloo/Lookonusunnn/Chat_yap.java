package com.diloo.Lookonusunnn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Chat_yap extends AppCompatActivity {
    private TextView chatoda;
    private EditText mesajgonder;
    private Button gondersari;
    private ImageView buttonn;
    private ListView listviewchatyap;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_yap);
        chatoda=findViewById(R.id.chatoda);
        buttonn=findViewById(R.id.buttonn);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gerioda=new Intent(Chat_yap.this,Chat_odalar.class);
                startActivity(gerioda);
                finish();
            }
        });
        mesajgonder=findViewById(R.id.mesajgonder);
        gondersari=findViewById(R.id.gondersari);
        listviewchatyap=findViewById(R.id.listviewchatyap);
        String oda=getIntent().getStringExtra("odakey");
        chatoda.setText(oda);
        final ArrayList<mesaj> mesajArrayList=new ArrayList<>();
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance();
        final DatabaseReference dbref=database.getReference("chats/"+oda);
        gondersari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gonderen=firebaseUser.getEmail();
                String mesaj=mesajgonder.getText().toString();
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:dd");
                String zaman=sdf.format(new Date());
                dbref.push().setValue(new mesaj(gonderen,mesaj,zaman));
                mesajgonder.setText(" ");
            }
        });
        final CustomAdapter adapter=new CustomAdapter(this,mesajArrayList,firebaseUser);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mesajArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    mesajArrayList.add(ds.getValue(mesaj.class));
                }
                listviewchatyap.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}