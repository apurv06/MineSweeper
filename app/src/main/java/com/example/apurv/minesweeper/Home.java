package com.example.apurv.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class Home extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{



    public final double easy=0.25;
    public final double medium=0.4;
    public final double hard=0.6;
    public double difficulty=easy;

    EditText et;
    Spinner spinner;
    int grid=1;
    ArrayAdapter<String> adapter;
    String[] grid_array={"10x14"
        ,"12x16"
        ,"14x18"
        ,"16x20"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et=findViewById(R.id.editText);
spinner=findViewById(R.id.spinner);
        Button button;
        button=findViewById(R.id.button);
        button.setOnClickListener(this);
        button=findViewById(R.id.button2);
        button.setOnClickListener(this);
        button=findViewById(R.id.button3);
        button.setOnClickListener(this);
        button=findViewById(R.id.button4);
        button.setOnClickListener(this);

        spinner=findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,grid_array);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==R.id.button)
        {
            view.setBackground(getDrawable(R.drawable.background_afterclick));
            findViewById(R.id.button2).setBackground(getDrawable(R.drawable.background_beforeclick));
            findViewById(R.id.button3).setBackground(getDrawable(R.drawable.background_beforeclick));
            difficulty=easy;
        }
        else if (id==R.id.button2)
        {
            view.setBackground(getDrawable(R.drawable.background_afterclick));
            findViewById(R.id.button).setBackground(getDrawable(R.drawable.background_beforeclick));
            findViewById(R.id.button3).setBackground(getDrawable(R.drawable.background_beforeclick));
            difficulty=medium;
        }
        else if (id==R.id.button3)
        {
            view.setBackground(getDrawable(R.drawable.background_afterclick));
            findViewById(R.id.button2).setBackground(getDrawable(R.drawable.background_beforeclick));
            findViewById(R.id.button).setBackground(getDrawable(R.drawable.background_beforeclick));
            difficulty=hard;
        }
        else if (id==R.id.button4)
        {
            int pass_grid=grid;
            String name=et.getText().toString();
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("diff",difficulty);
            intent.putExtra("grid",pass_grid);
            startActivity(intent);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        grid=i+1;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        grid=1;

    }
}
