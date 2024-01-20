package com.propertybuysell.mhome.RetrofitUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfDownloader {

    public static void main(String[] args) {
        String fileURL = "https://example.com/sample.pdf"; // Replace with the actual PDF URL
        String saveDir = "/path/to/your/directory"; // Replace with the directory where you want to save the PDF

        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // Check if the response is OK (HTTP 200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Get the input stream from the connection
                InputStream inputStream = httpConn.getInputStream();
                String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

                // Create an output stream to save the PDF
                OutputStream outputStream = new FileOutputStream(saveDir + File.separator + fileName);

                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("PDF downloaded successfully.");
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
