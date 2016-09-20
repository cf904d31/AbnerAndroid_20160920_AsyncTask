package iii.org.tw.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView showText;
    private MyTask mt1;
    private ImageView showImg;
    private Bitmap bmp;
    private UIHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showText = (TextView) findViewById(R.id.showText);
        showImg = (ImageView) findViewById(R.id.showImg);
        handler = new UIHandler();
    }

    private class MyTask extends AsyncTask<String,Object,String> {
        @Override
        protected String doInBackground(String... params) {
            Log.d("Abner","doInBackground");
            int i=0; boolean isCancel = false;
            for (String name : params) {
                if (isCancelled()) {
                    isCancel = true;
                    break;
                }
                i++;
                Log.d("Abner","Hello ,"+name);
                publishProgress(i,name);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return isCancel?"Cancel!":"Game Over";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Abner","onPreExecute");
        }

        @Override
        protected void onPostExecute(String end) {
            super.onPostExecute(end);
            Log.d("Abner","onPostExecute: " +end);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Log.d("Abner","onProgressUpdate");
            showText.setText((Integer) values[0] + ":" + (String)values[1]);
        }

        @Override
        protected void onCancelled(String end) {
            super.onCancelled(end);
            Log.d("Abner","onCancelled :" +end);
        }
    }

    public void async(View v) {
        mt1 = new MyTask();
        mt1.execute("Mary","Jack","Tom","Eric","Jerry","Abner");
    }

    public void stop(View v) {
        if (mt1 != null && !mt1.isCancelled()) {
            //-----這裡通常給true值，代表不管後台有無東西在動作都會直接被中斷
            mt1.cancel(true);
        }
    }

    public void showImgView(View v) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.androidcentral.com/sites/androidcentral.com/files/styles/w700/public/postimages/%5Buid%5D/podcast-ac-new.jpg?itok=tKuELSVP");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    bmp = BitmapFactory.decodeStream(conn.getInputStream());
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showImg.setImageBitmap(bmp);
        }
    }
}
