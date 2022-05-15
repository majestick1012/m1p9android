package com.example.educatif.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.educatif.R;
import com.example.educatif.controller.LoginController;
import com.example.educatif.model.AccessApi;
import com.example.educatif.model.Login;
import com.google.android.material.tabs.TabLayout;

public class SignupTabFragment extends Fragment {

    View root;
    LoginController loginController;
    private EditText name,email,password,phoneNumber;
    private TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.signup_tab_fragment,container,false);
        //getApiget();
        loginController = LoginController.getInstanceLogin();
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        phoneNumber = root.findViewById(R.id.mobile);

        InsertUserView();
        return root;

    }

    private void getApiget(){
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                AccessApi accessApi = new AccessApi();
                accessApi.sendRequest();
                Toast.makeText(root.getContext(), AccessApi.hello, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InsertUserView(){


        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textPhoneNumber = phoneNumber.getText().toString();
                loginController.login = new Login(textName,textEmail,textPassword,textPhoneNumber);
                AccessApi accessApi = new AccessApi();
                accessApi.insertUser(loginController.login,root.getContext());
                Intent intent = new Intent(root.getContext(), LoginActivity.class);
                startActivity(intent);

                Toast.makeText(root.getContext(), "Insertion fait", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
