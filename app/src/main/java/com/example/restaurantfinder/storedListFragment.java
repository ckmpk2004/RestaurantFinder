package com.example.restaurantfinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class storedListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater i, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = i.inflate(R.layout.tab_storedlist, container, false);
        return view;
    }
}