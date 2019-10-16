package com.ceibalabs.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {
        //class designed to be runned on a different thread
        //AsyncTask<1,2,3>
        //1: Type of variable we are going to send to the class (DownloadTask) in this case a URL
        //2: name of the method that we can or not use to show the progress of the task
        //3: Type of object thats is going to be returned by the task once it finishes

        @Override
        protected String doInBackground(String... urls) { //String... = var args its like an Array, contains a bunch of Strings

            Log.i("URL", urls[0]); //we show the first element of the var agrs (array)

            //BEGIN Second STEP
            String result = ""; //the object that will hold all the HTML code of the website
            URL url;
            HttpURLConnection urlConnection = null; //is like a browser to fethc the contents of the url

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection(); //open a browser window an attempt to connect
                InputStream inputStream= urlConnection.getInputStream(); //stream to hold the input of data as it comes
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read(); //we read 1 character at a time (just like the FileReader)
                while(data != -1){
                    char currentCharacter = (char) data;
                    result += currentCharacter;
                    data = reader.read();
                }
                return result;


            } catch(Exception e){
                e.printStackTrace();
                return "Failed";
            }
            //END Second STEP

            ////return "Done"; //parameter #3 of AsyncTask is a String
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null; //we can do this because of the var args
        try {
            //result = task.execute("https://www.google.com", "https://www.stackoverflow.com").get();
            result = task.execute("https://www.anahuacmayab.mx/").get();
        } catch (InterruptedException e) {
            e.printStackTrace(); //prints all the info of the exception
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("Result", result);
    }

}
