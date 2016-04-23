package com.riansousa.foodtrackerv2.Logic;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * This class is adapted from examples on:
 * http://developer.android.com/training/basics/network-ops/connecting.html#AsyncTask and
 * http://stackoverflow.com/questions/7618614/return-data-from-asynctask-class
 */
public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

    /** declare global variables */
    DataDownloadListener dataDownloadListener;

    /** class constructor */
    public DownloadWebPageTask()
    {
        //Constructor may be parametric
    }

    /** create interface for callback */
    public static interface DataDownloadListener {
        void dataDownloadedSuccessfully(Object data);
        void dataDownloadFailed();
    }

    /** attach event listener */
    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        this.dataDownloadListener = dataDownloadListener;
    }

    /**
     * method to be run in the backround
     * @param urls
     * @return
     */
    @Override
    protected String doInBackground(String[] urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    /**
     * execute callbacks to return data from async process
     * @param aResult
     */
    @Override
    protected void onPostExecute(String aResult) {
        if(aResult != null) {
            dataDownloadListener.dataDownloadedSuccessfully(aResult);
        } else {
            dataDownloadListener.dataDownloadFailed();
        }
    }

    /**
     * method to download a web page using a url
     * @param aUrl
     * @return
     * @throws IOException
     */
    private String downloadUrl(String aUrl) throws IOException {
        InputStream inStream = null;
        try {
            /** create url */
            URL url = new URL(aUrl);

            /** open connection */
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            /** set properties */
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            inStream = conn.getInputStream();

            /** read page data */
            String contentAsString = convertStreamToString(inStream);
            return contentAsString;

            // Close InputStream after the app completed use
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    /**
     * method to read in the entire contents of the page rather than fixed char length, copied from
     * https://github.com/beatcoin/androidplayer/blob/master/Beatcoin/src/main/java/org/beatcoin/player/android/DownloadWebpageTask.java
     * @param is
     * @return
     */
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

