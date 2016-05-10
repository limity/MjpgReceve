package com.ycm.mjpgreceve;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edit_ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(1);
        //setRequestedOrientation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_ip     =   (EditText)findViewById(R.id.edit_ip);
        findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                intent.putExtra("ip", edit_ip.getText().toString());
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.action_bar:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
