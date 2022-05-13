package com.example.educatif.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.educatif.R;
import com.example.educatif.model.AccessApi;

public class LoginTabFragment extends Fragment {

    View root;

    EditText name,password;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.login_tab_fragment,container,false);
        name = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        getApi();
        return root;
    }


    private void getApi(){


        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String nametext = name.getText().toString();
                String passwordtext = password.getText().toString();
                AccessApi accessApi = new AccessApi();
                //accessApi.sendRequest();
                accessApi.findUser(nametext,passwordtext);
                Toast.makeText(root.getContext(), AccessApi.hello, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
