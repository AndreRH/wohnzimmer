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

import android.util.Log;

public class lights extends Activity
{
    private int blue1on;
    private int blue2on;
    private int red1on;
    private int red2on;
    private int tv1on;
    private int pharaoon;
    private int aux1on;
    private int mirror1on;
    private int bed1on;
    /*private int unit8an;*/
    private int green1on;
    private int mainlight1an;

    private Button blue1;
    private Button blue2;
    private Button red1;
    private Button red2;
    private Button udp;
    private Button tv1;
    private Button pharao;
    private Button bed1;
    private Button mirror1;
    /*private Button mylight8;*/
    private Button aux1;
    private Button green1;
    private Button mainlight1;
    private Button mainlight1d;
    private Button jarbotv;

    private TextView volt;
    private String voltstr;

    private int errocc;
    private String errstr;
    private UDPListener listener;
    private Thread listenerthread;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        blue1 = (Button) findViewById(R.id.blue1);
        blue1.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=blueright&t="+(1-blue1on));
            }
        });

        blue2 = (Button) findViewById(R.id.blue2);
        blue2.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=blueleft&t="+(1-blue2on));
            }
        });

        red1 = (Button) findViewById(R.id.red1);
        red1.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=redleft&t="+(1-red1on));
            }
        });

        red2 = (Button) findViewById(R.id.red2);
        red2.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=redright&t="+(1-red2on));
            }
        });

        tv1 = (Button) findViewById(R.id.tv1); //tv
        tv1.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=tv&t="+(1-tv1on));
            }
        });

        pharao = (Button) findViewById(R.id.pharao); //pharao
        pharao.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                send_command_http("http://rk3399:12000/main.cgi?n=pharao&t="+(1-pharaoon));
            }
        });

        mirror1 = (Button) findViewById(R.id.mirror1); //mirror
        mirror1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?n=mirror&t="+(1 - mirror1on));
            }
        });

        bed1 = (Button) findViewById(R.id.bed1); //bed
        bed1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?n=bed&t="+(1 - bed1on));
            }
        });

        /*
        mylight8 = (Button) findViewById(R.id.mylight8);
        mylight8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?s=17&u=8&t="+(1 - unit8an));
            }
        });
        */

        aux1 = (Button) findViewById(R.id.aux1); //aux1
        aux1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?n=aux&t="+(1 - aux1on));
            }
        });

        green1 = (Button) findViewById(R.id.green1); //green
        green1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?n=green&t="+(1 - green1on));
            }
        });

        mainlight1 = (Button) findViewById(R.id.mainlight1); //decke
        mainlight1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_command_http("http://rk3399:12000/main.cgi?n=mainlight&t="+(1 - mainlight1an));
            }
        });

        /*mainlight1d = (Button) findViewById(R.id.mainlight1d);
        mainlight1d.setOnClickListener(new OnClickListener() {
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

        listener = new UDPListener();
        listenerthread = new Thread(listener);
        listenerthread.start();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        send_command_http("http://rk3399:12000/main.cgi");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        listenerthread.interrupt();
        listenerthread = null;
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
                    urlConnection.setConnectTimeout(2000);
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

            if (blue1on>0)
            {
                blue1.setTextColor(android.graphics.Color.BLACK);
                blue1.setBackgroundColor(android.graphics.Color.rgb(60,111,240));
            }
            else
            {
                blue1.setTextColor(android.graphics.Color.rgb(60,111,240));
                blue1.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (blue2on>0)
            {
                blue2.setTextColor(android.graphics.Color.BLACK);
                blue2.setBackgroundColor(android.graphics.Color.rgb(60,111,240));
            }
            else
            {
                blue2.setTextColor(android.graphics.Color.rgb(60,111,240));
                blue2.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (red1on>0)
            {
                red1.setTextColor(android.graphics.Color.BLACK);
                red1.setBackgroundColor(android.graphics.Color.rgb(255,50,50));
            }
            else
            {
                red1.setTextColor(android.graphics.Color.rgb(255,50,50));
                red1.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (red2on>0)
            {
                red2.setTextColor(android.graphics.Color.BLACK);
                red2.setBackgroundColor(android.graphics.Color.rgb(255,50,50));
            }
            else
            {
                red2.setTextColor(android.graphics.Color.rgb(255,50,50));
                red2.setBackgroundColor(android.graphics.Color.BLACK);
            }
            if (mirror1on>0)
            {
                mirror1.setTextColor(android.graphics.Color.BLACK);
                mirror1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mirror1.setTextColor(android.graphics.Color.GRAY);
                mirror1.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (bed1on>0)
            {
                bed1.setTextColor(android.graphics.Color.BLACK);
                bed1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                bed1.setTextColor(android.graphics.Color.GRAY);
                bed1.setBackgroundColor(android.graphics.Color.DKGRAY);
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
            if (green1on>0)
            {
                green1.setTextColor(android.graphics.Color.BLACK);
                green1.setBackgroundColor(android.graphics.Color.rgb(44,255,122));
                /*
                green1.setTextColor(android.graphics.Color.BLACK);
                green1.setBackgroundColor(android.graphics.Color.GRAY);
                udp.setTextColor(android.graphics.Color.BLACK);
                udp.setBackgroundColor(android.graphics.Color.GRAY);
                */
            }
            else
            {
                green1.setTextColor(android.graphics.Color.rgb(44,255,122));
                green1.setBackgroundColor(android.graphics.Color.BLACK);
                /*
                green1.setTextColor(android.graphics.Color.GRAY);
                green1.setBackgroundColor(android.graphics.Color.DKGRAY);
                udp.setTextColor(android.graphics.Color.GRAY);
                udp.setBackgroundColor(android.graphics.Color.DKGRAY);
                */
            }
            if (aux1on>0)
            {
                aux1.setTextColor(android.graphics.Color.BLACK);
                aux1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                aux1.setTextColor(android.graphics.Color.GRAY);
                aux1.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (mainlight1an>0)
            {
                mainlight1.setTextColor(android.graphics.Color.BLACK);
                mainlight1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                mainlight1.setTextColor(android.graphics.Color.GRAY);
                mainlight1.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (tv1on>0)
            {
                tv1.setTextColor(android.graphics.Color.BLACK);
                tv1.setBackgroundColor(android.graphics.Color.GRAY);
            }
            else
            {
                tv1.setTextColor(android.graphics.Color.GRAY);
                tv1.setBackgroundColor(android.graphics.Color.DKGRAY);
            }
            if (pharaoon>0)
            {
                pharao.setTextColor(android.graphics.Color.BLACK);
                pharao.setBackgroundColor(android.graphics.Color.rgb(241,67,20));
            }
            else
            {
                pharao.setTextColor(android.graphics.Color.rgb(241,67,20));
                pharao.setBackgroundColor(android.graphics.Color.BLACK);
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
                        blue1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("blueleft")==true && line.contains("=")==true)
                    {
                        blue2on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("redright")==true && line.contains("=")==true)
                    {
                        red2on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("redleft")==true && line.contains("=")==true)
                    {
                        red1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("green")==true && line.contains("=")==true)
                    {
                        green1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("tv")==true && line.contains("=")==true)
                    {
                        tv1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("pharao")==true && line.contains("=")==true)
                    {
                        pharaoon = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("mirror")==true && line.contains("=")==true)
                    {
                        mirror1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("aux")==true && line.contains("=")==true)
                    {
                        aux1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("mainlight")==true && line.contains("=")==true)
                    {
                        mainlight1an = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        line = r.readLine();
                    }
                    if (line.contains("bed")==true && line.contains("=")==true)
                    {
                        bed1on = (line.charAt(line.indexOf("=") + 1) == '1') ? 1 : 0;
                        continue;
                    }
                }
            } catch (IOException e) {
                errocc = 1;
                errstr = "readStream failed: " + e.getMessage();
            }
        }
    }

    private class UDPListener implements Runnable
    {
        DatagramSocket socket;

        private void listenAndWaitAndThrowIntent(InetAddress broadcastIP, Integer port) throws Exception {
            byte[] recvBuf = new byte[4];
            if (socket == null || socket.isClosed()) {
                socket = new DatagramSocket(port, broadcastIP);
                socket.setBroadcast(true);
            }
            //socket.setSoTimeout(1000);
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            Log.e("UDP", "Waiting for UDP broadcast");
            socket.receive(packet);

            String senderIP = packet.getAddress().getHostAddress();

            Log.e("UDP", "Got UDB broadcast from " + senderIP);

            socket.close();
        }

        @Override
        public void run()
        {
            try
            {
                InetAddress broadcastIP = InetAddress.getByName("192.168.178.255");
                Integer port = 11111;
                while (true) {
                    listenAndWaitAndThrowIntent(broadcastIP, port);
                    send_command_http("http://rk3399:12000/main.cgi");
                }
                //if (!shouldListenForUDPBroadcast) throw new ThreadDeath();
            } catch (Exception e) {
                Log.i("UDP", "no longer listening for UDP broadcasts cause of error " + e.getMessage());
            }
        }
    }
}
