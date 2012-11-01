package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class lights extends Activity
{
    private int blau1an;
    private int blau2an;
    private int rot1an;
    private int rot2an;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button blue1 = (Button) findViewById(R.id.blue1);
        blue1.setOnClickListener(new OnClickListener() {
            public void onClick(View v)  {
                blau1an = send_command(2, blau1an);
            }
        });
        
        Button blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                blau2an = send_command(0, blau2an);
            }
        });
        
        Button red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                rot1an = send_command(4, rot1an);
            }
        });
        
        Button red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                rot2an = send_command(6, rot2an);
            }
        });
    }

    private int send_command(int bx, int an)
    {
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL("http://192.168.178.32/main.cgi?bx"+bx+"="+an);
        } catch (java.net.MalformedURLException e){
            Toast toast = Toast.makeText(getApplicationContext(), "new URL failed", Toast.LENGTH_SHORT);
            toast.show();
            return an;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed", Toast.LENGTH_SHORT);
            toast.show();
            return an;
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            /*readStream(in);*/
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed", Toast.LENGTH_SHORT);
            toast.show();
            urlConnection.disconnect();
            return an;
        }
        urlConnection.disconnect();
        if (an>0) return 0;
        else return 1;
    }
/*
private void readStream(InputStream is) {
    try {
      int i = is.read();
      while(i != -1) {
        i = is.read();
      }
      return;
    } catch (IOException e) {
Toast toast = Toast.makeText(getApplicationContext(), "wtf", Toast.LENGTH_SHORT);
toast.show();
      return;
    }
}*/
}
