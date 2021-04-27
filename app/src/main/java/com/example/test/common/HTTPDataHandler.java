package com.example.test.common;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class HTTPDataHandler {
    public String stream="";

    public HTTPDataHandler(){}

    public String getHTTPData(String urlString)  {
        try{

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                stream = sb.toString();
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            Log.i("HTTP","ВСЕ ПЛОХО");
            return "";
        }

        return stream;
    }
}
