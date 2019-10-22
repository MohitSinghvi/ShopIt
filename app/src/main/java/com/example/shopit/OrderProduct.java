package com.example.shopit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
//import android.loc

public class OrderProduct extends AppCompatActivity {


    private FusedLocationProviderClient client;
    DatabaseHelper myDB;
    String order_location ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        String username = Session.getusername(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            String username = extras.getString("username");
            String prod_id = extras.getString("prod_id");
            //The key argument here must match that used in the other activity
        }

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        Button button = findViewById(R.id.mapbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {

                    return;
                }



                client.getLastLocation().addOnSuccessListener(OrderProduct.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location!=null){
                            TextView textView = findViewById(R.id.coordinates);
                            textView.setText(location.toString());
                            order_location=location.toString();
                        }

                    }
                });
            }
        });

        Intent intent = getIntent();
        String prod_name=intent.getExtras().getString("prod_name");
        int prod_id=intent.getExtras().getInt("prod_id");




        int prod_price=intent.getExtras().getInt("prod_price");


        TextView prod_name_view=findViewById(R.id.prod_name);
        TextView prod_price_view=findViewById(R.id.prod_price);

        prod_name_view.setText(prod_name);
        prod_price_view.setText(Integer.toString(prod_price));







    }
    public void placeOrder(View view){

        String username = Session.getusername(this);

        DisplayMessageActivity disp=new DisplayMessageActivity();
        myDB = new DatabaseHelper(this);
        Intent intent = getIntent();
//        String prod_name=intent.getExtras().getString("prod_name");
        int prod_id=intent.getExtras().getInt("prod_id");
//        String user_id = intent.getExtras().getString("user_id");

//        int prod_price=intent.getExtras().getInt("prod_price");
//        String user_name=intent.getExtras().getString("username");


        if(myDB.insertOrder(username,prod_id,order_location)){
            disp.showMessage(this,"Order Successful","Your Order has been placed successfuly");
        }
        else{
            disp.showMessage(this,"Order Unsuccessful","Try again");
        }
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);

    }
//    public void goBack(View view){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

    }



}
