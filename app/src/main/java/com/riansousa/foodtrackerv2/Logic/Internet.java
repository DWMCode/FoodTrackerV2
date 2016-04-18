package com.riansousa.foodtrackerv2.Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class Internet {

    /* declare global variables */
    private static final String TAG = "FoodTracker";
    private String page = "";

    /* class getters */
    public String getPage() {
        return this.page;
    }

    /**
     * Method to log the iNet connectivity and status
     * @param context
     * @return
     */
    public String logInetConn(android.content.Context context) {
        String msg = "";
        try {
            /** instantiate connectivity manager */
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            /** get network info */
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();

            /** create message */
            if ((netInfo != null && netInfo.isConnectedOrConnecting())) {
                msg = "TRUE|You are connected to the internet. Details: " + netInfo.toString();
            } else {
                msg = "FALSE|You ARE NOT connected to the internet. Details: " + netInfo.toString();
            }

            /** log status */
            Log.i(TAG, "Internet.logInetConn() - " + msg);

        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.logInetConn() -  ERROR: " + e.getMessage());
        }
        return msg;
    }

    /**
     * method to get a web page asynchronously
     * @param context
     * @param url
     * @return
     */
    public String getWebPage(android.content.Context context, String url) {
        try {
            /** instantiate the connectivity manager */
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            /** check network connection */
            if (networkInfo != null && networkInfo.isConnected()) {
                /** instantiate download class */
                DownloadWebPageTask getdata = new DownloadWebPageTask();

                /** attach listener */
                getdata.setDataDownloadListener(new DownloadWebPageTask.DataDownloadListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void dataDownloadedSuccessfully(Object data) {
                        /** set page data */
                        page = data.toString();
                    }

                    @Override
                    public void dataDownloadFailed() {
                        /** log failure */
                        Log.i(TAG, "Internet.getWebPage() -  ERROR: The download failed.");
                    }
                });
                getdata.execute(url);
            }
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.getWebPage() -  ERROR: " + e.getMessage());
        }
        return getPage();
    }

    /**
     * method to extract the list items from a page
     * @param page
     * @return
     */
    public String[] getItemsFromPage(android.content.Context context, String page) {
        String[] results = null;
        try {
            /** instantiate hashtable */
            Hashtable<String, String> itemDetails = new Hashtable<String, String>();

            /** get start / end points */
            int listStart = page.indexOf("<ol style=\"clear:both;\">") + 24;
            int listEnd = page.indexOf("</ol>");

            /** manipulate options string */
            String options = page.substring(listStart, listEnd);
            options = options.replace("</li>","").replace("<li>", "");

            /** split into array */
            String[] listItems = options.split("\n");

            /** loop through the array */
            for (int i=0; i<listItems.length; i++) {
                /** clean the data */
                String option = listItems[i];
                option = removeCode(option);
                option = removeParen(option);
                option = removeSpace(option);
                String url = getUrl(option);
                String name = getName(option);

                /** add to the hashtable */
                if ((name.length() > 0) && ( !itemDetails.containsKey(name))) {
                    itemDetails.put(name, url);
                }
            }

            /** convert hashtable data to string array */
            String[] list = new String[itemDetails.size()];
            Enumeration e = itemDetails.keys();
            int i = 0;
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                list[i] = key;
                i++;
            }
            results = list;

            /** serialize hashtable for later use
             * serialization research from http://beginnersbook.com/2013/12/how-to-serialize-hashmap-in-java/
             * */
            FileOutputStream fos = context.openFileOutput("hashtable.ser", Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(itemDetails);
            oos.close();
            fos.close();

        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.getItemsFromPage() -  ERROR: " + e.getMessage());
        }

        return results;
    }

    /**
     * method to clean data
     * @param option
     * @return
     */
    private String removeCode(String option) {
        try {
            int listStart = option.indexOf("<code>");
            if (listStart > -1) {
                int listEnd = option.indexOf("</code>");
                String snippet = option.substring(listStart, listEnd + 7);
                option = option.replace(snippet, "");
            }
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.removeCode() -  ERROR: " + e.getMessage());
        }
        return option;
    }

    /**
     * method to clean data
     * @param option
     * @return
     */
    private String removeParen(String option) {
        try {
            int listStart = option.indexOf("(");
            if (listStart > -1) {
                int listEnd = option.indexOf(")");
                String snippet = option.substring(listStart, listEnd+1);
                option = option.replace(snippet, "");
            }
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.removeCode() -  ERROR: " + e.getMessage());
        }
        return option;
    }

    /**
     * method to clean data
     * @param option
     * @return
     */
    private String removeSpace(String option) {
        return option.replace("&nbsp;","");
    }

    /**
     * method to clean data
     * @param option
     * @return
     */
    private String getUrl(String option) {
        try {
            int listStart = option.lastIndexOf("<a href");
            int listEnd = option.indexOf(">", listStart);
            option = option.substring(listStart+9, listEnd-1);
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.removeCode() -  ERROR: " + e.getMessage());
        }
        return option;
    }

    /**
     * method to clean data
     * @param option
     * @return
     */
    private String getName(String option) {
        try {
            int listStart = option.lastIndexOf("<a href");
            int listEnd = option.indexOf(">", listStart);
            String link = option.substring(listStart, listEnd+1);
            option = option.replace("</a>","");
            option = option.replace(link,"");
            option = option.replace("\n","");
            option = option.replace("&amp;","&").trim();
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "Internet.removeCode() -  ERROR: " + e.getMessage());
        }
        return option;
    }

    /**
     * method to extract the list items from a page
     * @param page
     * @return
     */
    public String getCaloriesFromPage(android.content.Context context, String page) {
        int end = page.indexOf("<div class=\"tab-label\">Calories</div>");
        String snippet = page.substring(0, end);
        int begin = snippet.lastIndexOf("<div class=\"tab-val\">") + 21;
        int last = snippet.lastIndexOf("</div>");
        String result = snippet.substring(begin, last).replace("\t","").replace("\n","");
        return result;
    }
}
