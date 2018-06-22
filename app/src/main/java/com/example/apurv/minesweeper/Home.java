package com.example.apurv.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//Comment Test for remote access
public class Home extends AppCompatActivity implements View.OnClickListener{


    public final double easy=0.25;
    public final double medium=0.4;
    public final double hard=0.6;
    public double difficulty=easy;

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et=findViewById(R.id.editText);

        Button button;

        button=findViewById(R.id.button);
        button.setOnClickListener(this);
        button=findViewById(R.id.button2);
        button.setOnClickListener(this);
        button=findViewById(R.id.button3);
        button.setOnClickListener(this);
        button=findViewById(R.id.button4);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        if(id==R.id.button)
        {
            difficulty=easy;
        }
        else if (id==R.id.button2)
        {
            difficulty=medium;
        }
        else if (id==R.id.button3)
        {
            difficulty=hard;
        }
        else if (id==R.id.button4)
        {
            String name=et.getText().toString();

            Intent intent=new Intent(this,MainActivity.class);

            intent.putExtra("name",name);
            intent.putExtra("diff",difficulty);

            startActivity(intent);



        }
    }
}
