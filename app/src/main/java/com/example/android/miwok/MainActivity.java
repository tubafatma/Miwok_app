package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Finds the view that shows numbers category
        TextView numbers=(TextView)findViewById(R.id.numbers);
        //select click listener
        numbers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //intent to open numbers Activity
            Intent numI=new Intent(MainActivity.this,NumbersActivity.class);
            startActivity(numI);

            }
        });
        //Finds the view that shows family category
        TextView family=(TextView)findViewById(R.id.family);
        //select click listener
        family.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //intent to open numbers Activity
                Intent famI=new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(famI);
            }
        });
        //Finds the view that shows colors category
        TextView colors=(TextView)findViewById(R.id.colors);
        //select click listener
        colors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //intent to open numbers Activity
                Intent colI=new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(colI);
            }
        });
        //Finds the view that shows phrases category
        TextView phrases=(TextView)findViewById(R.id.phrases);
        //select click listener
        phrases.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //intent to open numbers Activity
                Intent phraseI=new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(phraseI);
            }
        });
    }
}