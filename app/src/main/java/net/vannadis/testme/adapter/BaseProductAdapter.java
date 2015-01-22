package net.vannadis.testme.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import net.vannadis.testme.R;
import net.vannadis.testme.exception.TestMeException;
import net.vannadis.testme.interfaces.OnProductClickListener;
import net.vannadis.testme.model.Bug;
import net.vannadis.testme.model.Product;

import java.util.ArrayList;

/**
 * Created by viktoriala on 9/24/2014.
 */
public abstract class BaseProductAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Product> data;
    protected OnProductClickListener listener;
    private int resListRowId;

    public BaseProductAdapter(Context context, ArrayList<Product> data, int resListRowId) {
        this.context = context;
        this.resListRowId = resListRowId;
        if (context instanceof OnProductClickListener) {
            listener = (OnProductClickListener) context;
        }
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(resListRowId, null);
        }
        final Product product = data.get(position);

        Button productImageBtn = (Button) view.findViewById(R.id.product_btn);
        productImageBtn.setText(String.valueOf(position + 1));
        productImageBtn.setBackgroundColor(product.getBgImageColor());

        TextView productDescription = (TextView) view.findViewById(R.id.product_descr_tv);
        productDescription.setText(product.getDescription());

        TextView productPrice = (TextView) view.findViewById(R.id.product_price_tv);
        productPrice.setText(Html.fromHtml(context.getString(R.string.product_price, product.getPrice())));

        Button addProductBtn = (Button) view.findViewById(R.id.action_btn);
        addProductBtn.setTag(product);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = (Product) view.getTag();
                onFunctionalButtonClick(product);
            }
        });
        return view;
    }

    protected abstract void onFunctionalButtonClick(Product product);


}