package wohnzimmer.control;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import java.io.*;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Formatter;

public class jarbot extends Activity
{
    int cmd;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jarbot);

        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: cmd = cmd |  1; send_command_udp(cmd); break;
                case MotionEvent.ACTION_UP:   cmd = cmd & ~1; send_command_udp(cmd); break;
                }
                return true;
            }
        });

        Button backward = (Button) findViewById(R.id.backward);
        backward.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: cmd = cmd |  2; send_command_udp(cmd); break;
                case MotionEvent.ACTION_UP:   cmd = cmd & ~2; send_command_udp(cmd); break;
                }
                return true;
            }
        });

        Button left = (Button) findViewById(R.id.left);
        left.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: cmd = cmd |  4; send_command_udp(cmd); break;
                case MotionEvent.ACTION_UP:   cmd = cmd & ~4; send_command_udp(cmd); break;
                }
                return true;
            }
        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: cmd = cmd |  8; send_command_udp(cmd); break;
                case MotionEvent.ACTION_UP:   cmd = cmd & ~8; send_command_udp(cmd); break;
                }
                return true;
            }
        });
    }

    private void send_command_udp(int cmd)
    {
        UdpTask udptsk = new UdpTask(this);
        udptsk.execute(cmd);
    }

    private class UdpTask extends AsyncTask<Integer, Void, Void> {

        private Exception exception;

        private Context context;

        public UdpTask(Context context) {
            this.context = context;
        }

        protected Void doInBackground(Integer... ints) {
            DatagramSocket s = null;
            int cmd  = ints[0];

            try {
                InetAddress local;
                int server_port = 32202;

                s = new DatagramSocket();
                try {
                    local = InetAddress.getByName("192.168.178.34");
                } catch (java.net.UnknownHostException e) {
                    s.close();
                    return null;
                }
                s.setSoTimeout(42);

                byte[] bytes = new byte[1];
                bytes[0] = (byte)cmd;

                DatagramPacket p = new DatagramPacket(bytes, bytes.length, local, server_port);
                s.send(p);

                s.close();
                return null;
            } catch (InterruptedIOException e) {
                if (s != null)
                {
                    s.close();
                    s = null;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
