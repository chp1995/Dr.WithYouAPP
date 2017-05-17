package a800.com.drwithyouapp1st;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;


public class InfoActivity extends AppCompatActivity {

    String token;

    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    EditText et6;
    Button savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_info);

        Intent it=getIntent();
        token=it.getStringExtra("key");

        initWidget();
    }

    private void initWidget()
    {
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);
        et6=(EditText)findViewById(R.id.editText6);
        savebtn=(Button)findViewById(R.id.saveButton);
        savebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用一个线程
                receiveRequestWithHttpClient();
            }
        });

        sendRequestWithHttpClient();

    }


    //开辟新线程
    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String httpUrl = "http://dr.akchen.top:8080/DrWithYouWebServer/aduser";
                    httpUrl += "?"+"token="+token;
                    String str = getJsonByInternet(httpUrl);
                    String newstr=StringUtil.Decode(str);
                    //String flag = str.split(" ")[1];
                    JSONObject jsonObject=new JSONObject(newstr);
                    String result=jsonObject.getString("result");
                    JSONObject jsonSet=new JSONObject(jsonObject.getString("set"));
                    if (result.equals("true")) {
                        String dsada=jsonSet.getString("name");
                        et1.setText(dsada);
                        et2.setText(jsonSet.getString("doctor"));
                        et3.setText(jsonSet.getString("illness"));
                        et4.setText(jsonSet.getString("phone"));
                        et5.setText(jsonSet.getString("sex"));
                        et6.setText(jsonSet.getString("idcard"));
                    } else {
                        Toast.makeText(InfoActivity.this, "error!", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();//这个start()方法不要忘记了
    }
    //传入url 接收返回数据
    public static String getJsonByInternet(String path){
        //使用httpURLconnention 进行连接
//            String msg = "";
//            try {
//            URL url = new URL(path.trim());
//            //打开连接
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            if(200 == urlConnection.getResponseCode()){
//                //得到输入流
//                InputStream is =urlConnection.getInputStream();
//                // 创建字节输出流对象
//                ByteArrayOutputStream message = new ByteArrayOutputStream();
//                // 定义读取的长度
//                int len = 0;
//                // 定义缓冲区
//                byte buffer[] = new byte[1024];
//                // 按照缓冲区的大小，循环读取
//                while ((len = is.read(buffer)) != -1) {
//                    // 根据读取的长度写入到os对象中
//                    message.write(buffer, 0, len);
//                }
//                // 释放资源
//                is.close();
//                message.close();
//                // 返回字符串
//                msg = new String(message.toByteArray());
//                return msg;
//            }
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();

    }

    //开辟新线程
    private void receiveRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str= InfoPostUtils.InfoByPost(token,et1.getText().toString(), et4.getText().toString(),et5.getText().toString(),et6.getText().toString());
                    //
                    //String flag = str.split(" ")[1];
                    String newstr=StringUtil.Decode(str);
                    //String flag = str.split(" ")[1];
                    JSONObject jsonObject=new JSONObject(newstr);
                    String result=jsonObject.getString("result");
                    if (result.equals("true")) {
                        Toast.makeText(InfoActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InfoActivity.this, "失败", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();//这个start()方法不要忘记了
    }
}
