package com.example.ctorres.superagentemovil3.superagente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ctorres.superagentemovil3.R;

public class RecargaTelefonica extends AppCompatActivity {

    EditText txt_nomRecarga,txt_montoRecarga;
    Spinner sp_operadorRecarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_telefonica);
    }
}
