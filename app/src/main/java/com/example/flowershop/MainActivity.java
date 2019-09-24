package com.example.flowershop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;

    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.nameEditText);

      createSpinner();

      createMap();
    }
    void createSpinner(){
        spinner = findViewById(R.id.spinner);//далее, чтобы наполнить spinner элементами,их нужно поместить
        //в ArrayList
        spinner.setOnItemSelectedListener(this);//установила слушатель значений, чтобы наш класс прослушивал события этого спиннера

        spinnerArrayList = new ArrayList();
        spinnerArrayList.add("Cactus");
        spinnerArrayList.add("Orchid");
        spinnerArrayList.add("Rose");

        spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//устанавливаю выпадающий список
        spinner.setAdapter(spinnerAdapter);

    }
     void createMap(){
         goodsMap = new HashMap();
         goodsMap.put("Cactus",12.0);
         goodsMap.put("Orchid",36.0);
         goodsMap.put("Rose",24.0);//далее имплементирую новый интерфейс-прослушиватель OnItemsSelectedListener

    }

    public void increaseQuantity(View view) {
        quantity = quantity + 1;

        TextView quantityTextView = findViewById(R.id.quantityTextView);// связываю кнопку + с 0, чтобы добавлять в карзину предмет
        quantityTextView.setText("" + quantity);//т.к. метод setText принимае в качестве типа
        // только строки  произвожу конкатенацию,т.е. привожу int к String
        TextView priceTextView = findViewById(R.id.priceTextView);//чтобы при нажатии на кнопку + цена увеличивалась
        priceTextView.setText("" + quantity * price);
    }

    public void decreaseQuantity(View view) {//этот метод из minusButton определяет минус одну ед.товара
        quantity = quantity - 1;
        if(quantity < 0){//если пошли отрицат цифры то они =0
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);// чтобы при нажатии на кнопку - цуна уменьшалась
        priceTextView.setText("" + quantity * price);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
     goodsName = spinner.getSelectedItem().toString();//знач-е кот. выбранно в спинере в данный момент
        price = (double)goodsMap.get(goodsName);//goodsMap(ключ), goodsName(значение)
        TextView priceTextView = findViewById(R.id.priceTextView);//связываю с кнопкой 0$ (там где находиться цена)
        priceTextView.setText(""+quantity * price);//значение для кнопки 0$ устанавливаю след.: колво.товара * на цену

        ImageView goodsImageView = findViewById(R.id.goodsImageView);//нахожу ImageView в кот. расположен кактус

 /*       if(goodsName.equals("Cactus")){//1)картинки в спиннере можно менять так
            goodsImageView.setImageResource(R.drawable.cactus);
        }else if(goodsName.equals("Orchid")){
            goodsImageView.setImageResource(R.drawable.orchid);
        }else if(goodsName.equals("Rose")){
            goodsImageView.setImageResource(R.drawable.rose);
        }*/
        switch (goodsName){//2)или так
            case "Cactus":
                goodsImageView.setImageResource(R.drawable.cactus);
                break;
            case "Orchid":
                goodsImageView.setImageResource(R.drawable.orchid);
                break;
            case "Rose":
                goodsImageView.setImageResource(R.drawable.rose);
                break;
                default:
                    goodsImageView.setImageResource(R.drawable.rose);
                    break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addToCard(View view) {
        Order order = new Order();

        order.userName = userNameEditText.getText().toString();
        Log.d(TAG, "addToCard: " + order.userName);

        order.goodsName = goodsName;
        Log.d(TAG, "goodsName:" + order.goodsName);

        order.quantity = quantity;
        Log.d(TAG, "quantity: " + order.quantity);

        order.orderPrice = quantity * price;
        Log.d(TAG, "orderPrice: " + order.orderPrice);

        order.price = price;

        Intent orderIntent = new Intent(MainActivity.this,OrderActivity.class);//этим кодом передам
        //в конструктор интента то, что я вызываю класс OrderActivity из MainActivity (т.е. из этой активити)
        //далее передам данные userName,goodsName,quantity,orderPrice в OrderActivity, помещу их ниже
        orderIntent.putExtra("userNameForIntent",order.userName);
        orderIntent.putExtra("goodsNameForIntent",order.goodsName);
        orderIntent.putExtra("quantityIntent",order.quantity);
        orderIntent.putExtra("priceIntent",order.price);
        orderIntent.putExtra("orderPriceIntent",order.orderPrice);

        startActivity(orderIntent);


    }
}
