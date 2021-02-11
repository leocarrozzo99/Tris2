package com.example.tris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

import java.sql.BatchUpdateException;

public class TavolaTris extends View {
    private final int coloreTavola;
    private final int ColoreX;
    private final int ColoreO;
    private final int ColoreLineaVincitore;

    private boolean lineaVincente = false;

    private final Paint paint = new Paint();

    private final Funzioni gioco;

    private int dimensioneCelle = getWidth()/3;


    public TavolaTris(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gioco = new Funzioni();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TavolaTris, 0, 0);

        try {
            coloreTavola = a.getInteger(R.styleable.TavolaTris_coloreTavola, 0);
            ColoreX = a.getInteger(R.styleable.TavolaTris_ColoreX, 0);
            ColoreO = a.getInteger(R.styleable.TavolaTris_ColoreO, 0);
            ColoreLineaVincitore = a.getInteger(R.styleable.TavolaTris_ColoreLineaVincitore, 0 );

        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int larghezza, int altezza){
        super.onMeasure(larghezza, altezza);

        int dimensione = Math.min(getMeasuredWidth(), getMeasuredHeight());
        dimensioneCelle = dimensione/3;

        setMeasuredDimension(dimensione, dimensione);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (lineaVincente){
            paint.setColor(ColoreLineaVincitore);
            drawLineaVincente(canvas);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        //Le variabili x e y diranno il punto, tramite coordinato, in cui l'utente ha cliccato sullo schermo

        int azione = event.getAction();


        if (azione == MotionEvent.ACTION_DOWN){
            int riga = (int) Math.ceil(y/dimensioneCelle);
            int colonna = (int) Math.ceil(x/dimensioneCelle);


            if(!lineaVincente){
                if (gioco.aggiornaTavolaGioco(riga, colonna)){
                    invalidate();

                    if (gioco.controlloVincitore()){
                        lineaVincente = true;
                        invalidate();
                    }

                    //aggiorna il turno dei due giocatori
                    if (gioco.getGiocatore() % 2 == 0){
                        gioco.selezionaGiocatore(gioco.getGiocatore()-1);
                    }
                    else{
                        gioco.selezionaGiocatore(gioco.getGiocatore()+1);
                    }
                }
            }


            invalidate();

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(coloreTavola);
        paint.setStrokeWidth(16);

        for (int i=1; i<3; i++){
            canvas.drawLine(dimensioneCelle*i, 0, dimensioneCelle*i, canvas.getWidth(), paint);
        }

        for (int j=1; j<3; j++){
            canvas.drawLine(0, dimensioneCelle*j, canvas.getWidth(), dimensioneCelle*j, paint);
        }
    }

    private void drawX(Canvas canvas, int riga, int colonna){
        paint.setColor(ColoreX);

        canvas.drawLine((float) ((colonna+1)*dimensioneCelle - dimensioneCelle*0.2),
                        (float) (riga*dimensioneCelle + dimensioneCelle*0.2),
                        (float) (colonna*dimensioneCelle + dimensioneCelle*0.2),
                        (float) ((riga+1)*dimensioneCelle - dimensioneCelle*0.2),
                        paint);

        canvas.drawLine((float) (colonna*dimensioneCelle + dimensioneCelle*0.2),
                        (float) (riga*dimensioneCelle + dimensioneCelle*0.2),
                        (float) ((colonna+1)*dimensioneCelle - dimensioneCelle*0.2),
                        (float) ((riga+1)*dimensioneCelle - dimensioneCelle*0.2),
                        paint);
    }


    private void drawMarkers(Canvas canvas){
        //riga
        for (int i=0; i<3; i++){
            //colonna
            for (int j=0; j<3; j++){
                if (gioco.getTavolaGioco()[i][j] != 0){
                    if (gioco.getTavolaGioco()[i][j] == 1){
                        drawX(canvas, i, j);
                    }
                    else{
                        drawO(canvas, i, j);
                    }
                }
            }
        }
    }



    private void drawO(Canvas canvas, int riga, int colonna){
        paint.setColor(ColoreO);

        canvas.drawOval((float) (colonna*dimensioneCelle + dimensioneCelle*0.2),
                        (float) (riga*dimensioneCelle + dimensioneCelle*0.2),
                        (float) ((colonna*dimensioneCelle + dimensioneCelle) - dimensioneCelle*0.2),
                        (float) ((riga*dimensioneCelle + dimensioneCelle) - dimensioneCelle*0.2),
                        paint);
    }

    private void drawHorizontalLine(Canvas canvas, int riga, int colonna){
        canvas.drawLine(colonna, riga*dimensioneCelle + (float) dimensioneCelle/2,
                        dimensioneCelle*3, riga* dimensioneCelle + (float) dimensioneCelle/2,
                        paint);
    }

    private void drawVerticalLine(Canvas canvas, int riga, int colonna){
        canvas.drawLine(colonna*dimensioneCelle + (float) dimensioneCelle/2, riga,
                        colonna*dimensioneCelle + (float) dimensioneCelle/2, dimensioneCelle*3,
                        paint);
    }

    public void drawPrincipalDiagLine(Canvas canvas){
        canvas.drawLine(0, dimensioneCelle*3, dimensioneCelle*3, 0,
                        paint);
    }

    public void drawSecondDiagLine(Canvas canvas){
        canvas.drawLine(0, 0, dimensioneCelle*3, dimensioneCelle*3,
                paint);
    }

    private void drawLineaVincente(Canvas canvas){
        int riga = gioco.getTipoVittoria()[0];
        int colonna = gioco.getTipoVittoria()[1];

        switch ( (gioco.getTipoVittoria()[2])){
            case 1: drawHorizontalLine(canvas, riga, colonna);
                    break;
            case 2: drawVerticalLine(canvas, riga, colonna);
                    break;
            case 3: drawSecondDiagLine(canvas);
                    break;
            case 4: drawPrincipalDiagLine(canvas);
                    break;
        }
    }

    public void setUp(Button rigioca, Button menu, TextView giocatore, String[] nomi){
        gioco.setBottoneRigioca(rigioca);
        gioco.setBottoneMenu(menu);
        gioco.setTurnoGiocatore(giocatore);
        gioco.setNomeGiocatore(nomi);
    }

    public void rigioca(){
        gioco.rigioca();
        lineaVincente = false;
    }
}
