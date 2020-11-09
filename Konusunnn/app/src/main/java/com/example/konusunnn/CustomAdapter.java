package com.example.konusunnn;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<mesaj> mesajlist;
    FirebaseUser fuser;
    public CustomAdapter(Activity activity,ArrayList<mesaj> mesajlist,FirebaseUser fuser){
        this.mesajlist=mesajlist;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fuser=fuser;
    }
    @Override
    public int getCount() { return mesajlist.size(); }
    @Override
    public Object getItem(int position) {return mesajlist.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satir=null;
        mesaj mesaj=mesajlist.get(position);
        if(mesaj.getGonderen()==fuser.getEmail()){
            satir=layoutInflater.inflate(R.layout.satir_sag,null);
            TextView mailim=satir.findViewById(R.id.mailim);
            mailim.setText(mesaj.getGonderen());
            TextView mesajim=satir.findViewById(R.id.mesajim);
            mesajim.setText(mesaj.getMesaj());
            TextView zamanim=satir.findViewById(R.id.zamanim);
            zamanim.setText(mesaj.getZaman());

        }
        else {
            satir=layoutInflater.inflate(R.layout.satir_sol,null);
            TextView maili=satir.findViewById(R.id.maili);
            maili.setText(mesaj.getGonderen());
            TextView mesaji=satir.findViewById(R.id.mesaji);
            mesaji.setText(mesaj.getMesaj());
            TextView zamani=satir.findViewById(R.id.zamani);
            zamani.setText(mesaj.getZaman()); }
        return satir;
    }
}
