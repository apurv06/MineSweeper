package com.example.apurv.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    LinearLayout rootLayout;
    public ArrayList<LinearLayout> rows;

    public final int Incomplete=1;
    public final int Lost=2;
    public final int Won=3;
    public int status;

    public int firstclick=0;
    int firstx;
    int firsty;
    public int[][] matrix;
    public MSbutton[][] board;


    public int length=10;
    public int height=14;


    public final double Hard=0.6;
public final double Medium =0.4;
public final double Easy=0.25;
public double difficulty=Easy;

String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
Intent intent=getIntent();
        name=intent.getStringExtra("name");
        difficulty=intent.getDoubleExtra("diff",0.25);
        rootLayout=findViewById(R.id.root);
        setupBoard();
    }

    public void setupBoard() {

        rows = new ArrayList<>();
        status=1;
        board = new MSbutton[height][length];
        rootLayout.removeAllViews();

        for (int i = 0; i < height; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);

            rootLayout.addView(linearLayout);
            rows.add(linearLayout);
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                MSbutton button = new MSbutton(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                button.setCoordinate(i,j);
                button.setBackground(getDrawable(R.drawable.button_start));
                LinearLayout row = rows.get(i);
                row.addView(button);
                board[i][j] = button;
            }
        }



    }

    private void setupmines() {

        Random rand = new Random();
        int maxlength=length-1;
        int maxheight=height-1;
        int min=0;
        int total=length*height;
        double totalmax=(double)total*difficulty;
        int totalmin=(int)totalmax-5;
        int number=rand.nextInt(((int)totalmax-totalmin)+1)+totalmin;
        for(int k=0;k<number;k++) {
            int j = rand.nextInt((maxlength - min) + 1) + min;
            int i = rand.nextInt((maxheight - min) + 1) + min;
            if(canbemapped(i,j))
            {board[i][j].changevalue(-1);
            }
        }
        for (int i=0;i<height;i++)
        {
                for (int j=0;j<length;j++) {
                 if(board[i][j].getValue()==(-1))
                    {
                                increase(i,j);
                }
                }
        }

    }

    private boolean canbemapped(int i, int j) {
        for (int x=-1;x<2;x++)
        {
            for (int y=-1;y<2;y++)
            {
                if (firstx+x>=0&&firstx+x<height&&firsty+y>=0&&firsty<length)
                {
                    if(i==(firstx+x)&&j==(firsty+y))
                        return false;

                }
            }
        }
        return true;
    }

    private void increase(int i, int j) {

        for (int x=(-1);x<2;x++)
        {
            for (int y=(-1);y<2;y++)
            {
                if((i+x)>=0&&(i+x)<height&&(j+y)>=0&&(j+y)<length)
                {
                    if(board[i+x][j+y].getValue()!=(-1)) {
                        int value = board[i + x][j + y].getValue();
                        board[i + x][j + y].changevalue(value + 1);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {


        int id=view.getId();
        MSbutton button=(MSbutton) view;
        if (button.flag==1)
        {

            Toast.makeText(this, "Button Flagged", Toast.LENGTH_SHORT).show();
            return;
        }
        if(firstclick==0)
        {
            firstclick=1;
            firstx=button.geti();
            firsty=button.getj();
                setupmines();
            reveal(button.geti(),button.getj());
                return;
        }
        if(status==Incomplete) {
            if (button.getValue() == (-1)) {
                status = Lost;

                Toast.makeText(this, "MINE TOUCHED", Toast.LENGTH_SHORT).show();
                showallmines();
            }
            else if(button.getValue()==0)
            {
                reveal(button.geti(),button.getj());
                checkstatus();
            }
            else if(button.getValue()>0)
            {
                    button.setEnabled(false);
                button.setText(Integer.toString(button.getValue()));
                button.setBackground(getDrawable(R.drawable.click));

                checkstatus();
            }
        }
        else
        {
            if(status==Won)
            Toast.makeText(this, name +"You"+"won", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(this, "Sorry "+name+" You Lost", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showallmines() {
        for (int x=0;x<height;x++)
        {
            for (int y=0;y<length;y++)
            {
                if(board[x][y].getValue()==(-1))
                {
                    board[x][y].setBackground(getDrawable(R.drawable.minephoto1));
                }
                else
                    continue;
            }
        }
    }

    private void checkstatus() {

        for (int x=0;x<height;x++)
        {
            for (int y=0;y<length;y++)
            {
                if(board[x][y].getValue()!=(-1))
                {
                    if (board[x][y].isEnabled()) {
                        status = Incomplete;
                        return;
                    }
                }
                else
                    continue;
            }
        }
        Toast.makeText(this, "Won", Toast.LENGTH_SHORT).show();
        status=Won;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(final MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.reset)
        {
            firstclick=0;
            setupBoard();
        }
        else if(id==R.id.easy)
        {
            firstclick=0;
            difficulty=Easy;
            setupBoard();
        }
        else if(id==R.id.medium)
        {
            firstclick=0;
            difficulty=Medium;
            setupBoard();
        }
        else if(id==R.id.hard)
        {
            firstclick=0;
            difficulty=Hard;
            setupBoard();
        }

        return true;
    }
    void revealnextcell(int x, int y)
    {
        matrix[x][y]=1;
            if(board[x][y].flag==1)
            {
                return;
            }

        if (x < 0 || x >=height || y < 0 || y >= length)
            return;
        if (board[x][y].getValue() == (-1))
            return;
        if(board[x][y].getValue()>0)
        {
            board[x][y].setEnabled(false);
            board[x][y].setBackground(getDrawable(R.drawable.click));
            board[x][y].setText(Integer.toString(board[x][y].getValue()));
            return;
        }


        board[x][y].setBackground(getDrawable(R.drawable.click));
        board[x][y].setText("");
        board[x][y].setEnabled(false);

        if(x+1>=0&&x+1<height&&y>=0&&y<length&&matrix[x+1][y]!=1)
        revealnextcell( x+1, y);

        if(x-1>=0&&x-1<height&&y>=0&&y<length&&matrix[x-1][y]!=1)
        revealnextcell( x-1, y);

        if(x>=0&&x<height&&y+1>=0&&y+1<length&&matrix[x][y+1]!=1)
        revealnextcell( x, y+1);

        if(x>=0&&x<height&&y-1>=0&&y-1<length&&matrix[x][y-1]!=1)
        revealnextcell( x, y-1);

        if(x-1>=0&&x-1<height&&y-1>=0&&y-1<length&&matrix[x-1][y-1]!=1)
            revealnextcell( x-1, y-1);
        if(x-1>=0&&x-1<height&&y+1>=0&&y+1<length&&matrix[x-1][y+1]!=1)
            revealnextcell( x-1, y+1);
        if(x+1>=0&&x+1<height&&y+1>=0&&y+1<length&&matrix[x+1][y+1]!=1)
            revealnextcell( x+1, y+1);
        if(x+1>=0&&x+1<height&&y-1>=0&&y-1<length&&matrix[x+1][y-1]!=1)
            revealnextcell( x+1, y-1);
    }


    void reveal( int x, int y)
    {
        matrix=new int[height][length];

                revealnextcell(x, y);
    }

    @Override
    public boolean onLongClick(View view) {
        MSbutton button=(MSbutton) view;

        button.changeflag();
        return true;
    }
}
