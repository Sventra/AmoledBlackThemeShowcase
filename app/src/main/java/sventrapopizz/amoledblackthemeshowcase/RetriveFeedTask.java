package sventrapopizz.amoledblackthemeshowcase;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class RetriveFeedTask extends AsyncTask {
    private static final String TAG = "InitialString";
    public String stringa2;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Document doc = Jsoup.connect("https://telegram.dog/amoledblackthemeshowcasesupport/8?embed=1").get();
            String stringa2 = doc.select("div.tgme_widget_message_text").text();
            return stringa2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringa2;
    }
}
