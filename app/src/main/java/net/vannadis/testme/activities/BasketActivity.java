package net.vannadis.testme.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.vannadis.testme.R;
import net.vannadis.testme.adapter.BasketAdapter;
import net.vannadis.testme.exception.TestMeException;
import net.vannadis.testme.interfaces.OnProductClickListener;
import net.vannadis.testme.model.Bug;
import net.vannadis.testme.model.Product;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by viktoriala on 9/24/2014.
 */
public class BasketActivity extends Activity implements OnProductClickListener {

    private ListView basketList;
    private ArrayList<Product> products;
    private BaseAdapter adapter;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        products = getIntent().getParcelableArrayListExtra("products");
        adapter = new BasketAdapter(this, products);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout basketFooter = (LinearLayout) inflater.inflate(R.layout.basket_list_footer, null);

        total = (TextView) findViewById(R.id.basket_total_tv);
        total.setText(Html.fromHtml(getString(R.string.basket_total, calculateTotal(products))));

        basketList = (ListView) findViewById(R.id.basket_list);
        basketList.addFooterView(basketFooter);
        basketList.setAdapter(adapter);

        setTitle(getTitle() + " - Complete the order");
    }

    private int calculateTotal(ArrayList<Product> products){
        int total = 0;
        for (Product product: products) {
            total += product.getPrice();
        }
        return total;
    }

    public void onClearAllClick(View v) {
        addBugNotClearAll();
        adapter.notifyDataSetChanged();
        total.setText(Html.fromHtml(getString(R.string.basket_total, 0)));
        disableButtons();
    }

    public void onBuyClick(View v) {
        showCompleteOrderDialog();
    }

    @Override
    public void onProductClicked(Product product) {
        products.remove(product);
        adapter.notifyDataSetChanged();
        total.setText(Html.fromHtml(getString(R.string.basket_total, calculateTotal(products))));

        if (products.isEmpty()) {
            disableButtons();
        }
    }

    private void disableButtons(){
        Button buyBtn = (Button) findViewById(R.id.basket_buy_btn);
        Button clearBtn = (Button) findViewById(R.id.basket_clear_btn);
        buyBtn.setEnabled(false);
        clearBtn.setEnabled(false);
    }

    private void showCompleteOrderDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Order confirmed");
        builder1.setMessage("Your order is now complete, thank you for your purchase! ");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addCrashNoWiFi();// BUG-testme: id 13

                        dialog.cancel();
                        BasketActivity.this.setResult(RESULT_OK);
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    };

    private void addCrashNoWiFi(){
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            throw new TestMeException("Wi-Fi is turned off on Buy Click");

        }
    }

    private void addBugNotClearAll(){

        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next(); // must be called before you can call i.remove()
            if (product.getBug().getType() != Bug.TYPE_NOT_DELETABLE_CLEAR_ALL) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("products", products);
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
    }
}
