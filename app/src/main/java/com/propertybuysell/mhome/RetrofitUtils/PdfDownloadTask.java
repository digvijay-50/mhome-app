package com.propertybuysell.mhome.RetrofitUtils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfDownloadTask extends AsyncTask<Void, Void, Boolean> {
    private String url;
    private String fileName;

    public PdfDownloadTask(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            URL pdfURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) pdfURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            InputStream input = new BufferedInputStream(connection.getInputStream());
            File outputDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            );
            File outputFile = new File(outputDir, fileName);

            FileOutputStream output = new FileOutputStream(outputFile);
            byte[] data = new byte[1024];
            int count;
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            Log.i("checksuccess", "onPostExecutesuccess: ");
            // PDF download was successful, you can open the PDF file here
        } else {
            Log.i("checksuccess", "onPostExecutefail: ");
            // PDF download failed
        }
    }
}
