package edu.xda.adn.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import edu.xda.adn.R;
import edu.xda.adn.view.MyAlertDialog;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.fragment.BillFragment;
import edu.xda.adn.view.fragment.HomeFragment;
import edu.xda.adn.view.fragment.ProductFragment;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentState currentFragment = FragmentState.FRAGMENT_PRODUCT;

    private static DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            drawerLayout = findViewById(R.id.draw_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            navigationView = findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);

            replaceFragment(new ProductFragment());
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.tvHelloUser);
            navUsername.setText(MyString.HELLO_USER + " " + LoginActivity.USERNAME);
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume", "invoked");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("OnPause", "invoked");
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                if (!currentFragment.equals(FragmentState.FRAGMENT_HOME)) {
                    replaceFragment(new HomeFragment());
                    currentFragment = FragmentState.FRAGMENT_HOME;
                }
            } else if (id == R.id.nav_product) {
                if (!currentFragment.equals(FragmentState.FRAGMENT_PRODUCT)) {
                    replaceFragment(new ProductFragment());
                    currentFragment = FragmentState.FRAGMENT_PRODUCT;
                }
            } else if (id == R.id.nav_bill) {
                if (!currentFragment.equals(FragmentState.FRAGMENT_BILL)) {
                    replaceFragment(new BillFragment());
                    currentFragment = FragmentState.FRAGMENT_BILL;
                }
            } else if (id == R.id.nav_logout) {
                Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return;
            }
            if (currentFragment.equals(FragmentState.FRAGMENT_PRODUCT)) {
                if (ProductFragment.isIsDeleteState()) {
                    ProductFragment.setDeleteProductState(false);
                    ProductFragment.getProductAdapter().notifyDataSetChanged();
                    return;
                }
                replaceFragment(new HomeFragment());
                currentFragment = FragmentState.FRAGMENT_HOME;
                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            } else if (currentFragment.equals(FragmentState.FRAGMENT_BILL)) {
                replaceFragment(new HomeFragment());
                currentFragment = FragmentState.FRAGMENT_HOME;
                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            } else if (currentFragment.equals(FragmentState.FRAGMENT_HOME)) {
                MyAlertDialog.showAlertDialog(
                        this,
                        MyString.CONFIRM_TITLE,
                        MyString.LOGOUT_MESSAGE,
                        MyString.LOGOUT_NAME_POSITIVE_BUTTON,
                        MyString.LOGOUT_NAME_NEGATIVE_BUTTON,
                        (dialog, which) -> {
                            Toast.makeText(MainActivity.this, R.string.logout_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        },
                        (dialog, which) -> {

                        }
                );
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                if (currentFragment.equals(FragmentState.FRAGMENT_PRODUCT)) {
                    if (ProductFragment.isIsDeleteState()) {
                        ProductFragment.setDeleteProductState(false);
                        ProductFragment.getProductAdapter().notifyDataSetChanged();
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_nav, menu);
//        return true;
//    }

    enum FragmentState {
        FRAGMENT_HOME,
        FRAGMENT_PRODUCT,
        FRAGMENT_BILL
    }
}
