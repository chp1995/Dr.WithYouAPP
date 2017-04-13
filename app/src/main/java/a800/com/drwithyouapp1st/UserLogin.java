package a800.com.drwithyouapp1st;

/**
 * Created by lenovo on 2017/4/12.
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends Activity implements OnClickListener {
    private EditText login_username;
    private EditText login_password;
    private Button user_login_button;
    private Button user_register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.user_login);
        initWidget();

    }
    private void initWidget()
    {
        login_username=(EditText)findViewById(R.id.login_username);
        login_password=(EditText)findViewById(R.id.login_password);
        user_login_button=(Button)findViewById(R.id.user_login_button);
        user_register_button=(Button)findViewById(R.id.user_register_button);
        user_login_button.setOnClickListener(this);
        user_register_button.setOnClickListener(this);
        login_username.setOnFocusChangeListener(new OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    String username=login_username.getText().toString().trim();
                    if(username.length()<4){
                        Toast.makeText(UserLogin.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT);
                    }
                }
            }

        });
        login_password.setOnFocusChangeListener(new OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    String password=login_password.getText().toString().trim();
                    if(password.length()<4){
                        Toast.makeText(UserLogin.this, "密码不能小于4个字符", Toast.LENGTH_SHORT);
                    }
                }
            }

        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.user_login_button:
                if(checkEdit())
                {
                    login();
                }

                break;
            case R.id.user_register_button:
                Intent intent2=new Intent(UserLogin.this,UserRegister.class);
                startActivity(intent2);
                break;
        }
    }

    private boolean checkEdit(){
        if(login_username.getText().toString().trim().equals("")){
            Toast.makeText(UserLogin.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        }else if(login_password.getText().toString().trim().equals("")){
            Toast.makeText(UserLogin.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

    private void login(){
        String httpUrl="http:// 43.247.68.17:3306/Slogin?";
        HttpPost httpRequest=new HttpPost(httpUrl);
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username",login_username.getText().toString().trim()));
        params.add(new BasicNameValuePair("password",login_password.getText().toString().trim()));
        HttpEntity httpentity = null;
        try {
            httpentity = new UrlEncodedFormEntity(params,"utf8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpRequest.setEntity(httpentity);
        HttpClient httpclient=new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpRequest);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(httpResponse.getStatusLine().getStatusCode()==200)
        {
            String strResult = null;
            try {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Toast.makeText(UserLogin.this, strResult, Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(UserLogin.this,IndexActivity.class);
//            startActivity(intent);
        }
        else
        {
            Toast.makeText(UserLogin.this, "登录失败！", Toast.LENGTH_SHORT).show();
        }

    }
}