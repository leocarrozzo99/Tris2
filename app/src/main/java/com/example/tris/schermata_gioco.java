package com.example.tris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class schermata_gioco extends AppCompatActivity {

    private TavolaTris tavolaTris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_gioco);

        Button bottoneRigioca = findViewById(R.id.rigoca);
        Button bottoneMenu = findViewById(R.id.bottone_menu);
        TextView turnoGiocatore = findViewById(R.id.giocatore);
        TextView contatore_giocatore1 = findViewById(R.id.contatore_giocatore1);
        TextView contatore_giocatore2 = findViewById(R.id.contatore_giocatore2);


        //Rendo invisibili i bottoni quando si gioca
        bottoneRigioca.setVisibility(View.GONE);
        bottoneMenu.setVisibility(View.GONE);


        String[] nomiGiocatori = getIntent().getStringArrayExtra("NOMI_GIOCATORI");

        if (nomiGiocatori != null){
            turnoGiocatore.setText(("È il turno di: " + nomiGiocatori[0]));
            contatore_giocatore1.setText(nomiGiocatori[0]);
            contatore_giocatore2.setText(nomiGiocatori[1]);
        }



        tavolaTris = findViewById(R.id.tavolaTris);

        tavolaTris.setUp(bottoneRigioca, bottoneMenu, turnoGiocatore, nomiGiocatori);
    }

    public void playAgainButtonClick(View view){
        //Reset delle pedine
       tavolaTris.rigioca();
       tavolaTris.invalidate();
    }

    public void menuButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}