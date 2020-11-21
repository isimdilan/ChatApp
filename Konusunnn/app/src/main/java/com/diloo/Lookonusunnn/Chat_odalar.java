package com.diloo.Lookonusunnn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat_odalar extends AppCompatActivity {
    private TextView chat_odalari_isim;
    private EditText newchatname;
    private Button sohbetodasiekle,buttonresimler;
    private ListView listview;
    private ImageView buttonn2;
    private ArrayList<String> chatodalar;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_odalar);

        chat_odalari_isim=findViewById(R.id.chat_odalari_isim);
        newchatname=findViewById(R.id.newchatname);
        buttonn2=findViewById(R.id.buttonn2);

        buttonn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gerigiris=new Intent(Chat_odalar.this,MainActivity.class);
                startActivity(gerigiris);
                finish();

            }
        });
        sohbetodasiekle=findViewById(R.id.sohbetodasiekle);
        listview=findViewById(R.id.listview);
        chatodalar=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1,chatodalar);




        database=FirebaseDatabase.getInstance();
        final DatabaseReference dbref=database.getReference("chats");
        sohbetodasiekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oda=newchatname.getText().toString();
                    if(newchatname.getText().toString()!=" "){
                        dbref.child(oda).setValue(" ");
                        newchatname.setText(" ");
                    }else {Toast.makeText(Chat_odalar.this,"Oda ismi yazınız",Toast.LENGTH_SHORT).show();}



            }
        });
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatodalar.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    chatodalar.add(ds.getKey());
                }
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String secilenoda=chatodalar.get(position);
                Intent odaintent=new Intent(getApplicationContext(),Chat_yap.class);
                odaintent.putExtra("odakey",secilenoda);

                startActivity(odaintent);
            }
        });



    }
}