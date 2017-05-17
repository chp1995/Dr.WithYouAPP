package a800.com.drwithyouapp1st;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CenterActivity extends AppCompatActivity {

    Button btn1;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        Intent it=getIntent();
        token=it.getStringExtra("key");

        btn1=(Button)findViewById(R.id.button4);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(CenterActivity.this ,InfoActivity.class);
                intent.putExtra("key",token);
                startActivity(intent);
            }
        });
    }
}
