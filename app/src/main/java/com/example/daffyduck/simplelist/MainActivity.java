package com.example.daffyduck.simplelist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int index = 0;
    SimpleAdapter simpleAdapter;
    List<HashMap<String, String>> liste;
    ListView listView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        String jsonStr = "[{\"key\": \"bob1\", \"value\": \"01 01 01 01 01\"},"
            + "{\"key\": \"bob2\", \"value\": \"01 01 01 01 01\"},"
            + "{\"key\": \"bob3\", \"value\": \"01 01 01 01 01\"}]";
        liste = new Gson().fromJson(jsonStr, new TypeToken<List<HashMap<String, String>>>() {}.getType());
        simpleAdapter = new SimpleAdapter(this,
                liste,
                android.R.layout.simple_list_item_multiple_choice,
                new String[] {"key", "value"},
                new int[] {android.R.id.text1, android.R.id.text2 });
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

        //Switch
        Switch aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int resource;
                if(b) {
                    //On déclare qu'on ne peut plus sélectionner d'élément
                    resource = android.R.layout.simple_list_item_multiple_choice;
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } else {
                    //On déclare qu'on ne peut plus sélectionner d'élément
                    resource = android.R.layout.simple_list_item_1;
                    listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
                }

                simpleAdapter = new SimpleAdapter(context,
                        liste,
                        resource,
                        new String[] {"key", "value"},
                        new int[] {android.R.id.text1, android.R.id.text2 });
                //On affiche un layout qui ne permet pas de sélection
                listView.setAdapter(simpleAdapter);
            }
        });

        //Spinner
        List<String> strings = Arrays.asList(new String[]{"a", "b", "c"});
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, strings);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);

    }
    public void addEntry(View view) {
        Random random = new Random();
        HashMap<String, String> rndHashMap = liste.get(random.nextInt(liste.size()));
        HashMap<String, String> h = new HashMap<String, String>();
        h.put("key", rndHashMap.get("key"));
        h.put("value", rndHashMap.get("value"));
        liste.add(h);
        simpleAdapter.notifyDataSetChanged();
    }
}
