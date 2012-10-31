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
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL("http://192.168.178.32/main.cgi?bx2="+blau1an);
                } catch (java.net.MalformedURLException e){
                    Toast toast = Toast.makeText(getApplicationContext(), "new URL failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed", Toast.LENGTH_SHORT);
                    toast.show();
                return;
                } finally {
                    if (blau1an>0) blau1an=0;
                    else blau1an=1;
                    urlConnection.disconnect();
                }
            }
        });
        
        Button blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL("http://192.168.178.32/main.cgi?bx0="+blau2an);
                } catch (java.net.MalformedURLException e){
                    Toast toast = Toast.makeText(getApplicationContext(), "new URL failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed", Toast.LENGTH_SHORT);
                    toast.show();
                return;
                } finally {
                    if (blau2an>0) blau2an=0;
                    else blau2an=1;
                    urlConnection.disconnect();
                }
            }
        });
        
        Button red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL("http://192.168.178.32/main.cgi?bx4="+rot1an);
                } catch (java.net.MalformedURLException e){
                    Toast toast = Toast.makeText(getApplicationContext(), "new URL failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed", Toast.LENGTH_SHORT);
                    toast.show();
                return;
                } finally {
                    if (rot1an>0) rot1an=0;
                    else rot1an=1;
                    urlConnection.disconnect();
                }
            }
        });
        
        Button red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL("http://192.168.178.32/main.cgi?bx6="+rot2an);
                } catch (java.net.MalformedURLException e){
                    Toast toast = Toast.makeText(getApplicationContext(), "new URL failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed", Toast.LENGTH_SHORT);
                    toast.show();
                return;
                } finally {
                    if (rot2an>0) rot2an=0;
                    else rot2an=1;
                    urlConnection.disconnect();
                }
            }
        });
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
