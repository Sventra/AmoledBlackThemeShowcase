package sventrapopizz.amoledblackthemeshowcase;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RetriveFeedTask2 extends AsyncTask {
    private static final String TAG = "AppVersion";
    public String version;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Document doc = Jsoup.connect("https://telegram.dog/amoledblackthemeshowcasesupport/11?embed=1").get();
            String version = doc.select("div.tgme_widget_message_text").text();
            return version;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }
}
