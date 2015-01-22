package net.vannadis.testme.adapter;

import android.content.Context;

import net.vannadis.testme.R;
import net.vannadis.testme.exception.TestMeException;
import net.vannadis.testme.model.Bug;
import net.vannadis.testme.model.Product;

import java.util.ArrayList;

/**
 * Created by viktoriala on 9/23/2014.
 */
public class ProductsAdapter extends BaseProductAdapter {

    public ProductsAdapter(Context context, ArrayList<Product> data){
        super(context, data, R.layout.item_product);
    }

    @Override
    protected void onFunctionalButtonClick(Product product) {
        if (product.getBug().getType() == Bug.TYPE_CRASH) {
            throw new TestMeException("On ADD buggy product clicked: " + product.getPrice());
        } else if (product.getBug().getType() == Bug.TYPE_NOT_ADDABLE){

        } else if (listener != null){
            listener.onProductClicked(product);
        }
    }
}
