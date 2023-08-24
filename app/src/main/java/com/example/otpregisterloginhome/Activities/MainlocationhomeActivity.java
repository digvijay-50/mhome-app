package com.example.otpregisterloginhome.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.otpregisterloginhome.OtherUtils.AppController;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.placeapi.GoEditText;
import com.example.otpregisterloginhome.placeapi.PlaceJSONParser;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainlocationhomeActivity extends AppCompatActivity {
    GoEditText destaddtv;
    PlacesTask placesTask;
    ParserTask parserTask;
    SavePref savePref;
    Boolean locationchanged = false;
    TextView tv_saveandproceed, response_tv;
    Double dLatitude, dLongitude;
    ImageView iv_edit;
    String type = "1";


    String pincode;

    String justmovedlat = "00";
    String justmovedlong = "00";
    String newLat = "";
    String newLong = "";
    String addStr = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        try {
            savePref = new SavePref(this);
            justmovedlat = savePref.getLocationLattHome();
            justmovedlong = savePref.getLocationLonggHome();
            addStr = getAddress(Double.parseDouble(savePref.getLocationLattHome()),
                    Double.parseDouble(savePref.getLocationLonggHome()));
            newLat = savePref.getLocationLattHome();
            newLong = savePref.getLocationLonggHome();
            iv_edit = findViewById(R.id.iv_edit);
            response_tv = findViewById(R.id.response);
            tv_saveandproceed = findViewById(R.id.tv_saveandproceed);

            destaddtv = findViewById(R.id.pickuplocationet);


            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        destaddtv.setText("");
                        destaddtv.setHint("My Current Location");
                        locationchanged = false;
                    } catch (Exception e) {

                    }

                }
            });


            try {
                if (savePref.getLocationLattHome().equalsIgnoreCase("")) {

                } else {
                    try {
                        destaddtv.setText(getAddress(Double.parseDouble(savePref.getLocationLattHome()), Double.parseDouble(savePref.getLocationLonggHome())));

                    } catch (Exception e) {

                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            destaddtv.setThreshold(1);
            destaddtv.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    String s1 = s.toString();
                    s1 = s1.replace(" ", "+");

                    placesTask = new PlacesTask();
                    placesTask.execute(s1.toString());


                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
//            https:maps.googleapis.com/maps/api/place/autocomplete/
            destaddtv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int action, KeyEvent event) {
                    if (action == EditorInfo.IME_ACTION_DONE || action == EditorInfo.IME_ACTION_NEXT || action == EditorInfo.IME_ACTION_UNSPECIFIED) {
                        //hide the keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        return true;
                    } else {
                        return false;
                    }
                }
            });


            tv_saveandproceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savePref.setAddress(destaddtv.getText().toString());
                    Intent i = new Intent(MainlocationhomeActivity.this,NewHomeActivity.class);
                    i.putExtra("address", destaddtv.getText().toString());
                    startActivity(i);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    public String getAddress(double lat, double lng) {
        String addre = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            String postalCode = addresses.get(0).getPostalCode();
            pincode = postalCode;
            addre = add;
            dLatitude = lat;
            dLongitude = lng;
            newLat = dLatitude + "";
            newLong = dLongitude + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addre;
    }


    public String getPincodeAdd(double lat, double lng) {

        String addre = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String postalCode = addresses.get(0).getPostalCode();
            addre = postalCode;
            dLatitude = lat;
            dLongitude = lng;
            newLat = dLatitude + "";
            newLong = dLongitude + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addre;
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);

            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public String doubleToString(Double d) {
        double d1 = d;
        DecimalFormat f = new DecimalFormat("##.00000");
        String sd1 = f.format(d1);
        return sd1;


    }

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {

            String data = "";

            String key = "key=AIzaSyDY_U0BMEIZb4Pm3rU3T7dnxi-fXPfZlqQ";
            String locationn = "location=" + savePref.getLocationLattHome() + "," + savePref.getLocationLonggHome();
            String input = "";
            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            String sensor = "sensor=true";

            String radius = "radius=500";


            String parameters = input + "&" + sensor + "&" + key + "&" + locationn + "&" + radius;

            String output = "json";


            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;

            Log.i("doInBackground", "doInBackground: " + url);
            try {
                data = downloadUrl(url);
            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJsonParser.parse(jObject);


            } catch (Exception e) {

            }
            return places;
        }


        @Override
        protected void onPostExecute(final List<HashMap<String, String>> result) {


            String[] from = new String[]{"description"};
            final int[] to = new int[]{android.R.id.text1};

            SimpleAdapter adapter = null;
            try {
                adapter = new SimpleAdapter(MainlocationhomeActivity.this, result, android.R.layout.simple_list_item_1, from, to);
                destaddtv.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }


            adapter.notifyDataSetChanged();


            destaddtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        destaddtv.showDropDown();
                    }
                }
            });


            destaddtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    Toast.makeText(MainSelectlocationActivity.this, "select"+result, Toast.LENGTH_SHORT).show();
                    destaddtv.setText(result.get(position).get("description"));
                    String placeid = result.get(position).get("reference");


                    addStr = result.get(position).get("description");


                    LatLng latLng = getLocationFromAddress(MainlocationhomeActivity.this, result.get(position).get("description"));

                    pincode = getPincodeAdd(latLng.latitude, latLng.longitude);


                    justmovedlat = latLng.latitude + "";
                    justmovedlong = latLng.longitude + "";


                    getLatLongintoMap(placeid);

                }

                private void getLatLongintoMap(String placeid) {


                    StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                            "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=AIzaSyDY_U0BMEIZb4Pm3rU3T7dnxi-fXPfZlqQ", new Response.Listener<String>() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject result = jsonObject.getJSONObject("result");
                                JSONObject geometry = result.getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                String lat = location.getDouble("lat") + "";
                                String lng = location.getDouble("lng") + "";


                                newLat = lat + "";
                                newLong = lng + "";

                                savePref.setLocationLonggHome(newLong);
                                savePref.setLocationLattHome(newLat);


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    AppController.getInstance().addToRequestQueue(jsonObjReq);


                }
            });

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
