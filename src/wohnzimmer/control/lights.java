package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.HttpURLConnection;

public class lights extends Activity
{
    private int blau1an;
    private int blau2an;
    private int rot1an;
    private int rot2an;
    private int eth1an;
    private int eth2an;

    private int blocktcp;
    
    private Button blue1;
    private Button blue2;
    private Button red1;
    private Button red2;
    private Button eth1;
    private Button eth2;

    private TextView volt;
    private String voltstr;

    private int errocc;
    private String errstr;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        blue1 = (Button) findViewById(R.id.blue1);
        blue1.setOnClickListener(new OnClickListener() {
            public void onClick(View v)  {
                send_command(2, blau1an, 0);
            }
        });

        blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command(0, blau2an, 0);
            }
        });

        red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command(4, rot1an, 0);
            }
        });

        red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command(6, rot2an, 0);
            }
        });

        eth1 = (Button) findViewById(R.id.eth1);
        eth1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_tcp(1, eth1an, 0);
            }
        });

        eth2 = (Button) findViewById(R.id.eth2);
        eth2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_tcp(2, eth2an, 0);
            }
        });

        Button udp = (Button) findViewById(R.id.udp);
        udp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), epl.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(myIntent, 0);
            }
        });

        volt = (TextView) findViewById(R.id.volt);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        send_command(0,0,1);
        send_command_tcp(0,0,1);
    }

    private void send_command(int bx, int an, int update)
    {
        SendTask sndtsk = new SendTask(this);
        if (update>0)
            sndtsk.execute("http://redblue/main.cgi");
        else
            sndtsk.execute("http://redblue/main.cgi?bx"+bx+"="+an);
    }

    private void send_command_tcp(int nr, int an, int update)
    {
        if (blocktcp > 0) return; else blocktcp = 1;
        int cmd = 100 + nr + (an * 10);
        TCPTask tcptsk = new TCPTask();
        if (update > 0) cmd = 91;
        tcptsk.execute(cmd, 0);
    }

    private class SendTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        private HttpURLConnection urlConnection;
        private Context context;

        public SendTask(Context context) {
            this.context = context;
        }

        protected Void doInBackground(String... urls) {
            try {
                URL url= new URL(urls[0]);
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    errocc = 1;
                    errstr = "openConnection failed: " + e.getMessage();
                    return null;
                }
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                    urlConnection.disconnect();
                    return null;
                } catch (IOException e) {
                    errocc = 1;
                    errstr = "BufferedInputStream failed: " + e.getMessage();
                    urlConnection.disconnect();
                    return null;
                }
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(Void v) {
            if (this.exception!=null) {
                errocc = 1;
                errstr = "this: " + this.exception.getMessage();
                this.exception = null;
            }

            if (blau1an>0)
            {
                blue1.setTextColor(android.graphics.Color.BLACK);
                blue1.setBackgroundColor(android.graphics.Color.rgb(60,111,240));
            }
            else
            {
                blue1.setTextColor(android.graphics.Color.rgb(60,111,240));
                blue1.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (blau2an>0)
            {
                blue2.setTextColor(android.graphics.Color.BLACK);
                blue2.setBackgroundColor(android.graphics.Color.rgb(60,111,240));
            }
            else
            {
                blue2.setTextColor(android.graphics.Color.rgb(60,111,240));
                blue2.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (rot1an>0)
            {
                red1.setTextColor(android.graphics.Color.BLACK);
                red1.setBackgroundColor(android.graphics.Color.rgb(255,50,50));
            }
            else
            {
                red1.setTextColor(android.graphics.Color.rgb(255,50,50));
                red1.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (rot2an>0)
            {
                red2.setTextColor(android.graphics.Color.BLACK);
                red2.setBackgroundColor(android.graphics.Color.rgb(255,50,50));
            }
            else
            {
                red2.setTextColor(android.graphics.Color.rgb(255,50,50));
                red2.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (errocc>0)
                volt.setText(errstr);
            errocc = 0;
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
                errocc = 1;
                errstr = "readStream failed: " + e.getMessage();
            }
        }
    }

    private class TCPTask extends AsyncTask<Integer, Void, Void> {

        private Exception exception;

        protected Void doInBackground(Integer... ints) {
            int cmd = ints[0];
            try {
                Socket s = new Socket("greenhead", 17494);
                OutputStream tcpout = s.getOutputStream();
                InputStream  tcpinp = s.getInputStream();
                if (cmd != 91)
                {
                    tcpout.write(cmd);
                    Thread.sleep(10);
                }

                tcpout.write(91);
                Thread.sleep(10);
                int ret = tcpinp.read();
                if ((ret & 1) > 0) eth1an = 1; else eth1an = 0;
                if ((ret & 2) > 0) eth2an = 1; else eth2an = 0;

                tcpout.write(93);
                Thread.sleep(10);
                ret = tcpinp.read();
                double dret = ret;
                dret /= 10.0;
                voltstr = "Spannung: " + dret + " V";

                Thread.sleep(10);
                s.close();
            } catch (InterruptedException e) {
                errocc = 1;
                errstr = "InterruptedException: " + e.getMessage();
                e.printStackTrace();
            /*} catch (UnknownHostException e) {
                e.printStackTrace();*/
            } catch (IOException e) {
                errocc = 1;
                errstr = "IOException: " + e.getMessage();
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            blocktcp = 0;
            if (eth1an>0)
            {
                eth1.setTextColor(android.graphics.Color.BLACK);
                eth1.setBackgroundColor(android.graphics.Color.rgb(44,255,122));
            }
            else
            {
                eth1.setTextColor(android.graphics.Color.rgb(44,255,122));
                eth1.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (eth2an>0)
            {
                eth2.setTextColor(android.graphics.Color.BLACK);
                eth2.setBackgroundColor(android.graphics.Color.rgb(241,67,20));
            }
            else
            {
                eth2.setTextColor(android.graphics.Color.rgb(241,67,20));
                eth2.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (errocc>0)
                volt.setText(errstr);
            else
                volt.setText(voltstr);
            errocc = 0;
        }
    }
}
