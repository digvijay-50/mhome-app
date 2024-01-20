package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.Activities.SplashActivity.disablefor1sec;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.propertybuysell.mhome.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebviewActivity extends AppCompatActivity {

    String url;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        pdfView=findViewById(R.id.pdfView);
        ImageView backic = findViewById(R.id.backic);

        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("");


        url=getIntent().getStringExtra("url");

        Log.i("onCreate", "onCreate: "+url);

        WebView webView = findViewById(R.id.web);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

//        webView.loadUrl(url);
//        pdfView.fromUri(Uri.parse(url)).load();
//        new RetrievePDFfromUrl().execute(url);
    }

    class RetrievePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }

}