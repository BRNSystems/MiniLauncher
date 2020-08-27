package net.dynu.brnstream.minilauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            String name = (String) pm.getApplicationLabel(packageInfo);
            String packname = packageInfo.packageName;
            Global.Name.add(name);
            Global.PackName.add(packname);
        }
        for (int i = 0; i < Global.Name.size(); i++) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, Global.Name);
            final ListView listView = findViewById(R.id.apps);
            listView.setAdapter(adapter);
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    String packname = Global.PackName.get(position);
                    Context dat = ScrollingActivity.this;
                    //Toast toast = Toast.makeText(dat, String.valueOf(packname), (int) 0.1);
                    //toast.show();
                    Intent pack = getPackageManager().getLaunchIntentForPackage(packname);
                    try {
                        startActivity(pack);
                    }
                    catch (Exception e) {
                        Toast toast2 = Toast.makeText(dat, "Cannot run this app", Toast.LENGTH_LONG);
                        toast2.show();
                    }
                }
            });
        }
            final EditText text = findViewById(R.id.searchbar);
            text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String value = text.getText().toString();
                    int i=0;
                    for (String name : Global.Name) {
                        if (name.toLowerCase().contains(value.toLowerCase())) {
                            final ListView listView = findViewById(R.id.apps);
                            listView.smoothScrollToPosition(i);
                            break;
                        }
                    i++;
                    }
                }
                });

    }
}