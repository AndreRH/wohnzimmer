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

    private int wzwaran;

    private int zsun1available;

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

        eth1 = (Button) findViewById(R.id.eth1);
        eth1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_tcp(1, eth1an, 0);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_tcp(1, eth1an, 0);
                         }
                    }, 5555);
                }
            }
        });

        eth2 = (Button) findViewById(R.id.eth2);
        eth2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (unit2an > 0)
                    send_command_tcp(2, eth2an, 0);
                else
                {
                    Handler handler = new Handler();
                    send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=1");
                    handler.postDelayed(new Runnable() {
                         public void run() {
                            send_command_tcp(2, eth2an, 0);
                         }
                    }, 5555);
                }
            }
        });

        mylight14 = (Button) findViewById(R.id.mylight14); //water
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

        mylight8 = (Button) findViewById(R.id.mylight8); //tv
        mylight8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=8&t="+(1 - unit8an));
            }
        });

        mylight8d = (Button) findViewById(R.id.mylight8d);
        mylight8d.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=1");
                send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=1");
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=8&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=8&t=0");
                     }
                }, 5000);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=17&u=2&t=0");
                        wzwaran = 0;
                     }
                }, 7000);
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                     public void run() {
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=0");
                        send_command_http("http://odroid64:12000/main.cgi?s=35&u=15&t=0");
                     }
                }, 15000);
            }
        });

        mylight4 = (Button) findViewById(R.id.mylight4); //epl
        mylight4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://odroid64:12000/main.cgi?s=17&u=4&t="+(1 - unit4an));
            }
        });

        zsun1 = (Button) findViewById(R.id.zsun1);
        zsun1.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: send_command_udp(1, 1); break;
                case MotionEvent.ACTION_UP:   send_command_udp(1, 0); break;
                }
                return true;
            }
        });

        zsun1d = (Button) findViewById(R.id.zsun1d);
        zsun1d.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_udp(1, 2);
            }
        });

        udp = (Button) findViewById(R.id.udp);
        udp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), epl.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(myIntent, 0);
            }
        });

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
        send_command_http("http://redblue/main.cgi");
        send_command_http("http://odroid64:12000/main.cgi");
        send_command_tcp(0,0,1);
        send_command_udp(1, 3);
    }

    private void send_command_http(String cmd)
    {
        SendTask sndtsk = new SendTask(this);
        sndtsk.execute(cmd);
    }

    private void send_command_tcp(int nr, int an, int update)
    {
        if (blocktcp > 0) return; else blocktcp = 1;
        int cmd = 100 + nr + (an * 10);
        TCPTask tcptsk = new TCPTask();
        if (update > 0) cmd = 91;
        tcptsk.execute(cmd, 0);
    }

    private void send_command_udp(int nr, int cmd)
    {
        UdpTask udptsk = new UdpTask(this);
        udptsk.execute(nr, cmd);
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
            if (unit4an>0)
            {
                mylight4.setTextColor(android.graphics.Color.BLACK);
                mylight4.setBackgroundColor(android.graphics.Color.GRAY);
                udp.setTextColor(android.graphics.Color.BLACK);
                udp.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mylight4.setTextColor(android.graphics.Color.GRAY);
                mylight4.setBackgroundColor(android.graphics.Color.DKGRAY);
                udp.setTextColor(android.graphics.Color.GRAY);
                udp.setBackgroundColor(android.graphics.Color.DKGRAY);
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

    private class TCPTask extends AsyncTask<Integer, Void, Void> {

        private Exception exception;

        protected Void doInBackground(Integer... ints) {
            int cmd = ints[0];
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress("greenhead", 17494), 50);
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

    private class UdpTask extends AsyncTask<Integer, Void, Void> {

        private Exception exception;

        private Context context;

        public UdpTask(Context context) {
            this.context = context;
        }

        protected Void doInBackground(Integer... ints) {
            DatagramSocket s = null;
            int zsun = ints[0];
            int cmd  = ints[1];

            zsun1available = 0;
            if (zsun != 1) return null;

            try {
                InetAddress local;
                int server_port = 32201;

                s = new DatagramSocket();
                try {
                    local = InetAddress.getByName("zsun1");
                } catch (java.net.UnknownHostException e) {
                    s.close();
                    return null;
                }
                s.setSoTimeout(42);

                byte[] bytes = new byte[1];
                if (cmd == 1)
                    bytes[0] = 1;
                else if (cmd == 2)
                    bytes[0] = 2;
                else if (cmd == 3)
                    bytes[0] = 3;
                else
                    bytes[0] = 0;

                DatagramPacket p = new DatagramPacket(bytes, bytes.length, local, server_port);
                s.send(p);

                DatagramPacket r = new DatagramPacket(bytes, bytes.length);
                Thread.sleep(10);
                s.receive(r);
                zsun1available = 1;

                s.close();
                return null;
            } catch (InterruptedIOException e) {
                if (s != null)
                {
                    zsun1available = 0;
                    s.close();
                    s = null;
                }
                return null;
            } catch (Exception e) {
                errocc = 1;
                errstr = "IOException: " + e.getMessage();
                return null;
            }
        }

        protected void onPostExecute(Void v) {
            if (errocc>0)
                volt.setText(errstr);
            errocc = 0;

            if (zsun1available>0)
            {
                zsun1.setTextColor(android.graphics.Color.BLACK);
                zsun1.setBackgroundColor(android.graphics.Color.GRAY);
                zsun1d.setTextColor(android.graphics.Color.BLACK);
                zsun1d.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                zsun1.setTextColor(android.graphics.Color.GRAY);
                zsun1.setBackgroundColor(android.graphics.Color.DKGRAY);
                zsun1d.setTextColor(android.graphics.Color.GRAY);
                zsun1d.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
        }
    }
}
