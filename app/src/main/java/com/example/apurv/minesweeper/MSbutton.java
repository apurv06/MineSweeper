package com.example.apurv.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;

public class MSbutton extends AppCompatButton {

    int value;
    int i;
    int j;
    public int flag=0;
    public MSbutton(Context context) {
        super(context);
        value=0;
        //setText(Integer.toString(value));

    }
    public void setCoordinate(int i,int j)
    {
        this.i=i;
        this.j=j;
    }
    public int getValue()
    {
        return value;
    }
    public int geti()
    {
        return i;
    }
    public int getj()
    {
        return j;
    }
    public void changevalue(int value)
    {
        this.value=value;
        //setText(Integer.toString(value));
    }

    public void changeflag() {
        if(flag==1) {
            flag = 0;
            setText("");
        }
        else {
            flag = 1;

            setText("F");

        }
    }
}
