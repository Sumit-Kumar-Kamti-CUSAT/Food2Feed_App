package com.rsn.food2feedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rsn.food2feedo.ui.history.HistoryFragment;
import com.rsn.food2feedo.ui.home.HomeFragment;

public class DonorMainActivity extends AppCompatActivity {
    DrawerLayout dr;
    NavigationView nv;
    Toolbar tb;

    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_main);
        bnView=findViewById(R.id.nav_bot);

        dr=findViewById(R.id.drawer);
        nv=findViewById(R.id.navViewDr);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(DonorMainActivity.this,dr,tb,R.string.OpenDrawer,R.string.CloseDrawer);

        dr.addDrawerListener(toggle);
        toggle.syncState();
        FrameLayout f=(FrameLayout)findViewById(R.id.container);
        f.removeAllViews();
        loadFragment(new HomeFragment(),true);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        loadFragment(new HomeFragment(),true);
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(DonorMainActivity.this,AdminLogin.class));
                        finish();
                        break;
                    case R.id.navigation_history:
                        loadFragment(new HistoryFragment(),false);
                        break;
                    case R.id.theme:
                        setTheme(android.R.style.Theme_DeviceDefault_DayNight);
                        break;
                    case R.id.about_us:
                        startActivity(new Intent(getApplicationContext(),AboutUs.class));
                        break;
                    case R.id.rateus:
                        Uri uri = Uri.parse("http://www.play.google.com"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case R.id.share:
                        Intent sendIntent=new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT,"Download This APP\n\n https://play.google.com/store/apps/details?id ''=com.instagram.android''"+getPackageName());
                        startActivity(Intent.createChooser(sendIntent,"Choose One"));
                        break;
                    default:
                        Toast.makeText(DonorMainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                dr.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // App Drawer Layout...

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.navigation_home1:
                        FrameLayout f=(FrameLayout)findViewById(R.id.container);
                        f.removeAllViews();
                        loadFragment(new HomeFragment(),true);
                        break;
                    case R.id.navigation_logout1:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(DonorMainActivity.this,AdminLogin.class));
                        finish();
                        break;
                    case R.id.navigation_history1:
                        FrameLayout fl=(FrameLayout)findViewById(R.id.container);
                        fl.removeAllViews();
                        loadFragment(new HistoryFragment(),false);
                        break;
                    case R.id.abt:
                        startActivity(new Intent(getApplicationContext(),AboutUs.class));
                        break;
                    case R.id.feedbk:
                        Intent intent = new Intent(getApplicationContext(),Feedback.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(DonorMainActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.navigation_home1);



    }

    @Override
    public void onBackPressed() {
        if(dr.isDrawerOpen(GravityCompat.START)){
            dr.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();
        }
        AlertDialog.Builder exitDialog=new AlertDialog.Builder(this);
        exitDialog.setTitle("Exit?");
        exitDialog.setMessage("Are you sure want to exit?");
        exitDialog.setIcon(R.drawable.exit);

        exitDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Welcome Back!",Toast.LENGTH_SHORT).show();
            }
        });
        exitDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        exitDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
            }
        });
        exitDialog.show();
    }

    private void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        FrameLayout f=(FrameLayout)findViewById(R.id.container);
        f.removeAllViews();
        if(flag)
            ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


}