package com.blood.blooddonation;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class BecomeDonor extends AppCompatActivity {

    ImageView aposi, anega, bnega, bposi, oposi, onega, abposi, abnega, male, female;
    int i = 1, j = 1;
    boolean ma = false, fe = false, sel = false;
    EditText name, age, phone;
    TextView city;
    String gender, bloodgroup, visibility_str;
    CheckBox visibility;
    DatePickerDialog.OnDateSetListener dateSetListener;
    String dat, personalemail;
    double lat;
    double lon;
    Location location;
    private LocationManager locationManager;
    int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        setContentView(R.layout.activity_become_donor);

        PlacesAutocompleteTextView placesAutocompleteTextView;

        placesAutocompleteTextView = findViewById(R.id.places_autocomplete);
        placesAutocompleteTextView.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String pla = place.description;
                Log.i("JSON", pla);
            }
        });
        aposi = findViewById(R.id.apos);
        anega = findViewById(R.id.aneg);
        bnega = findViewById(R.id.bneg);
        bposi = findViewById(R.id.bpos);
        oposi = findViewById(R.id.opos);
        onega = findViewById(R.id.oneg);
        abposi = findViewById(R.id.abpos);
        abnega = findViewById(R.id.abneg);
        male = findViewById(R.id.imageView);
        female = findViewById(R.id.imageView2);
        // city = findViewById(R.id.city);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(BecomeDonor.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

        ImageButton imageButton = findViewById(R.id.back_donor);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

//        city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("JSON","REached");
//                    get_city();
//            }
//        });

        //Edittext definition
        name = findViewById(R.id.editText);
        //city = findViewById(R.id.city);
        age = findViewById(R.id.age_donor);
        phone = findViewById(R.id.phonenumber);

        //Check box
        visibility = findViewById(R.id.checkBox2);

        final TextView date;
        date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(BecomeDonor.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, dateSetListener, year, month, date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                dat = year + "/" + month + "/" + dayOfMonth;
                date.setText(dat);
            }
        };

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    male.setImageResource(R.drawable.cman);
                    i = 0;
                    ma = true;
                    gender = "male";
                } else if (i == 0) {
                    male.setImageResource(R.drawable.cman);
                    ma = true;
                    female.setImageResource(R.drawable.woman);
                }

            }
        });


        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    female.setImageResource(R.drawable.cwomen);
                    fe = true;
                    gender = "female";
                    i = 0;
                } else if (i == 0) {
                    female.setImageResource(R.drawable.cwomen);
                    fe = true;
                    male.setImageResource(R.drawable.man1);

                }
            }
        });

        aposi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (j == 1 || sel) {
                    aposi.setImageResource(R.drawable.cgroup310);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    anega.setImageResource(R.drawable.group311);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);

                    bloodgroup = "A+";
                } else {
                    sel = false;
                    aposi.setImageResource(R.drawable.group310);
                    j = 1;
                }
            }
        });

        anega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anega.setImageResource(R.drawable.cgroup311);
                if (j == 1 || sel) {
                    anega.setImageResource(R.drawable.cgroup311);

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);

                    j = 0;
                    bloodgroup = "A-";
                } else {

                    sel = false;
                    anega.setImageResource(R.drawable.group311);
                    j = 1;
                }
            }
        });

        bnega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnega.setImageResource(R.drawable.cgroup312);
                if (j == 1 || sel) {
                    bnega.setImageResource(R.drawable.cgroup312);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    anega.setImageResource(R.drawable.group311);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);

                    bloodgroup = "B-";
                } else {
                    sel = false;
                    bnega.setImageResource(R.drawable.group312);
                    j = 1;
                    bloodgroup = null;
                }
            }
        });

        bposi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bposi.setImageResource(R.drawable.cgroup319);
                if (j == 1 || sel) {
                    bposi.setImageResource(R.drawable.cgroup319);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    anega.setImageResource(R.drawable.group311);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);
                    bloodgroup = "B+";
                } else {
                    sel = false;
                    bposi.setImageResource(R.drawable.group319);
                    j = 1;
                    bloodgroup = null;
                }
            }
        });

        oposi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oposi.setImageResource(R.drawable.cgroup313);
                if (j == 1 || sel) {
                    oposi.setImageResource(R.drawable.cgroup313);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    anega.setImageResource(R.drawable.group311);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);

                    bloodgroup = "O+";
                } else {

                    sel = false;
                    oposi.setImageResource(R.drawable.group313);
                    j = 1;
                    bloodgroup = null;
                }
            }
        });

        onega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onega.setImageResource(R.drawable.cgroup318);
                if (j == 1 || sel) {
                    onega.setImageResource(R.drawable.cgroup318);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    anega.setImageResource(R.drawable.group311);
                    abposi.setImageResource(R.drawable.group317);
                    abnega.setImageResource(R.drawable.group316);

                    bloodgroup = "O-";
                } else {

                    sel = false;
                    onega.setImageResource(R.drawable.group318);
                    j = 1;
                    bloodgroup = null;
                }
            }
        });

        abposi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abposi.setImageResource(R.drawable.cgroup317);
                if (j == 1 || sel) {
                    abposi.setImageResource(R.drawable.cgroup317);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    anega.setImageResource(R.drawable.group311);
                    abnega.setImageResource(R.drawable.group316);

                    bloodgroup = "AB+";
                } else {

                    sel = false;
                    abposi.setImageResource(R.drawable.group317);
                    j = 1;
                    bloodgroup = null;
                }
            }
        });

        abnega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abnega.setImageResource(R.drawable.cgroup316);
                if (j == 1 || sel) {
                    abnega.setImageResource(R.drawable.cgroup316);
                    j = 0;

                    sel = true;
                    //Set all other to blank
                    aposi.setImageResource(R.drawable.group310);
                    bnega.setImageResource(R.drawable.group312);
                    bposi.setImageResource(R.drawable.group319);
                    oposi.setImageResource(R.drawable.group313);
                    onega.setImageResource(R.drawable.group318);
                    abposi.setImageResource(R.drawable.group317);
                    anega.setImageResource(R.drawable.group311);

                    bloodgroup = "AB-";
                } else {
                    sel = false;
                    abnega.setImageResource(R.drawable.group316);
                    j = 1;
                }
            }
        });


        Button sub_button = findViewById(R.id.button);
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility.isChecked())
                    visibility_str = "checked";
                else
                    visibility_str = "unchecked";

                addPost(name.getText().toString(), city.getText().toString(), gender, bloodgroup, visibility_str);
            }
        });
    }

    public void back(View view) {
        finish();
    }

//    private void requestPermissions() {
//
//        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission Needed")
//                    .setMessage("This permission is needed to fetch your current city")
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(BecomeDonor.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        }
//        else{
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
//        }
//    }

    private void get_city() {
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(lat, lon, 1);
            String cit = addresses.get(0).getLocality();
            city.setText(cit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addPost(final String name, final String email, final String gender, final String bloodgroup, final String visibility_str) {


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        final RequestParams params = new RequestParams();

        if (account != null) {

            personalemail = account.getEmail();
            Log.i("JSON", name + " " + personalemail + " " + gender + " " + bloodgroup + " " + visibility_str + " " + age.getText().toString() + " " + phone.getText().toString() + " " + dat);
            params.put("name", name);
            params.put("mobile", phone.getText().toString());
            params.put("donor", "yes");
            params.put("visibility", visibility_str);
            params.put("gender", gender);
            params.put("email", personalemail);
            params.put("age", age.getText().toString());
            params.put("city", city.getText().toString());
            params.put("bloodgrp", bloodgroup);
            params.put("area", "area");
            params.put("lastdonated", dat);

            String donor_url = "https://bloodtransfer.herokuapp.com/index.php/data/save";

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(donor_url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("JSON", "JSON is " + response.toString());
                    Log.i("JSON", "Status  code" + statusCode);
                    Log.i("JSON", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getApplicationContext(), "Request Failed:(", Toast.LENGTH_SHORT).show();
                    Log.i("JSON", "Status code" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Request Success:)", Toast.LENGTH_SHORT).show();
                    Log.i("JSON", "Status  code" + statusCode);
                    Log.i("JSON", responseString);
                }
            });

        } else {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {

                                if (object.has("email")) {
                                    personalemail = object.getString("email");
                                    Log.i("JSON", name + " " + personalemail + " " + gender + " " + bloodgroup + " " + visibility_str + " " + age.getText().toString() + " " + phone.getText().toString() + " " + dat);
                                    params.put("name", name);
                                    params.put("mobile", phone.getText().toString());
                                    params.put("donor", "yes");
                                    params.put("visibility", visibility_str);
                                    params.put("gender", gender);
                                    params.put("email", personalemail);
                                    params.put("age", age.getText().toString());
                                    params.put("city", "Hyderabad");
                                    params.put("bloodgrp", bloodgroup);
                                    params.put("area", "area");
                                    params.put("lastdonated", dat);

                                    Log.i("JSON", "Has email" + personalemail);
                                    String donor_url = "https://bloodtransfer.herokuapp.com/index.php/data/save";

                                    AsyncHttpClient client = new AsyncHttpClient();
                                    client.post(donor_url, params, new JsonHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            Log.i("JSON", "JSON is " + response.toString());
                                            Log.i("JSON", "Status  code" + statusCode);
                                            Log.i("JSON", response.toString());
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                            Toast.makeText(getApplicationContext(), "Request Failed:(", Toast.LENGTH_SHORT).show();
                                            Log.i("JSON", "Status code" + statusCode);
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            Toast.makeText(getApplicationContext(), "Request Failed:(", Toast.LENGTH_SHORT).show();
                                            Log.i("JSON", "Status  code" + statusCode);
                                            Log.i("JSON", responseString);
                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "error in name", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
            request.setParameters(parameters);
            request.executeAsync();
        }
        Log.i("JSON", "reached addPost method");


    }


}