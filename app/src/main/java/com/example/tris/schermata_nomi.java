package com.example.tris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class schermata_nomi extends AppCompatActivity {

    private EditText giocatore1;
    private EditText giocatore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_nomi);

        giocatore1 = findViewById(R.id.NomeGiocatore1);
        giocatore2 = findViewById(R.id.NomeGiocatore2);
    }

    public void confirmButtonClick(View view){
        String NomeGiocatore1 = giocatore1.getText().toString();
        String NomeGiocatore2 = giocatore2.getText().toString();

        Intent intent = new Intent(this, schermata_gioco.class);
        intent.putExtra("NOMI_GIOCATORI", new String[] {NomeGiocatore1, NomeGiocatore2});
        startActivity(intent);
    }
}