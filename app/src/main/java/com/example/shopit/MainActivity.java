package com.example.shopit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
//import LayoutParams;
//import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE="com.example.shopit.Message";
//    private GestureDetectorCompat gestureDetectorCompat = null;
    private FusedLocationProviderClient client;
    String user_id;
    DatabaseHelper myDB;
    String username;
//    =new DatabaseHelper(this);
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        Button login_button = findViewById(R.id.main_page_login_button);
        Button signup_button = findViewById(R.id.main_page_signup_button);
        Button logout_button = findViewById(R.id.main_page_logout_button);
        Button addproduct_button = findViewById(R.id.main_page_add_prod_button);
        Button greeting = findViewById(R.id.main_page_greeting);
        String query="select * from products";
        String search="";



        if (Session.getusername(this)==null){
            login_button.setVisibility(View.VISIBLE);
            signup_button.setVisibility(View.VISIBLE);
            logout_button.setVisibility(View.GONE);

        }
        else{
            login_button.setVisibility(View.INVISIBLE);
            signup_button.setVisibility(View.GONE);
            logout_button.setVisibility(View.VISIBLE);
            greeting.setVisibility(View.VISIBLE);
//            addproduct_button.setVisibility(View.VISIBLE);

            greeting.setText("HI "+Session.getusername(this)+" !");
            if (Session.getusername(this).equals("admin")){
//                addProducts
                addproduct_button.setVisibility(View.VISIBLE);


            }
        }

        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        if(extras!=null){

            query=intent.getExtras().getString("search");
//            search=intent.getExtras().getString("val");
        }

        displayproducts(username,query,search);





//        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
//        gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
//        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);


//        DisplayMessageActivity disp = new DisplayMessageActivity();

//        disp.showMessage(this,"HI",Session.getusername(this));


//        client = LoctionServices.getFusedLocationProviderClient(this);















    }
//    public boolean onTouchEvent(MotionEvent event) {
//        // Pass activity on touch event to the gesture detector.
//        gestureDetectorCompat.onTouchEvent(event);
//        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
//        return true;
//    }




    public void sendMessage(View view){
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.search_text);
        String message=editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message );
        startActivity(intent);
    }
    public void goToLoginPage(View view){
        Intent intent = new Intent(this,LoginPage.class);
        startActivity(intent);
    }
    public void goToSignupPage(View view){
        Intent intent = new Intent(this,SignupPage.class);
        startActivity(intent);
    }
    public void showMenu(View view){
        showMenu();
    }
    public void showMenu(){
        ConstraintLayout mymenu = findViewById(R.id.my_menu);
        mymenu.setVisibility(View.VISIBLE);
    }

    public void hideMenu(){
        ConstraintLayout mymenu = findViewById(R.id.my_menu);
        mymenu.setVisibility(View.INVISIBLE);
    }
    public void hideMenu(View view){
        hideMenu();
    }
    public void addButton(View view){
        LinearLayout mymenu = (LinearLayout) findViewById(R.id.my_menu);
        Button button= new Button(this);

        String username=Session.getusername(this);
        button.setText("HI "+username);
        mymenu.addView(button);

    }
    public void logoutUser(View view){
        Session.endsession(this);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void gotoaddProductpage(View view){
        Intent intent =new Intent(this,AddProduct.class)    ;
        startActivity(intent);
    }
    public void search_product(View view){
        TextView searchbox=findViewById(R.id.search_text);
        String search = searchbox.getText().toString()+"'";
        String query="select * from products where prod_name ='"+search;
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("search",query);
//        intent.putExtra("val",search);
        startActivity(intent);

    }
    void displayproducts(String username,String query,String search){


        myDB = new DatabaseHelper(this);
        final Cursor res= myDB.ShowProducts(query,search);
        while(res.moveToNext()){




            LinearLayout prod_layout = (LinearLayout) findViewById(R.id.prod_layout);

            LinearLayout single_product =new LinearLayout(this);
            LinearLayout prod_content=new LinearLayout(this);
            LinearLayout prod_contents=new LinearLayout(this);
            ImageView prod_image=new ImageView(this);



//        LayoutParams params1;

            single_product.setOrientation(LinearLayout.VERTICAL);
            prod_content.setOrientation(LinearLayout.HORIZONTAL);
            prod_contents.setOrientation(LinearLayout.VERTICAL);

//        single_product.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));



            final TextView prod_name=new TextView(this);
            TextView prod_description=new TextView((this));
            TextView prod_price = new TextView(this);




            final int prod_id=Integer.parseInt(res.getString(res.getColumnIndex("prod_id")));

            final int prod_price_val=Integer.parseInt(res.getString(res.getColumnIndex("prod_price")));


            prod_price.setText("₹ "+Integer.toString(prod_price_val));
            final String prod_name_val=res.getString(res.getColumnIndex("prod_name"));
            prod_name.setText(prod_name_val);
            prod_description.setText(res.getString(res.getColumnIndex("prod_description")));
            byte[] prod_img=res.getBlob(res.getColumnIndex("prod_image"));

            prod_image.setImageBitmap(BitmapFactory.decodeByteArray(prod_img, 0, prod_img.length));
//


            single_product.addView(prod_name);

            single_product.addView(prod_content);
            prod_content.addView(prod_image);

            prod_content.addView(prod_contents);
            prod_contents.addView(prod_description);
            prod_contents.addView(prod_price);


            prod_layout.addView(single_product);

            Button button= new Button(this);
            button.setText("BUY");
            button.setBackgroundColor(Color.BLACK);
            button.setTextColor(Color.WHITE);

            single_product.addView(button);
//            prod_image.getLayoutParams().width=300;
//
            button.setWidth(50);

            android.view.ViewGroup.LayoutParams layoutParams = prod_image.getLayoutParams();
            layoutParams.width = 500;
            layoutParams.height = 500;
            prod_image.setLayoutParams(layoutParams);
            prod_name.setGravity(Gravity.CENTER);
//            single_product.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);


//            user_id=Session.getuserId(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click



//                    String username=Session.getusername(MainActivity.this);
                    if (Session.getusername(MainActivity.this)!=null) {
                        Intent orderintent = new Intent(getBaseContext(), OrderProduct.class);
                        orderintent.putExtra("prod_id", prod_id);
                        orderintent.putExtra("prod_name", prod_name_val);
//                    orderintent.putExtra("user_name",username);
                        orderintent.putExtra("prod_price", prod_price_val);
//                    orderintent.putExtra("user_id",user_id);
                        // currentContext.startActivity(activityChangeIntent);

                        startActivity(orderintent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this,LoginPage.class);
                        startActivity(intent);

                    }
                }
            });
        }


    }


}






