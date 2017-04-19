package a800.com.drwithyouapp1st;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());

        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new ButtonListener2());
    }

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this ,UserLogin.class);
            startActivity(intent);
        }
    }

    class ButtonListener2 implements OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this ,UserRegister.class);
            startActivity(intent);
        }
    }

}
