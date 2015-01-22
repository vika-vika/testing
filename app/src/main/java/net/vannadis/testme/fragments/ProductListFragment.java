package net.vannadis.testme.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.vannadis.testme.BugsSettings;
import net.vannadis.testme.R;
import net.vannadis.testme.adapter.ProductsAdapter;
import net.vannadis.testme.exception.TestMeException;
import net.vannadis.testme.model.Bug;
import net.vannadis.testme.model.Product;

import java.util.ArrayList;

/**
 * Created by viktoriala on 9/22/2014.
 */
public class ProductListFragment extends Fragment implements AdapterView.OnItemLongClickListener {

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.products_list);
        ArrayList<Product> products = generateProductsList();

        list.setAdapter(new ProductsAdapter(this.getActivity(), products));
        list.setOnItemLongClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private ArrayList<Product> generateProductsList(){

        ArrayList<Product> products = new ArrayList<Product>();

        for(int i = 0; i < BugsSettings.PRODUCTS_COUNT ; i++) {

            String description = getString(R.string.product_description, i+1);
            int price = i+1;

            Product product = new Product(Product.generateRandomBgColor(), description, price);

            Bug bug = BugsSettings.productsBugs.get(price);
            if (bug != null) {
                product.setBug(bug);
            }
            products.add(product);

        }
        return products;
    }

    // BUG-testme: id 18
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        throw new TestMeException("On list long pressed");
    }
}
