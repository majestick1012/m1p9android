package com.example.educatif.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.educatif.R;
import com.example.educatif.Utils.AccesHTTP;
import com.example.educatif.controller.LoginController;
import com.example.educatif.model.AccessApi;

import java.util.concurrent.ExecutionException;

import kotlinx.coroutines.scheduling.Task;

public class LoginTabFragment extends Fragment {

    View root;
    private LoginController loginController;

    EditText name,password;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.login_tab_fragment,container,false);
        name = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        verifyApiLogin();

        return root;
    }


    private void verifyApiLogin(){
        loginController = LoginController.getInstanceLogin();
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();

                Intent intent = loginController.verifyLogin(nameText,passwordText,root.getContext());
                if(intent!=null){
                    startActivity(intent);
                }
            }
        });
    }




}
