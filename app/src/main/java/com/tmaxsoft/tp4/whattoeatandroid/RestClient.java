package com.tmaxsoft.tp4.whattoeatandroid;

/**
 * Created by jwhan on 16. 10. 18.
 */


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RestClient {

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /* This is a test function which will connects to a given
     * rest service and prints it's response to Android Log with
     * labels "Praeda".
     */
    public static JSONObject get(String url) {

        // Prepare a request object
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 2000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 2000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("Praeda", response.getStatusLine().toString());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                Log.i("Praeda", result);

                // A Simple JSONObject Creation
                JSONObject json = new JSONObject(result);
                Log.i("Praeda", "<jsonobject>\n" + json.toString() + "\n</jsonobject>");

                // A Simple JSONObject Parsing
                JSONArray nameArray = json.names();
                JSONArray valArray = json.toJSONArray(nameArray);
                for (int i = 0; i < valArray.length(); i++) {
                    Log.i("Praeda", "<jsonname" + i + ">\n" + nameArray.getString(i) + "\n</jsonname" + i + ">\n"
                            + "<jsonvalue" + i + ">\n" + valArray.getString(i) + "\n</jsonvalue" + i + ">");
                }

                // A Simple JSONObject Value Pushing
                json.put("sample key", "sample value");
                Log.i("Praeda", "<jsonobject>\n" + json.toString() + "\n</jsonobject>");

                // Closing the input stream will trigger connection release
                instream.close();

                return json;
            }


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getFood() {
        String result = "";

        try {
            JSONObject jsonObject = get("http://52.78.182.130:8080/WhatToEatServer/WhatToEat");
            if (jsonObject != null) {
                result = jsonObject.getString("food");
                return result;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

}
