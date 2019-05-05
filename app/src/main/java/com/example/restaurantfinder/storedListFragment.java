package com.example.restaurantfinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class storedListFragment extends Fragment {

    private View view;
    private TextView textView;
    private ListView listView;

    private String ShopName;
    private ArrayList<String> stored_list_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater i, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = i.inflate(R.layout.tab_storedlist, container, false);
        textView = view.findViewById(R.id.test_StoredList_textView);
        listView = view.findViewById(R.id.list_stored_list);

        return view;
    }

    @Override
    public void onResume() {
        if (MainActivity.myBundle.isEmpty() == false) {
            ShopName = MainActivity.myBundle.getString("ShopName");

            textView.setText(ShopName);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, stored_list_list);
            listView.setAdapter(arrayAdapter);

            stored_list_list.add(ShopName);
            arrayAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

}