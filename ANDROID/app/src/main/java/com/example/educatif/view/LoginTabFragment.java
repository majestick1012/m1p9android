package com.example.educatif.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.educatif.R;

public class LoginTabFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.login_tab_fragment,container,false);
        return root;
    }

}
