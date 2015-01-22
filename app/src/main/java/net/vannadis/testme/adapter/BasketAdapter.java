package net.vannadis.testme.adapter;

import android.content.Context;

import net.vannadis.testme.R;
import net.vannadis.testme.model.Bug;
import net.vannadis.testme.model.Product;

import java.util.ArrayList;

/**
 * Created by viktoriala on 9/24/2014.
 */
public class BasketAdapter extends BaseProductAdapter {

    public BasketAdapter(Context context, ArrayList<Product> data) {
        super(context, data, R.layout.item_basket);
    }

    @Override
    protected void onFunctionalButtonClick(Product product) {
        if (product.getBug().getType() == Bug.TYPE_NOT_DELETABLE){
            return;
        }
        if (listener != null) {
            listener.onProductClicked(product);
        }
    }
}
