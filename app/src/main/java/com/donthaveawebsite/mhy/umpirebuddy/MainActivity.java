package com.donthaveawebsite.mhy.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Button mBallButton;
    private Button mStrikeButton;
    private int BallCount = 0;
    private int StrikeCount = 0;
    private TextView BallText;
    private TextView StrikeText;
    private TextView OutText;


    public static final String PREFS_NAME = "MyOuts";
    private SharedPreferences appData;
    private int outcount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appData = getSharedPreferences(PREFS_NAME, 0);
        outcount = appData.getInt("Ocount", 0);
        BallCount = appData.getInt("Bcount", 0);
        StrikeCount = appData.getInt("Scount", 0);

        BallText = (TextView) findViewById(R.id.textView2);
        BallText.setText("Balls : " + BallCount);

        StrikeText = (TextView) findViewById(R.id.textView3);
        StrikeText.setText("Strikes : " + StrikeCount);

        OutText = (TextView) findViewById(R.id.OUTDisplay);
        OutText.setText("Outs : " + outcount);

        final AlertDialog.Builder msgboxbuilder = new AlertDialog.Builder(this);
        msgboxbuilder.setMessage("THREE STRIKES YOU ARE OUT!!");
        final AlertDialog.Builder msgboxbuilder2 = new AlertDialog.Builder(this);
        msgboxbuilder2.setMessage("Four balls is a walk!!");




        mBallButton = (Button)findViewById(R.id.Ball_button);
        mBallButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            if (appData.getInt("Bcount", 0) == 3)
            {

                msgboxbuilder2.show();
                SharedPreferences.Editor editor = appData.edit();
                editor.putInt("Bcount", 0);
                editor.commit();
                UpdateCount();

            }
               else
            {
                SharedPreferences.Editor editor = appData.edit();
                editor.putInt("Bcount", BallCount + 1);
                editor.commit();
                UpdateCount();
            }

            }
        });

        mStrikeButton = (Button)findViewById(R.id.Strike_button);
        mStrikeButton.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {
                    if (appData.getInt("Scount", 0) == 2)
                    {

                        msgboxbuilder.show();
                        OUTSUPDATER();
                        SharedPreferences.Editor editor = appData.edit();
                        editor.putInt("Scount", 0);
                        editor.commit();
                        UpdateCount();
                    }
                    else
                    {
                        SharedPreferences.Editor editor = appData.edit();
                        editor.putInt("Scount", StrikeCount + 1);
                        editor.commit();
                        UpdateCount();
                    }
                }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean UpdateCount()
    {
        outcount = appData.getInt("Ocount", 0);
        BallCount = appData.getInt("Bcount", 0);
        StrikeCount = appData.getInt("Scount", 0);

        BallText.setText("Balls : " + BallCount);
        StrikeText.setText("Strikes : " + StrikeCount);
        OutText.setText("Outs : " + outcount);
        return true;
    }

    public void OUTSUPDATER()
    {
        SharedPreferences.Editor editor = appData.edit();
        editor.putInt("Ocount", outcount + 1);
        editor.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.about_settings) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }
        if(id == R.id.reset_button)
        {

            SharedPreferences.Editor editor = appData.edit();
            editor.putInt("Ocount", 0);
            editor.putInt("Scount", 0);
            editor.putInt("Bcount", 0);
            editor.commit();
            UpdateCount();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
