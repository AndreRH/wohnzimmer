package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
//import java.lang.Byte;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Formatter;

public class epl extends Activity
{
    byte speed=2;
    byte brightness=120;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epl);

        Button sendudp = (Button) findViewById(R.id.sendudp);
        sendudp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                send_udp();
            }
        });

        SeekBar brightctrl = (SeekBar) findViewById(R.id.bright_bar);
        brightctrl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
                brightness = (byte) progressChanged;
            }
        });

        SeekBar speedctrl = (SeekBar) findViewById(R.id.speed_bar);
        speedctrl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress + 1;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
                speed = (byte) progressChanged;
            }
        });

    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format(" %02x", b);
        }

        return sb.toString();  
    }

    private static byte[] StringToMessge(byte command, byte spd, byte brt, String str) {
        byte[] bytes = new byte[3+str.length()+1];
        byte[] strb = str.getBytes();

        bytes[0] = command;
        bytes[1] = spd;
        bytes[2] = brt;
        for(int i=1; i<=str.length(); i++) {
            bytes[i+2] = strb[i-1];
        }

        return bytes;  
    }

    private void send_udp()
    {
        UdpTask udptsk = new UdpTask(this);
        udptsk.execute();
    }

    private class UdpTask extends AsyncTask<Void, Void, byte[]> {

        private Exception exception;

        private Context context;

        public UdpTask(Context context) {
            this.context = context;
        }

        protected byte[] doInBackground(Void... v) {
            try {
                InetAddress local;
                DatagramSocket s;
                int server_port = 32000;
                s = new DatagramSocket();
                try {
                    local = InetAddress.getByName("192.168.178.54");
                } catch (java.net.UnknownHostException e) {
                    s.close();
                    return null;
                }
/*
                EditText speedstr = (EditText) findViewById(R.id.speedstr);
                speed = Byte.parseByte(speedstr.getText().toString());
                EditText brightstr = (EditText) findViewById(R.id.brightstr);
                brightness = Byte.parseByte(brightstr.getText().toString());*/
                EditText inputstr = (EditText) findViewById(R.id.inputstr);
                String instr = inputstr.getText().toString();
                byte[] message = StringToMessge((byte)185, speed, brightness, instr);

                DatagramPacket p = new DatagramPacket(message, instr.length()+4, local, server_port);
                s.send(p);

                s.close();
                return message;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(byte[] ret) {
            if (this.exception!=null) {
                Toast toast = Toast.makeText(context, this.exception.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
                this.exception = null;
            }
            if (ret!=null) {
                TextView test2 = (TextView) findViewById(R.id.test2);
                test2.setText(bytesToHexString(ret));
                /*test2.append(bytesToHexString(ret));
                test2.append(" -");*/
            }
        }
    }
}
