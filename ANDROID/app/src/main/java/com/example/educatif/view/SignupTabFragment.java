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
import com.example.educatif.model.AccessApi;
import com.example.educatif.model.Login;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupTabFragment extends Fragment {

    View root;
    LoginController loginController;
    private EditText name,email,password,phoneNumber;
    private TabLayout tabLayout;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_Url="http://10.0.2.2:3000";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.signup_tab_fragment,container,false);
        //getApiget();
        loginController = LoginController.getInstanceLogin();
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        phoneNumber = root.findViewById(R.id.mobile);


        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        handleSignUpDialog();
        //InsertUserView();
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

    private void handleSignUpDialog(){
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textPhoneNumber = phoneNumber.getText().toString();

                HashMap<String,String> map = new HashMap<>();
                map.put("name",textName);
                map.put("email",textEmail);
                map.put("password",textPassword);
                map.put("phonenumber",textPhoneNumber);

                Call<Void> call = retrofitInterface.executeSignUp(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        //Toast.makeText(root.getContext(),response.body().toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(root.getContext(), "Insertion fait", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(root.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                //Toast.makeText(root.getContext(), "Insertion fait", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
