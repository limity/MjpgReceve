package com.ycm.mjpgreceve;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;


public class IndexActivity extends AppCompatActivity {
    private Point size;
    private SurfaceHolder holder;
    private Thread my_thread;
    private Canvas canvas;
    private String url;
    private int height;
    private URL videoUrl = null;
    private HttpURLConnection conn;
    private Bitmap bmp;
    private PrintStream outstream;
    private final String SERVER_HOST_IP = "192.168.43.66";
    private final int SERVER_HOST_PORT = 8000;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        url = getIntent().getStringExtra("ip");

        Btn();
        Show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Btn() {
        Thread myThread = new Thread() {
            @Override
            public void run() {
                super.run();
                    sendMessage();

            }
        };
        myThread.start();
        Button btn_top = (Button) findViewById(R.id.btn_forward);
        btn_top.getLayoutParams().height = size.x / 4;
        btn_top.getLayoutParams().width = size.x / 2;
        btn_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        outstream.print("aa");
                        Toast.makeText(getApplicationContext(), "前进",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        outstream.print("tt");
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        Button btn_down = (Button) findViewById(R.id.btn_back);
        btn_down.getLayoutParams().height = size.x / 4 - 20;
        btn_down.getLayoutParams().width = size.x / 2;
        btn_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        outstream.print("ss");
                        Toast.makeText(getApplicationContext(), "后退",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        outstream.print("tt");
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        Button btn_le = (Button) findViewById(R.id.btn_left);
        btn_le.getLayoutParams().height = size.x / 2;
        btn_le.getLayoutParams().width = size.x / 4;
        btn_le.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        outstream.print("lf");
                        Toast.makeText(getApplicationContext(), "左转",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        outstream.print("tt");
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        Button btn_ri = (Button) findViewById(R.id.btn_right);
        btn_ri.getLayoutParams().height = size.x / 2;
        btn_ri.getLayoutParams().width = size.x / 4;
        btn_ri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        outstream.print("rt");
                        Toast.makeText(getApplicationContext(), "右转",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        outstream.print("tt");
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void Show() {
        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        ViewGroup.LayoutParams lp = surface.getLayoutParams();
        lp.width = size.x;
        height = size.y - size.x / 2;
        lp.height = height;
        surface.setLayoutParams(lp);
        surface.setKeepScreenOn(true);
        my_thread = new Thread() {
            @Override
            public void run() {
                super.run();
                     while(true) {
                         draw();
                     }
            }
        };
        holder = surface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                my_thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void draw() {
        try {
            videoUrl = new URL(url);
            conn = (HttpURLConnection) videoUrl.openConnection();
            conn.setDoInput(true);
            InputStream inputstream = null;
            conn.connect();
            inputstream = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(inputstream);
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            Rect rect = new Rect(0, 0, size.x, height);
            canvas.drawBitmap(bmp, null, rect, null);
            holder.unlockCanvasAndPost(canvas);
            conn.disconnect();
        } catch (Exception ex) {
        } finally {
        }
    }

    private void sendMessage() {
        try {
            socket = new Socket(SERVER_HOST_IP,SERVER_HOST_PORT);
            outstream = new PrintStream(socket.getOutputStream(), true, "utf-8");
        }catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }


}


