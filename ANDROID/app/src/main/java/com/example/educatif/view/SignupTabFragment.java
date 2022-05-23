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
import com.example.educatif.model.Login;

public class SignupTabFragment extends Fragment {

    View root;
    private LoginController loginController;
    private EditText name,email,password,lastname;
    private TabLayout tabLayout;
    private LoadingDialog loadingDialog;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.signup_tab_fragment,container,false);
        
        loginController = LoginController.getInstanceLogin();
        loadingDialog = new LoadingDialog(getActivity());
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        lastname = root.findViewById(R.id.lastname);

        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.urlAPI)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        handleSignUpDialog();
        return root;
    }

    private void handleSignUpDialog(){
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textlastname = lastname.getText().toString();

                HashMap<String,String> map = new HashMap<>();
                map.put("email",textEmail);
                map.put("firstname",textName);
                map.put("lastname",textlastname);
                map.put("password",textPassword);

                Call<Login> call = retrofitInterface.executeSignUp(map);
                loadingDialog.startLoadingDialog();

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        Login login = response.body();
                        if(login!=null && login.getSuccess()) {
                            loadingDialog.dismissDialog();
                            Toast.makeText(root.getContext(),R.string.signup_success,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(root.getContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingDialog.dismissDialog();
                            Toast.makeText(root.getContext(),R.string.signup_failed,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        loadingDialog.dismissDialog();
                        Toast.makeText(root.getContext(),R.string.no_internet,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
