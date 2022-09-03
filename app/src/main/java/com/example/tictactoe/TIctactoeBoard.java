package com.example.tictactoe;

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



public class TIctactoeBoard extends View {
    private final int Boardcolorr;
    private final int Xcolor;
    private final int Ocolor;
    private final int Winninglinecolor;
    private final GameLogic game;
    private boolean winningline;



    private final Paint paint = new Paint();

    private  int cellsize = getWidth()/3;

    public TIctactoeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.TIctactoeBoard,0,0);
        try{
            Boardcolorr = a.getInteger(R.styleable.TIctactoeBoard_Boardcolor,0);
            Xcolor = a.getInteger(R.styleable.TIctactoeBoard_Xcolor,0);
            Ocolor = a.getInteger(R.styleable.TIctactoeBoard_Ocolor,0);
            Winninglinecolor = a.getInteger(R.styleable.TIctactoeBoard_Winninglinecolor,0);
        }finally {
            a.recycle();
        }

    }
    @Override
    protected void onMeasure(int width,int height) {
        super.onMeasure(width, height);
        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellsize = dimensions/3;
        setMeasuredDimension(dimensions, dimensions);

    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningline){
            paint.setColor(Winninglinecolor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellsize);
            int col = (int) Math.ceil(x/cellsize);
            if(!winningline){
                if(game.updateGameBoard(row,col)){
                    invalidate();
                    if(game.winnerCheck()){
                        winningline = true;
                        invalidate();
                    }
                    if(game.getPlayer()%2==0){
                        game.setPlayer(game.getPlayer()-1);
                    }
                    else{
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }
            invalidate();
            return true;
        }
        return false;
    }



    private void drawGameBoard(Canvas canvas){
        paint.setColor(Boardcolorr);
        paint.setStrokeWidth(16);
        for (int c=1; c<3; c++){
            canvas.drawLine(cellsize*c,0,cellsize*c,canvas.getWidth(),paint);
        }
        for (int d=1; d<3; d++){
            canvas.drawLine(0,d*cellsize,canvas.getWidth(),cellsize*d,paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if(game.getGameBoard()[r][c]!=0){
                    if(game.getGameBoard()[r][c]==1){
                        drawX(canvas,r,c);
                    }
                    else{
                        drawO(canvas,r,c);
                    }
                }
            }
        }
    }



    public void drawX(Canvas canvas, int row, int col){
        paint.setColor(Xcolor);
        canvas.drawLine((float)((col+1)*cellsize - cellsize*0.2),
                (float) (row*cellsize + cellsize*0.2),
                (float) (col*cellsize + cellsize*0.2),
                (float) ((row+1)*cellsize - cellsize*0.2),
                paint);
        canvas.drawLine((float) ((col)*cellsize + cellsize*0.2),
                (float) (row*cellsize + cellsize*0.2),
                (float) ((col+1)*cellsize - cellsize*0.2),
                (float) ((row+1)*cellsize - cellsize*0.2),
                paint);
    }

    public void drawO(Canvas canvas, int row, int col){
        paint.setColor(Ocolor);
        canvas.drawOval((float) (col*cellsize + cellsize*0.2),
                (float) (row*cellsize + cellsize*0.2),
                (float) ((col*cellsize+cellsize) - cellsize*0.2),
                (float) ((row*cellsize+cellsize)-cellsize*0.2),
                paint);
    }


    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col,row*cellsize + (float)cellsize/2,
                cellsize*3, row*cellsize + (float) cellsize/2,paint);
    }
    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellsize +(float) cellsize/2,row,
                col*cellsize+(float)cellsize/2, cellsize*3,paint);
    }
    private void drawDigonalLinepos(Canvas canvas){
        canvas.drawLine(0,cellsize*3,
                cellsize*3,0,paint);
    }
    private void drawDigonalLineneg(Canvas canvas){
        canvas.drawLine(0,0,
                cellsize*3,cellsize*3,paint);
    }
    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] name){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayTurn(playerDisplay);
        game.setPlayerNames(name);
    }
    private void drawWinningLine(Canvas canvas){
        int row = game.getWintype()[0];
        int col = game.getWintype()[1];
        switch (game.getWintype()[2]){
            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDigonalLineneg(canvas);
                break;
            case 4:
                drawDigonalLinepos(canvas);
                break;

        }
    }

    public void resetGamee(){
        game.resetGameBoard();
        winningline = false;
    }
}
