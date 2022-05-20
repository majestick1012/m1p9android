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
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.controller.LoginController;
import com.example.educatif.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginTabFragment extends Fragment {

    View root;
    private LoginController loginController;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_Url="http://testnodeekaly.herokuapp.com";


    EditText name,password;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.login_tab_fragment,container,false);
        name = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        //verifyApiLogin();
        VerifyLogin();
        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        return root;
    }


    private void verifyApiLogin(){
        loginController = LoginController.getInstanceLogin();
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();
                Intent intent = loginController.verifyLogin(nameText,passwordText,root.getContext());
               //Intent intent = new Intent(root.getContext(), LessonActivity.class);
                if(intent!=null){
                    startActivity(intent);
                }
            }
        });
    }

    private void VerifyLogin() {
        loginController = LoginController.getInstanceLogin();
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();
                HashMap<String,String> map = new HashMap<>();

                //recuperation des valeurs du login a verifier
                map.put("name",nameText);
                map.put("password",passwordText);

                Call<Login> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        loginController.login = response.body();
                            if(loginController.login!=null) {
                            Toast.makeText(root.getContext(),"Connexion réussie !",Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(root.getContext(), LessonActivity.class);
                            Intent intent = new Intent(root.getContext(), ListLessonActivity.class);
                            startActivity(intent);
                            }
                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        //erreur connexion ou autre exception test 2
                        Toast.makeText(root.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
    }


}
