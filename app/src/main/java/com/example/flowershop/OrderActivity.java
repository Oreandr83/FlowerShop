package com.example.flowershop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    String[] addresses = {"oreandr83@mail.ru"};
    String subject = "Order from Flower Shop";
    String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent receivedOrderIntent = getIntent();//возвращает нам  интент из MainActivity(т.е.тот интент при помощи кот.была запущена
        //эта активити)
        //по ключу извлекаю значение из интента MainActivity.class
        String userName = receivedOrderIntent.getStringExtra("userNameForIntent");
        String goodsName = receivedOrderIntent.getStringExtra("goodsNameForIntent");
        int quantity = receivedOrderIntent.getIntExtra("quantityIntent",  0);
        double price = receivedOrderIntent.getDoubleExtra("priceIntent",0);
        double orderPrice = receivedOrderIntent.getDoubleExtra("orderPriceIntent", 0 );


        emailText = "Customer name: " + userName +"\n"
                +"Goods name: " + goodsName +"\n"
                + "Quantity: " + quantity +"\n"
                +"Price: "+ price + "\n"
                + "Order price: "+ orderPrice;
        TextView orderTextView = findViewById(R.id.orderTextView);
        orderTextView.setText(emailText);

    }

    public void submitOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,emailText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
