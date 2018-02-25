package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;
import java.io.*;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
    private int unit14an;
    private int unit15an;
    private int unit8an;
    private int unit4an;
    private int unit2an;
    private int zsun1an;

    private int wzwaran;

    private int blocktcp;

    private Button blue1;
    private Button blue2;
    private Button red1;
    private Button red2;
    private Button udp;
    private Button eth1;
    private Button eth2;
    private Button mylight15;
    private Button mylight14;
    private Button mylight8;
    private Button mylight8d;
    private Button mylight4;
    private Button zsun1;
    private Button zsun1d;
    private Button jarbotv;

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
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=blueright&t="+(1-blau1an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=blueright&t="+(1-blau1an));
                         }
                    }, 9000);
                }
            }
        });

        blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=blueleft&t="+(1-blau2an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=blueleft&t="+(1-blau2an));
                         }
                    }, 9000);
                }
            }
        });

        red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=redleft&t="+(1-rot1an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=redleft&t="+(1-rot1an));
                         }
                    }, 9000);
                }
            }
        });

        red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=redright&t="+(1-rot2an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=redright&t="+(1-rot1an));
                         }
                    }, 9000);
                }
            }
        });

        eth1 = (Button) findViewById(R.id.eth1); //tv
        eth1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=tv&t="+(1-eth1an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=tv&t="+(1-eth1an));
                         }
                    }, 5555);
                }
            }
        });

        eth2 = (Button) findViewById(R.id.eth2); //pharao
        eth2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://odroid64:12000/main.cgi?n=pharao&t="+(1-eth2an));
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://odroid64:12000/main.cgi?n=pharao&t="+(1-eth1an));
                         }
                    }, 5555);
                }
            }
        });

        mylight14 = (Button) findViewById(R.id.mylight14); //mirror
        mylight14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?n=mirror&t="+(1 - unit14an));
            }
        });

        mylight15 = (Button) findViewById(R.id.mylight15); //bed
        mylight15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?n=bed&t="+(1 - unit15an));
            }
        });

        /*
        mylight8 = (Button) findViewById(R.id.mylight8);
        mylight8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=8&t="+(1 - unit8an));
            }
        });
        */

        mylight8d = (Button) findViewById(R.id.mylight8d);
        mylight8d.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?n=bed&t=1");
                mylight8d.setTextColor(android.graphics.Color.BLACK);
                mylight8d.setBackgroundColor(android.graphics.Color.GRAY);
                send_command_http("http://odroid64:12000/main.cgi?n=bed&t=1");
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?n=mainlight&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=0");
                        blau1an=0;
                        blau2an=0;
                        rot1an=0;
                        rot2an=0;
                        eth1an=0;
                        eth2an=0;
                        send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=0");
                        wzwaran = 0;
                     }
                }, 5000);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?n=mainlight&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=green&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=green&t=0");
                     }
                }, 7000);
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?n=mainlight&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=bed&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=mirror&t=0");
                        mylight8d.setTextColor(android.graphics.Color.GRAY);
                        mylight8d.setBackgroundColor(android.graphics.Color.DKGRAY);
                        send_command_http("http://odroid64:12000/main.cgi?n=bed&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?n=mirror&t=0");
                     }
                }, 15000);
            }
        });

        mylight4 = (Button) findViewById(R.id.mylight4); //green
        mylight4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?n=green&t="+(1 - unit4an));
            }
        });

        zsun1 = (Button) findViewById(R.id.zsun1); //decke
        zsun1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?n=mainlight&t="+(1 - zsun1an));
            }
        });

        /*zsun1d = (Button) findViewById(R.id.zsun1d);
        zsun1d.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_udp(1, 2);
            }
        });*/

        /*
        udp = (Button) findViewById(R.id.udp);
        udp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), epl.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(myIntent, 0);
            }
        });
        */

        jarbotv = (Button) findViewById(R.id.jarbot);
        jarbotv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), jarbot.class);
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
        send_command_http("http://odroid64:12000/main.cgi");
    }

    private void send_command_http(String cmd)
    {
        SendTask sndtsk = new SendTask(this);
        sndtsk.execute(cmd);
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
                    urlConnection.setConnectTimeout(50);
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
            if (unit14an>0)
            {
                mylight14.setTextColor(android.graphics.Color.BLACK);
                mylight14.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mylight14.setTextColor(android.graphics.Color.GRAY);
                mylight14.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (unit15an>0)
            {
                mylight15.setTextColor(android.graphics.Color.BLACK);
                mylight15.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mylight15.setTextColor(android.graphics.Color.GRAY);
                mylight15.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            /*
            if (unit8an>0)
            {
                mylight8.setTextColor(android.graphics.Color.BLACK);
                mylight8.setBackgroundColor(android.graphics.Color.GRAY);
                mylight8d.setTextColor(android.graphics.Color.BLACK);
                mylight8d.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mylight8.setTextColor(android.graphics.Color.GRAY);
                mylight8.setBackgroundColor(android.graphics.Color.DKGRAY);
                mylight8d.setTextColor(android.graphics.Color.GRAY);
                mylight8d.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            */
            if (unit4an>0)
            {
                mylight4.setTextColor(android.graphics.Color.BLACK);
                mylight4.setBackgroundColor(android.graphics.Color.rgb(44,255,122));
                /*
                mylight4.setTextColor(android.graphics.Color.BLACK);
                mylight4.setBackgroundColor(android.graphics.Color.GRAY);
                udp.setTextColor(android.graphics.Color.BLACK);
                udp.setBackgroundColor(android.graphics.Color.GRAY);
                */
            }
            else
            {
                mylight4.setTextColor(android.graphics.Color.rgb(44,255,122));
                mylight4.setBackgroundColor(android.graphics.Color.BLACK);
                /*
                mylight4.setTextColor(android.graphics.Color.GRAY);
                mylight4.setBackgroundColor(android.graphics.Color.DKGRAY);
                udp.setTextColor(android.graphics.Color.GRAY);
                udp.setBackgroundColor(android.graphics.Color.DKGRAY);
                */
            }
            if (zsun1an>0)
            {
                zsun1.setTextColor(android.graphics.Color.BLACK);
                zsun1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                zsun1.setTextColor(android.graphics.Color.GRAY);
                zsun1.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (eth1an>0)
            {
                eth1.setTextColor(android.graphics.Color.BLACK);
                eth1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                eth1.setTextColor(android.graphics.Color.GRAY);
                eth1.setBackgroundColor(android.graphics.Color.DKGRAY);
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
            if (wzwaran > 0 && blau1an == 0 && blau2an == 0 && rot1an == 0 && rot2an == 0 && eth1an == 0 && eth2an == 0)
            {
                send_command_http("http://odroid64:12000/main.cgi?n=wzmain&t=0");
                wzwaran = 0;
            }
            else if (blau1an > 0 || blau2an > 0 || rot1an > 0 || rot2an > 0 || eth1an > 0 || eth2an > 0)
            {
                wzwaran = 1;
                unit2an = 1;
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
                    if (line.contains("blueright")==true && line.contains("=")==true)
                    {
                        blau1an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("blueleft")==true && line.contains("=")==true)
                    {
                        blau2an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("redright")==true && line.contains("=")==true)
                    {
                        rot2an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("redleft")==true && line.contains("=")==true)
                    {
                        rot1an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("green")==true && line.contains("=")==true)
                    {
                        unit4an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("tv")==true && line.contains("=")==true)
                    {
                        eth1an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("pharao")==true && line.contains("=")==true)
                    {
                        eth2an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("mirror")==true && line.contains("=")==true)
                    {
                        unit14an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("wzmain")==true && line.contains("=")==true)
                    {
                        unit2an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("mainlight")==true && line.contains("=")==true)
                    {
                        zsun1an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("bed")==true && line.contains("=")==true)
                    {
                        unit15an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        continue;
                    }
                }
            } catch (IOException e) {
                errocc = 1;
                errstr = "readStream failed: " + e.getMessage();
            }
        }
    }
}
