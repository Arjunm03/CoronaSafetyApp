package com.example.coronasafety;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<Void, Void, Void> {
    String info = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.covidtracking.com/v1/states/current.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));
            String line = "";
            while (line != null) {
                line = br.readLine();
                info = info + line;
            }
            JSONArray JA = new JSONArray(info);
            int x = 0;
            for (int i = 0; i < MainActivity.data.length - 1; i++) {
                if (x == 3 || x == 12 || x == 27 || x == 42) {
                    x++;
                    i--;
                    continue;
                }
                JSONObject JO = (JSONObject) JA.get(x);
                int caseNum = (int) JO.get("positive");
                MainActivity.data[i][2] = Integer.toString(caseNum);
                x++;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        info = "";
        try {
            URL url = new URL("https://api.covidtracking.com/v1/us/current.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));
            String line = "";
            while (line != null) {
                line = br.readLine();
                info = info + line;
            }
            JSONArray JA = new JSONArray(info);
            JSONObject JO = (JSONObject) JA.get(0);
            int caseNum = (int) JO.get("positive");
            MainActivity.data[52][2] = Integer.toString(caseNum);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
