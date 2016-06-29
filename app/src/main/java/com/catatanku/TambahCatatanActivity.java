package com.catatanku;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.catatanku.com.catatanku.helper.DBAdapter;
import com.catatanku.model.Catatan;

public class TambahCatatanActivity extends Activity {
    private EditText txtJudul, txtCatatan;
    private Button btnSimpan;
    private Spinner spWarna;

    private boolean isEdit = false;
    private int id = 0;
    private String[] warna = {"kuning", "merah", "biru", "hijau", "Abu-Abu"};
    private int warnaDipilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);
        isEdit = getIntent().getExtras().getBoolean("edit", false);
        setupView();

        if (isEdit) {
            String judul = getIntent().getExtras().getString("judul");
            String isi = getIntent().getExtras().getString("isi");
            id = getIntent().getExtras().getInt("id");
            warnaDipilih = getIntent().getExtras().getInt("warna");
            txtJudul.setText(judul);
            txtCatatan.setText(isi);
            spWarna.setSelection(warnaDipilih);
        }
    }

    private void setupView() {
        txtJudul = (EditText) findViewById(R.id.txtJudul);
        txtCatatan = (EditText) findViewById(R.id.txtCatatan);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
//event tombol submit diklik
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                simpan();
            }
        });
        spWarna = (Spinner) findViewById(R.id.spWarna);
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),
                android.R.layout.simple_spinner_item, warna);
        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
//masukkan adapter ke spinner
        spWarna.setAdapter(adapter);
//event saat spinner dipilih
        spWarna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int posisi, long arg3) {
                warnaDipilih = posisi;
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    protected void simpan() {
        if (txtJudul.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "Judul Harus Diisi",
                    Toast.LENGTH_LONG).show();
        } else if (txtCatatan.getText().toString()
                .equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(),
                    "Catatan Harus Diisi",
                    Toast.LENGTH_LONG).show();
        } else {
            DBAdapter db = new DBAdapter(getBaseContext());
            db.open();

            Catatan c = new Catatan();
            c.setTitle(txtJudul.getText().toString());
            c.setIsi(txtCatatan.getText().toString());
            c.setWarna(warnaDipilih);
            boolean hasil = false;
            if (isEdit) {
                hasil = db.updateData(c, id);
            } else {
                hasil = db.insertData(c);
            }
            if (hasil) {
                Toast.makeText(getBaseContext(),
                        "Catatan Berhasil Disimpan",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),
                        DaftarCatatanActivity.class));
                finish();
        } else {
            Toast.makeText(getBaseContext(),
                    "Ooopps, error simpan data. silahkan coba lagi.",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
}