package com.example.educatif.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.educatif.R;
import com.example.educatif.model.AccessApi;

public class LoginTabFragment extends Fragment {

    View root;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.login_tab_fragment,container,false);
        getApi();
        return root;

    }


    private void getApi(){
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                AccessApi accessApi = new AccessApi();
                accessApi.sendRequest();

                Toast.makeText(root.getContext(), AccessApi.hello, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
