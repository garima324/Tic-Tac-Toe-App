package UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wallpaper_application.R;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(Splash_Screen.this,Home_Activity.class);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}

