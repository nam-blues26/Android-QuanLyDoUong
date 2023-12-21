package edu.xda.adn.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import edu.xda.adn.view.fragment.CategoryFragment;
import edu.xda.adn.view.fragment.ProductFragment;
import edu.xda.adn.view.fragment.StaffFragment;

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

//            View headerView = navigationView.getHeaderView(0);
//            TextView navUsername = (TextView) headerView.findViewById(R.id.tvHelloUser);
//            navUsername.setText(MyString.HELLO_USER + " " + LoginActivity.USERNAME);
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
            if (id == R.id.nav_product) {
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
            }else if (id == R.id.nav_staff) {
                if (!currentFragment.equals(FragmentState.FRAGMENT_STAFF)) {
                    replaceFragment(new StaffFragment());
                    currentFragment = FragmentState.FRAGMENT_STAFF;
                }
            }else if (id == R.id.nav_category) {
                if (!currentFragment.equals(FragmentState.FRAGMENT_CATEGORY)) {
                    replaceFragment(new CategoryFragment());
                    currentFragment = FragmentState.FRAGMENT_CATEGORY;
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } catch (Exception e) {
            Log.e("Nav-item:", e.getMessage());
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
                replaceFragment(new ProductFragment());
                currentFragment = FragmentState.FRAGMENT_PRODUCT;
                navigationView.getMenu().findItem(R.id.nav_product).setChecked(true);
            } else if (currentFragment.equals(FragmentState.FRAGMENT_BILL)) {
                replaceFragment(new BillFragment());
                currentFragment = FragmentState.FRAGMENT_BILL;
                navigationView.getMenu().findItem(R.id.nav_bill).setChecked(true);
            }else if (currentFragment.equals(FragmentState.FRAGMENT_STAFF)) {
                replaceFragment(new StaffFragment());
                currentFragment = FragmentState.FRAGMENT_STAFF;
                navigationView.getMenu().findItem(R.id.nav_staff).setChecked(true);
            }else if (currentFragment.equals(FragmentState.FRAGMENT_CATEGORY)) {
                replaceFragment(new CategoryFragment());
                currentFragment = FragmentState.FRAGMENT_CATEGORY;
                navigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
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
        FRAGMENT_BILL,
        FRAGMENT_STAFF,
        FRAGMENT_CATEGORY
    }
}
