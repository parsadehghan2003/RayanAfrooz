package com.PechPech.pechpechprivate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ir.rayanafrooz.app.ActivityTable */
public class MessageActivity extends AppCompatActivity {
    int countRowItems;
    TableLayout tableLayout;

    /* JADX WARNING: type inference failed for: r13v0, types: [android.content.Context, ir.rayanafrooz.app.ActivityTable, androidx.appcompat.app.AppCompatActivity] */
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        List<String> stringList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("messageId"));
            this.countRowItems = jsonObject.getInt("rowItem");
            for (int i = 0; i < jsonObject.length() - 1; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("itemRow");
                sb.append(i);
                stringList.addAll(Arrays.asList(jsonObject.getString(sb.toString()).split(",")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.tableLayout = (TableLayout) findViewById(R.id.tbl_layout);
        for (int i2 = 0; i2 < stringList.size() / this.countRowItems; i2++) {
            TableRow tableRow = new TableRow(this);
            LayoutParams tableParams = new LayoutParams(-1, -1);
            tableParams.weight = 1.0f;
            tableRow.setLayoutParams(tableParams);
            tableRow.setWeightSum(2.0f);
            for (int j = 0; j < this.countRowItems; j++) {
                TextView textView = new TextView(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, -1);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(17);
                textView.setText((CharSequence) stringList.get((this.countRowItems * i2) + j));
                textView.setTextSize(18);
                textView.setTextColor(getResources().getColor(R.color.white));
                if (i2 != 0) {
                    if (i2 % 2 == 0) {
                        textView.setBackgroundColor(getResources().getColor(R.color.white));
                        textView.setTextColor(getResources().getColor(R.color.black));

                    } else {
                        textView.setTextColor(getResources().getColor(R.color.white));
                        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                    tableRow.addView(textView);
                } else {
                    textView.setPadding(70, 70, 70, 70);
                    layoutParams.setMargins(1, 1, 1, 1);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tableRow.addView(textView);
                }
            }
            this.tableLayout.addView(tableRow);
        }
    }
}
