package iii.org.tw.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView showText;
    private MyTask mt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showText = (TextView) findViewById(R.id.showText);
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.d("Abner","doInBackground");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Abner","onPreExecute");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Abner","onPostExecute");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.d("Abner","onProgressUpdate");
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            Log.d("Abner","onCancelled");
        }
    }

    public void async(View v) {
        mt1 = new MyTask();
        mt1.execute();
    }

    public void stop(View v) {
        if (mt1 != null && !mt1.isCancelled()) {
            //-----這裡通常給true值，代表不管後台有無東西在動作都會直接被中斷
            mt1.cancel(true);
        }
    }
}
