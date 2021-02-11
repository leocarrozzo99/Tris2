package com.example.tris;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

class Funzioni {
    private int[][] tavolaGioco;

    //Se nessuno dovesse inserire i nomi uscrianno: Giocatore 1 e Giocatore 2
    private String[] nomeGiocatore = {"Giocatore 1", "Giocatore 2"};


    //Il primo elemento andrà a immagazzinare le righe, il secondo elemento andrà a immagazzinare le colonne,
    //il terzo elemento andrà a immagazzinare il tipo di linea
    private int[] tipoVittoria = {-1, -1, -1};

    private Button bottoneRigioca;
    private Button bottoneMenu;
    private TextView turnoGiocatore;

    private int giocatore = 1;

    Funzioni(){
        tavolaGioco = new int[3][3];
        //riga
        for (int i=0; i<3; i++){
            //colonna
            for (int j=0; j<3; j++){
                tavolaGioco[i][j] = 0;
            }
        }
    }


    public boolean aggiornaTavolaGioco(int riga, int colonna){
        if (tavolaGioco[riga-1][colonna-1] == 0){
            tavolaGioco[riga-1][colonna-1] = giocatore;

            if(giocatore == 1){
                turnoGiocatore.setText(("È il turno di: " + nomeGiocatore[1]));
            }
            else{
                turnoGiocatore.setText(("È il turno di: " + nomeGiocatore[0]));
            }

            return true;
        }
        else{
            return false;
        }
    }

    public boolean controlloVincitore(){
        boolean isWinner = false;

        //For loop per vincitore orizzontale (tipoVittoria == 1)
        for (int i=0; i<3; i++){
            if (tavolaGioco[i][0] == tavolaGioco[i][1] && tavolaGioco[i][0] == tavolaGioco[i][2] && tavolaGioco[i][0] != 0){

                tipoVittoria = new int[] {i, 0, 1};
                isWinner = true;
            }
        }

        //For loop per vincitore verticale (tipoVittoria == 2)
        for (int j=0; j<3; j++){
            if (tavolaGioco[0][j] == tavolaGioco[1][j] && tavolaGioco[0][j] == tavolaGioco[2][j] && tavolaGioco[0][j] != 0){

                tipoVittoria = new int[] {0, j, 2};
                isWinner = true;
            }
        }


        //For loop per vincitore diagonale secondaria (tipoVittoria == 3)
        for (int k=0; k<3; k++){
            if (tavolaGioco[0][0] == tavolaGioco[1][1] && tavolaGioco[0][0] == tavolaGioco[2][2] && tavolaGioco[0][0] != 0){

                tipoVittoria = new int[] {0, 2, 3};
                isWinner = true;
            }
        }

        //For loop per vincitore diagonale principale (tipoVittoria == 4)
        for (int m=0; m<3; m++){
            if (tavolaGioco[2][0] == tavolaGioco[1][1] && tavolaGioco[2][0] == tavolaGioco[0][2] && tavolaGioco[2][0] != 0){

                tipoVittoria = new int[] {2, 2, 4};
                isWinner = true;
            }
        }



        int tavolaRiempita = 0;

        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if (tavolaGioco[i][j] != 0){
                    tavolaRiempita++;
                }
            }
        }

        if (isWinner){
            //Se c'è un vincitore i bottoni sono resi nuovamente visibili
            bottoneRigioca.setVisibility(View.VISIBLE);
            bottoneMenu.setVisibility(View.VISIBLE);

            turnoGiocatore.setText(("Il vincitore è: " + nomeGiocatore[giocatore-1]));


            return true;
        }
        else if(tavolaRiempita == 9){
            bottoneRigioca.setVisibility(View.VISIBLE);
            bottoneMenu.setVisibility(View.VISIBLE);

            turnoGiocatore.setText(("Nessun Vincitore"));

            return true;
        }
        else{
            return false;
        }
    }

    //Codice per reset partita
    public void rigioca(){
        for (int i=0; i<3; i++){
            //colonna
            for (int j=0; j<3; j++){
                tavolaGioco[i][j] = 0;
            }
        }

        giocatore = 1;

        bottoneRigioca.setVisibility(View.GONE);
        bottoneMenu.setVisibility(View.GONE);

        turnoGiocatore.setText(("È il turno di: " + nomeGiocatore[0]));
    }

    public void setBottoneRigioca(Button bottoneRigioca){
        this.bottoneRigioca = bottoneRigioca;
    }

    public void setBottoneMenu(Button bottoneMenu) {
        this.bottoneMenu = bottoneMenu;
    }

    public void setTurnoGiocatore(TextView turnoGiocatore) {
        this.turnoGiocatore = turnoGiocatore;
    }

    public void setNomeGiocatore(String[] nomeGiocatore) {
        this.nomeGiocatore = nomeGiocatore;
    }

    public int[][] getTavolaGioco() {
        return tavolaGioco;
    }

    public void selezionaGiocatore(int giocatore){
        this.giocatore = giocatore;
    }

    public int getGiocatore(){
        return giocatore;
    }

    public int[] getTipoVittoria(){
        return tipoVittoria;
    }

}
