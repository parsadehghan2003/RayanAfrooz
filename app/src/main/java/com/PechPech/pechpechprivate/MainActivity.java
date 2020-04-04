package com.PechPech.pechpechprivate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    String[] country = {"all", "۱۰", "٢۵", "۵۰", "٧۵", "۱۰۰"};
    RecyclerView mRecyclerViewMain;
    DataBase dataBase;
    FloatingActionButton mFloatingActionButtonDelete;
    RecyclerViewAdapterMain recyclerViewAdapterMain;
    int count = 0;
    CheckBox checkBox;
    Intent intent;
    NoInternetDialog noInternetDialog;
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.checkBox = findViewById(R.id.checkBox2);
        Spinner spin = findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.sp_layout, this.country);
        aa.setDropDownViewResource(17367049);

        spin.setAdapter(aa);
        spin.setPrompt("تعداد نمایش پیام در صفحه");
        this.intent = new Intent(this, JobService.class);
        SharedPreferences mSharedPref = getSharedPreferences("Setting", 0);
        final SharedPreferences.Editor mSharedPrefEditor = mSharedPref.edit();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar3));
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ( findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });
        navigationView.setCheckedItem(R.id.app_bar_switch_notification);
        final SwitchCompat drawerSwitch = (SwitchCompat) navigationView.getMenu().findItem(R.id.app_bar_switch_notification).getActionView();
        String str = "Notification";
        drawerSwitch.setChecked(mSharedPref.getBoolean(str, true));
        mSharedPrefEditor.putBoolean(str, drawerSwitch.isChecked());
        mSharedPrefEditor.apply();
        ContextCompat.startForegroundService(this, this.intent);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()){

                    case R.id.app_bar_switch_notification:

                        drawerSwitch.setChecked(!drawerSwitch.isChecked());
                        mSharedPrefEditor.putBoolean("Notification",drawerSwitch.isChecked());
                        mSharedPrefEditor.apply();
                        break;
                    case R.id.nav_change_password:
                        Intent intent;
                        intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_exit:

                        new android.app.AlertDialog.Builder(MainActivity.this)
                                .setTitle("خروج از حساب")
                                .setMessage("آیا میخواهید از حساب کاربری خود خارج شوید")
                                .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getSharedPreferences("User",MODE_PRIVATE).edit().clear().apply();
                                        mSharedPrefEditor.clear().apply();
                                        dataBase.deleteAllMessages();
                                        stopService(MainActivity.this.intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("خیر", null)
                                .setIcon(R.drawable.ic_exit)
                                .show();
                        break;
                }
                return true;
            }
        });
        this.dataBase = new DataBase(this);
        this.mFloatingActionButtonDelete = findViewById(R.id.floatingActionButton);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != 0) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.count = Integer.parseInt(mainActivity.country[position]);
                }
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.recyclerViewAdapterMain = new RecyclerViewAdapterMain(mainActivity2.dataBase.getAllMessages(MainActivity.this.count), MainActivity.this.checkBox, MainActivity.this.mFloatingActionButtonDelete);
                MainActivity.this.mRecyclerViewMain.setAdapter(MainActivity.this.recyclerViewAdapterMain);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(1);
            }
        });
        mRecyclerViewMain = findViewById(R.id.rc_view_main);
        mRecyclerViewMain.setAdapter(this.recyclerViewAdapterMain);
        mRecyclerViewMain.setNestedScrollingEnabled(false);
        mRecyclerViewMain.setHasFixedSize(true);
        mRecyclerViewMain.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (MainActivity.this.recyclerViewAdapterMain.getItemCount() < MainActivity.this.dataBase.getAllMessages(MainActivity.this.count).size()) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.recyclerViewAdapterMain = new RecyclerViewAdapterMain(mainActivity.dataBase.getAllMessages(MainActivity.this.count), MainActivity.this.checkBox, MainActivity.this.mFloatingActionButtonDelete);
                    MainActivity.this.mRecyclerViewMain.setAdapter(MainActivity.this.recyclerViewAdapterMain);
                }
            }
        });
        this.mRecyclerViewMain.setLayoutManager(new LinearLayoutManager(this));

        mFloatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case DialogInterface.BUTTON_POSITIVE:
                                final List<RecyclerViewDataModel> selected = recyclerViewAdapterMain.onSelected();
                                for (RecyclerViewDataModel model: selected) {
                                    if (model.isSelected())
                                        dataBase.deleteMassageOnByOn(model.getId());
                                }
                                recyclerViewAdapterMain.DeleteOnSelected(dataBase.getAllMessages(count),View.GONE);
                                mFloatingActionButtonDelete.setVisibility(View.GONE);
                                checkBox.setChecked(false);
                                checkBox.setVisibility(View.GONE);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("آیااز حذف پبام اطمینان دارید؟").setPositiveButton("بله", dialogClickListener)
                        .setNegativeButton("خیر", dialogClickListener).show();
            }

        });

    }

    public void onBackPressed() {
        if (mFloatingActionButtonDelete.getVisibility() == View.VISIBLE) {
            recyclerViewAdapterMain.DeleteOnSelected(this.dataBase.getAllMessages(this.count), View.GONE);
            mFloatingActionButtonDelete.setVisibility(View.GONE);
            checkBox.setChecked(false);
            checkBox.setVisibility(View.GONE);
            return;
        }
        MainActivity.super.onBackPressed();
    }
    public void onResume() {
        String str = "عدم دسترسی به اینترنت";
        super.onResume();
        try {
            NoInternetDialog.Builder noInternetDialog2 = new NoInternetDialog.Builder(this);
            noInternetDialog2.setConnectionCallback(new ConnectionCallback() {
                public void hasActiveConnection(boolean b) {
                }
            });
            noInternetDialog2.setNoInternetConnectionTitle(str);
            noInternetDialog2.setNoInternetConnectionMessage("لطفا از اتصال به اینترنت اطمینان حاصل کنید");
            noInternetDialog2.setShowInternetOnButtons(true);
            noInternetDialog2.setPleaseTurnOnText("لطفا اتصال خود را بر قرار کنید");
            noInternetDialog2.setWifiOnButtonText("وای فای");
            noInternetDialog2.setMobileDataOnButtonText("اینترنت همراه");
            noInternetDialog2.setCancelable(false);
            noInternetDialog2.setOnAirplaneModeTitle(str);
            noInternetDialog2.setOnAirplaneModeMessage("شما حالت پرواز را روشن کرده اید");
            noInternetDialog2.setPleaseTurnOffText("لطفا آن را خاموش کنید");
            noInternetDialog2.setAirplaneModeOffButtonText("حالت پرواز");
            noInternetDialog2.setShowAirplaneModeOffButtons(true);
            this.noInternetDialog = noInternetDialog2.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}