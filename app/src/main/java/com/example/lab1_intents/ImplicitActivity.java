package com.example.lab1_intents;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImplicitActivity extends AppCompatActivity {

    private static final String TAG = "ImplicitActivity";

    private ArrayList<String> app_name_list = new ArrayList<>();
    private ArrayList<String> app_package_list = new ArrayList<>();
    private ArrayList<Drawable> app_icon_list = new ArrayList<>();
    private ArrayList<ComponentName> componentNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        Log.d(TAG, "onCreate: started.");
        listApps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear_view:
                initRecyclerView("linear");
                return true;

            case R.id.grid_view:
                initRecyclerView("grid");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listApps() {
        PackageManager packageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : apps) {
            try {
                String package_name = resolveInfo.activityInfo.packageName;
                /*String app_name = (String)packageManager.getApplicationLabel(packageManager.getApplicationInfo(package_name,
                        PackageManager.GET_META_DATA));*/
                String app_name = (String)resolveInfo.loadLabel(packageManager);
                Drawable app_icon = resolveInfo.loadIcon(packageManager);

                ActivityInfo activity = resolveInfo.activityInfo;
                ComponentName componentName = new ComponentName(activity.applicationInfo.packageName, activity.name);

                boolean same = false;
                for (int i = 0; i < app_name_list.size(); i++) {
                    if (package_name.equals(app_package_list.get(i)))
                        same = true;
                }
                if (!same) {
                    app_name_list.add(app_name);
                    app_package_list.add(package_name);
                    app_icon_list.add(app_icon);
                    componentNames.add(componentName);
                }
            } catch (Exception ignored) {}
        }

        initRecyclerView("default");
    }

    private void initRecyclerView(String layout_type) {
        Log.d(TAG, "initRecyclerView: init RecyclerView");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        if ("grid".equals(layout_type)) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(app_name_list, app_icon_list, componentNames, layout_type, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(app_name_list, app_icon_list, componentNames, layout_type, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }
}
