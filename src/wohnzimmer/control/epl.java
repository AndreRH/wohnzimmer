package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Formatter;

public class epl extends Activity
{
    byte cmd=(byte)185;
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

        Spinner spinner = (Spinner) findViewById(R.id.cmd_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cmd_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                cmd = (byte)(pos + 185);
            }

            public void onNothingSelected(AdapterView parent) {
                cmd = (byte) 185;
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
        byte[] bytes = new byte[3+3+str.length()+1];
        byte[] strb;

        try {
            strb = str.getBytes("ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            strb = str.getBytes();
        }

        bytes[0] = command;
        if (command == (byte)(185 + 7)) /* Uhr */
        {
            Calendar c = Calendar.getInstance();
            bytes[1] = 1;
            bytes[2] = brt;
            bytes[3] = (byte) c.get(Calendar.HOUR_OF_DAY);
            bytes[4] = (byte) c.get(Calendar.MINUTE);
            bytes[5] = (byte) c.get(Calendar.SECOND);
            bytes[6] = 0;
        }
        else
        {
            bytes[1] = spd;
            bytes[2] = brt;
            for (int i=1; i<=str.length(); i++) {
                byte tmpb = strb[i-1];

                if (tmpb == (byte)0xc4) tmpb = (byte)0x80;      /* Ä */
                else if (tmpb == (byte)0xd6) tmpb = (byte)0x81; /* Ö */
                else if (tmpb == (byte)0xdc) tmpb = (byte)0x82; /* Ü */
                else if (tmpb == (byte)0xe4) tmpb = (byte)0x83; /* ä */
                else if (tmpb == (byte)0xf6) tmpb = (byte)0x84; /* ö */
                else if (tmpb == (byte)0xfc) tmpb = (byte)0x85; /* ü */
                else if (tmpb == (byte)0xdf) tmpb = (byte)0x86; /* ß */
                else if (tmpb == (byte)0xe9) tmpb = (byte)0x87; /* é */
                else if (tmpb == (byte)0xa4) tmpb = (byte)0x88; /* <3 */

                bytes[i+2] = tmpb;
            }
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

                EditText inputstr = (EditText) findViewById(R.id.inputstr);
                String instr = inputstr.getText().toString();
                byte[] message = StringToMessge(cmd, speed, brightness, instr);

                DatagramPacket p = new DatagramPacket(message, instr.length()+7, local, server_port);
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

            /*
            {
                TextView test2 = (TextView) findViewById(R.id.test2);
                EditText inputstr = (EditText) findViewById(R.id.inputstr);
                String instr = inputstr.getText().toString();
                byte[] strb;
                try {
                    strb = instr.getBytes("ISO-8859-1");
                } catch (java.io.UnsupportedEncodingException e) {
                    Toast toast = Toast.makeText(context, "damn ISO-8859-1", Toast.LENGTH_SHORT);
                    toast.show();
                    strb = instr.getBytes();
                }
                test2.setText(bytesToHexString(strb));
            }
            */
            /*
            if (ret!=null) {
                TextView test2 = (TextView) findViewById(R.id.test2);
                test2.setText(bytesToHexString(ret));
            }
            */
        }
    }
}
