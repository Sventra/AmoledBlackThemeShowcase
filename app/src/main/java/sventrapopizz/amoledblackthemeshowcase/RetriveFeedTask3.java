package sventrapopizz.amoledblackthemeshowcase;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RetriveFeedTask3 extends AsyncTask {
    private static final String TAG = "tgxEnabled";
    public boolean tgxEnabled;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Document doc = Jsoup.connect("https://t.me/amoledblackthemeshowcasesupport/13?embed=1").get();
            String acquired = doc.select("div.tgme_widget_message_text").text();
            tgxEnabled = Boolean.valueOf(acquired);
            return tgxEnabled;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tgxEnabled;
    }
}
