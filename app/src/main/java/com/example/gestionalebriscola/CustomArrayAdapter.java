package com.example.gestionalebriscola;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CustomArrayAdapter extends BaseAdapter {

    Context c;
    ArrayList<Data> data;
    FireBaseReadData fireBaseReadData;
    DatabaseReference databaseReference;
    Preference preference = new Preference();

    private UpdateDataBase updateDataBase = new UpdateDataBase();


    public CustomArrayAdapter(Context c, ArrayList<Data> data) {
        this.c = c;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fireBaseReadData = new FireBaseReadData(databaseReference);

        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        }
        fireBaseReadData.readFromDatabase(position, c);
        TextView name = (TextView) convertView.findViewById(R.id.nameT);
        Button Btn_Reg = (Button) convertView.findViewById(R.id.btn_reg);
        Button Btn_Info = (Button) convertView.findViewById(R.id.Btn_info);
        final Data data = (Data) this.getItem(position);
        name.setText(data.getTournamentName());

        Btn_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogInfo(position);
            }
        });
        Btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        return convertView;
    }

    private void ShowDialogInfo(final int position) {
        fireBaseReadData.readFromDatabase(position, c);
        updateDataBase = new UpdateDataBase();
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.dialoginfo);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        final FloatingActionButton Btn_close, Btn_delete;
        Btn_close = dialog.findViewById(R.id.Btn_No);
        Btn_delete = dialog.findViewById(R.id.Btn_delete);


        TextView Plyers = dialog.findViewById(R.id.txt_playersName);
        TextView NumberOfPlayers = dialog.findViewById(R.id.txt_playersNumber);
        NumberOfPlayers.setText(preference.getPlayerAvailable() + " / " + preference.getPlayerMax());
        Plyers.setText(preference.getNamePlayers());
        Btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {fireBaseReadData.DeleteItem(position, c); dialog.dismiss();}
        });
        Btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private void ShowDialog() {


        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.reg_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        final FloatingActionButton BtnYes, BtnNo;
        BtnYes = dialog.findViewById(R.id.Btn_close);
        BtnNo = dialog.findViewById(R.id.Btn_No);
        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preference.isState()) {
                    Toast.makeText(c, "Sei già registrato", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(c, "Registrazione effettuata con successo", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }

        });

        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}


