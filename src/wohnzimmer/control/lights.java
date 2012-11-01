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
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
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

        send_update();
    }

    private void send_update()
    {
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL("http://192.168.178.32/main.cgi");
        } catch (java.net.MalformedURLException e){
            Toast toast = Toast.makeText(getApplicationContext(), "new URL failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            urlConnection.disconnect();
            return;
        }
        urlConnection.disconnect();
    }

    private int send_command(int bx, int an)
    {
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL("http://192.168.178.32/main.cgi?bx"+bx+"="+an);
        } catch (java.net.MalformedURLException e){
            Toast toast = Toast.makeText(getApplicationContext(), "new URL failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return an;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "openConnection failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return an;
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "BufferedInputStream failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            urlConnection.disconnect();
            return an;
        }
        urlConnection.disconnect();
        if (an>0) return 0;
        else return 1;
    }

    private void readStream(InputStream is) {
        try {
            String line;
            BufferedReader r = new BufferedReader(new InputStreamReader(is),250);  
            while ((line = r.readLine()) != null) {
                if(line.contains("script")==true && line.contains("ln")==true)
                {
                    char an = line.charAt(line.indexOf("bx") - 3);
                    switch(line.charAt(line.indexOf("bx") + 2))
                    {
                        case '0':
                            if (an == '1') blau2an=1; else blau2an=0;
                            break;
                        case '2':
                            if (an == '1') blau1an=1; else blau1an=0;
                            break;
                        case '4':
                            if (an == '1') rot1an=1; else rot1an=0;
                            break;
                        case '6':
                            if (an == '1') rot2an=1; else rot2an=0;
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "readStream failed: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }

        Button blue1;
        blue1 = (Button) findViewById(R.id.blue1);
        while (blue1 == null) blue1 = (Button) findViewById(R.id.blue1);
        if (blau1an==1)
        {
            blue1.setTextColor(android.graphics.Color.BLACK);
            blue1.setBackgroundColor(android.graphics.Color.BLUE);
        }
        else
        {
            blue1.setTextColor(android.graphics.Color.BLUE);
            blue1.setBackgroundColor(android.graphics.Color.BLACK);
        }
        Button blue2 = (Button) findViewById(R.id.blue2);
        if (blau2an==1)
        {
            blue2.setTextColor(android.graphics.Color.BLACK);
            blue2.setBackgroundColor(android.graphics.Color.BLUE);
        }
        else
        {
            blue2.setTextColor(android.graphics.Color.BLUE);
            blue2.setBackgroundColor(android.graphics.Color.BLACK);
        }
        Button red1 = (Button) findViewById(R.id.red1);
        if (rot1an==1)
        {
            red1.setTextColor(android.graphics.Color.BLACK);
            red1.setBackgroundColor(android.graphics.Color.RED);
        }
        else
        {
            red1.setTextColor(android.graphics.Color.RED);
            red1.setBackgroundColor(android.graphics.Color.BLACK);
        }
        Button red2 = (Button) findViewById(R.id.red2);
        if (rot2an==1)
        {
            red2.setTextColor(android.graphics.Color.BLACK);
            red2.setBackgroundColor(android.graphics.Color.RED);
        }
        else
        {
            red2.setTextColor(android.graphics.Color.RED);
            red2.setBackgroundColor(android.graphics.Color.BLACK);
        }
    }
}
