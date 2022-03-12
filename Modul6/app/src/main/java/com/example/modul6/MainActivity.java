package com.example.modul6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    DatabaseManager dm;
    EditText nama, hobi;
    Button addBtn;
    TableLayout tabel4data; // tabel for data

    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseManager(this);
        tabel4data = (TableLayout) findViewById(R.id.tabel_data);
        nama = (EditText) findViewById(R.id.inNama);
        hobi = (EditText) findViewById(R.id.inHobi);
        addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Auto-generate method stub
                simpanData();
            }
        });
        updateTable();
    }

    protected void simpanData(){
        //TODO Auto-generated method sub

        try{
            dm.TambahRow(nama.getText().toString(),hobi.getText().toString());
            Toast.makeText(getBaseContext(), nama.getText().toString()+", Berhasil Disimpan", Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        }

        catch (Exception e){
            //TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Gagal Simpan, " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void kosongkanField(){
        nama.setText("");
        hobi.setText("");
    }

    protected void updateTable(){
        //TODO Auto-generated method stub
        while (tabel4data.getChildCount()>1){
            tabel4data.removeViewAt(1);
        }
        double aa= tabel4data.getChildCount();
        String a = String.valueOf(aa);
        Toast.makeText(getBaseContext(), "Tabel Data Child : " + a, Toast.LENGTH_SHORT).show();
        ArrayList<ArrayList<Object>>data = dm.ambilSemuaBaris();//

        for (int posisi = 0; posisi< data.size(); posisi++){
            TableRow tableBaris = new TableRow(this);
            ArrayList<Object>baris = data.get(posisi);

            TextView idTxt = new TextView(this);
            idTxt.setText(baris.get(0).toString());
            tableBaris.addView(idTxt);

            TextView namaTxt = new TextView(this);
            namaTxt.setText(baris.get(1).toString());
            tableBaris.addView(namaTxt);

            TextView hobiTxt = new TextView(this);
            hobiTxt.setText(baris.get(2).toString());
            tableBaris.addView(hobiTxt);
            tabel4data.addView(tableBaris);
        }
    }
}