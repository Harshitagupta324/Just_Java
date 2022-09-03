package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int Quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view) {
        if(Quantity == 100){
            Toast.makeText(this, "YOU CANNOT ORDER MORE THAN 100 COFFEES", Toast.LENGTH_SHORT).show();
            return;
        }
        Quantity = Quantity + 1;
        display(Quantity);
    }

    public void decrement(View view) {
        if(Quantity == 1){
            Toast.makeText(this, "YOU CANNOT ORDER LESS THAN ONE COFFEE", Toast.LENGTH_SHORT).show();
            return;
        }
        Quantity = Quantity - 1;
        display(Quantity);
    }

    public void submitOrder(View View) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String Name = nameField.getText().toString();
        Log.v("MainActivity", "Name" + Name);

        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has Whipped Cream:" + hasWhippedCream);

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        Log.v("MainActivity", "Has Chocolate:" + hasChocolate);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary( Name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);
    }
    public void onClick(View view) {
        String priceMessage = "Your order is booked!\n Please visit Again.";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        EditText phoneNumber = (EditText) findViewById(R.id.phone_number);
        Button send = (Button) findViewById(R.id.button);
        intent.setData(Uri.parse("smsto:" + phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body", priceMessage);
        startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        EditText phonenumber = (EditText) findViewById(R.id.phone_number)
//            String number=phonenumber.getText().toString();
//            String msg=priceMessage.getText().toString();
//            try {
//                SmsManager smsManager=SmsManager.getDefault();
//                smsManager.sendTextMessage(number,null,msg,null,null);
//                Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
//            }catch (Exception e)
//            {
//                Toast.makeText(getApplicationContext(),"Some fiedls is Empty",Toast.LENGTH_LONG).show();
//            }
    }



    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if(addChocolate){
            basePrice =  basePrice+ 2;
        }
        int price = Quantity * basePrice;
        return price;
    }

    private String createOrderSummary(String Name, int price, boolean addWhippedCream, boolean addChocolate ){
        String priceMessage ="Name:" + Name;
        priceMessage += "\nAdd Whipped Cream?" + addWhippedCream;
        priceMessage += "\nAdd Chocolate?" + addChocolate;
        priceMessage += "\nQuantity " + Quantity;
        priceMessage +=  "\nTotal: $" + price;
        priceMessage +=  "\nThank You";
        return priceMessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}
