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
                    send_command_http("http://redblue/main.cgi?bx2="+blau1an);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://redblue/main.cgi?bx2="+blau1an);
                         }
                    }, 9000);
                }
            }
        });

        blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://redblue/main.cgi?bx0="+blau2an);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://redblue/main.cgi?bx0="+blau2an);
                         }
                    }, 9000);
                }
            }
        });

        red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://redblue/main.cgi?bx4="+rot1an);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://redblue/main.cgi?bx4="+rot1an);
                         }
                    }, 9000);
                }
            }
        });

        red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_http("http://redblue/main.cgi?bx6="+rot2an);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_http("http://redblue/main.cgi?bx6="+rot2an);
                         }
                    }, 9000);
                }
            }
        });

        eth1 = (Button) findViewById(R.id.eth1); //tv
        eth1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_tcp("greenhead", 1, eth1an, 0);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_tcp("greenhead", 1, eth1an, 0);
                         }
                    }, 5555);
                }
            }
        });

        eth2 = (Button) findViewById(R.id.eth2); //pharao
        eth2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_tcp("greenhead", 2, eth2an, 0);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_tcp("greenhead", 2, eth2an, 0);
                         }
                    }, 5555);
                }
            }
        });

        mylight14 = (Button) findViewById(R.id.mylight14); //mirror
        mylight14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=14&t="+(1 - unit14an));
            }
        });

        mylight15 = (Button) findViewById(R.id.mylight15); //bed
        mylight15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t="+(1 - unit15an));
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
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=1");
                mylight8d.setTextColor(android.graphics.Color.BLACK);
                mylight8d.setBackgroundColor(android.graphics.Color.GRAY);
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=1");
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                     public void run() {
                        send_command_tcp("zsun1", 1, 1, 0);
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
                        blau1an=0;
                        blau2an=0;
                        rot1an=0;
                        rot2an=0;
                        eth1an=0;
                        eth2an=0;
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
                        wzwaran = 0;
                     }
                }, 5000);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                     public void run() {
                        send_command_tcp("zsun1", 1, 1, 0);
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=4&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=4&t=0");
                     }
                }, 7000);
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                     public void run() {
                        send_command_tcp("zsun1", 1, 1, 0);
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=14&t=0");
                        mylight8d.setTextColor(android.graphics.Color.GRAY);
                        mylight8d.setBackgroundColor(android.graphics.Color.DKGRAY);
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=14&t=0");
                     }
                }, 15000);
            }
        });

        mylight4 = (Button) findViewById(R.id.mylight4); //green
        mylight4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=4&t="+(1 - unit4an));
            }
        });

        zsun1 = (Button) findViewById(R.id.zsun1); //decke
        zsun1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_tcp("zsun1", 1, zsun1an, 0);
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
        send_command_tcp("zsun1", 0,0,1);
        send_command_http("http://redblue/main.cgi");
        send_command_tcp("zsun1", 0,0,1);
        send_command_http("http://odroid64:12000/main.cgi");
        send_command_tcp("greenhead", 0,0,1);
    }

    private void send_command_http(String cmd)
    {
        SendTask sndtsk = new SendTask(this);
        sndtsk.execute(cmd);
    }

    private void send_command_tcp(String adr, int nr, int an, int update)
    {
        if (blocktcp > 0) return; else blocktcp = 1;
        int cmd = 100 + nr + (an * 10);
        TCPTask tcptsk = new TCPTask();
        if (update > 0) cmd = 91;
        tcptsk.execute(adr, Integer.toString(cmd));
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
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
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
                    if(line.contains("unit")==true && line.contains("=")==true)
                    {
                        char an = line.charAt(line.indexOf("=") + 1);
                        switch(Integer.parseInt(line.substring(line.indexOf("unit") + 4, line.indexOf("unit") + 6)))
                        {
                            case 15:
                                if (an == '1') unit15an=1; else unit15an=0;
                                break;
                            case 14:
                                if (an == '1') unit14an=1; else unit14an=0;
                                break;
                            case 8:
                                if (an == '1') unit8an=1; else unit8an=0;
                                break;
                            case 4:
                                if (an == '1') unit4an=1; else unit4an=0;
                                break;
                            case 2:
                                if (an == '1')
                                {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                         public void run() {
                                            unit2an=1;
                                         }
                                    }, 4444);
                                }
                                else
                                    unit2an=0;
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

    private class TCPTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urlandcmd) {
            String adr = urlandcmd[0];
            int cmd = Integer.parseInt(urlandcmd[1]);
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress(adr, 17494), 500);
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

                if (adr.equals("zsun1"))
                {
                    if ((ret & 1) > 0) zsun1an = 1; else zsun1an = 0;
                    voltstr = "";
                }
                else
                {
                    if ((ret & 1) > 0) eth1an = 1; else eth1an = 0;
                    if ((ret & 2) > 0) eth2an = 1; else eth2an = 0;
                    tcpout.write(93);
                    Thread.sleep(10);
                    ret = tcpinp.read();
                    double dret = ret;
                    dret /= 10.0;
                    voltstr = "Spannung: " + dret + " V";
                }

                Thread.sleep(10);
                s.close();
            } catch (InterruptedException e) {
                errocc = 1;
                errstr = "InterruptedException: " + e.getMessage();
                //e.printStackTrace();
            /*} catch (UnknownHostException e) {
                e.printStackTrace();*/
            } catch (IOException e) {
                errocc = 1;
                errstr = "IOException: " + e.getMessage();
                //e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            blocktcp = 0;
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
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
                wzwaran = 0;
            }
            else if (blau1an > 0 || blau2an > 0 || rot1an > 0 || rot2an > 0 || eth1an > 0 || eth2an > 0)
            {
                wzwaran = 1;
                unit2an = 1;
            }
            if (errocc>0)
                volt.setText(errstr);
            else
                volt.setText(voltstr);
            errocc = 0;
        }
    }
}
