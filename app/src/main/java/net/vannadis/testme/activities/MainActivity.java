package net.vannadis.testme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import android.widget.Toast;

import net.vannadis.testme.R;
import net.vannadis.testme.fragments.HelpFragment;
import net.vannadis.testme.fragments.UserProfileFragment;
import net.vannadis.testme.fragments.NavigationDrawerFragment;
import net.vannadis.testme.fragments.ProductListFragment;
import net.vannadis.testme.fragments.RegistrationFragment;
import net.vannadis.testme.interfaces.OnProductClickListener;
import net.vannadis.testme.interfaces.RegistrationListener;
import net.vannadis.testme.model.Product;
import net.vannadis.testme.model.User;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnProductClickListener, RegistrationListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private ArrayList<Product> addedProducts = new ArrayList<Product>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp( R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position, final String title) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        mTitle = title;

        if (getString(R.string.title_product_list).equals(title)) {
            fragment = new ProductListFragment().newInstance();

        } else if (getString(R.string.title_registration).equals(title)) {
            fragment = new RegistrationFragment().newInstance();

        } else if (getString(R.string.title_user_profile).equals(title)) {
            fragment = new UserProfileFragment().newInstance(user);

        } else if (getString(R.string.title_help).equals(title)) {
            fragment = new HelpFragment().newInstance();
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onUserRegistered(User user) {
        this.user = user;
        mNavigationDrawerFragment.switchToMainScreen();
        mNavigationDrawerFragment.updateDrawerItems(user != null);

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //http://findicons.com/icon/203378/bug_buddy?id=204798
        /* {@link=http://findicons.com/icon/559050/bug} */
        actionBar.setLogo(R.drawable.bug);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductClicked(Product product) {
        if (addedProducts.contains(product)) {
            Toast.makeText(this, "Unable to add this product. You can add one unique product only: " + product.getPrice(), Toast.LENGTH_LONG).show();
        } else {
            addedProducts.add(product);
            Toast.makeText(this, "product added :" + product.getPrice(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActionItemClicked(int id) {
        if ((id == R.id.action_basket) &&
                (mTitle != getString(R.string.title_help)) ){ // BUG-testme: id 20
            if (addedProducts.size() > 0) {
                Intent i = new Intent(this, BasketActivity.class);
                i.putParcelableArrayListExtra("products", addedProducts);
                startActivityForResult(i, 1);
            } else {
                Toast.makeText(this, "Your basket is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }
        if (getString(R.string.title_user_profile).equals(mTitle)){ // BUG-testme: id 19
            mTitle = getString(R.string.title_registration);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, RegistrationFragment.newInstance()).commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                addedProducts.clear();

            } else if (resultCode == RESULT_CANCELED) {
                ArrayList<Product> newProducts = data.getParcelableArrayListExtra("products");
                Iterator<Product> iterator = addedProducts.iterator();
                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    if (!newProducts.contains(product)) {
                        iterator.remove();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
