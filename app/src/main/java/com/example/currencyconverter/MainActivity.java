package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable {

    //private static String rmbnum = "0";// 输入的人民币数值
    //private static String dollarnum = "0";// 显示的美元结果
    //private static String euronum = "0";// 显示的欧元结果
    //private static String wonnum = "0";// 显示的韩元结果

    //private  final  int scoreArray[]={1,2,3};
    //private Button button,button2,button3,button5;
    //private TextView textView2;
    //EditText edt;
    //private  static final  String TAG = "MainActivity";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==5){
                String str =  (String)msg.obj;
                Log.i("TAG", "handleMessage: getMessage msg = "+str);
            }
            super.handleMessage(msg);
        }
    };
    URL url;
    float dollar;
    float pound;
    float euro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("rate", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        Thread t = new Thread(this);
        t.start();
        dollar = sp.getFloat("dollar_rate", 0.146585f);
        pound = sp.getFloat("pound_rate", 0.114881f);
        euro = sp.getFloat("euro_rate", 0.125975f);
    }

    public void dollar(View v) {
        TextView input = findViewById(R.id.edt);
        TextView output = findViewById(R.id.textView2);
        if(input.getText().toString().isEmpty()) {
            //no input
            Toast.makeText(this,

                    "请输入人民币金额", Toast.LENGTH_SHORT).show();
        }else{
            String str = input.getText().toString();
            Float result;
            if(v.getId() == R.id.button){
                result = Float.parseFloat(str) * dollar;
                output.setText("" + result);
            }else if(v.getId() == R.id.button3){
                result = Float.parseFloat(str) * pound;
                output.setText("" + result);
            }else if(v.getId() == R.id.button4){
                result = Float.parseFloat(str) * euro;
                output.setText("" + result);
            }
        }
    }



    public void config(View v) {
        Intent config = new Intent(this, MainActivity2.class);
        config.putExtra("dollar_rate", dollar);
        config.putExtra("pound_rate", pound);
        config.putExtra("euro_rate", euro);
        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle rate = data.getExtras();
            Toast.makeText(this, "配置更新中", Toast.LENGTH_SHORT).show();
            dollar = rate.getFloat("dollar_rate", 0.0f);
            pound = rate.getFloat("pound_rate", 0.0f);
            euro = rate.getFloat("euro_rate", 0.0f);
            Log.i("TAG","dollar_rate=" + dollar);
            Toast.makeText(this, "配置已更新", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Message msg = handler.obtainMessage(5);
        try {
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Log.i("TAG","run:html = "+html);
//            Document doc = Jsoup.connect(URL).data().post();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        msg.obj = "Hello from run()";
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        //prirvate String inputStream2String(InputStream inputStream) throws IOException
        //final
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

}