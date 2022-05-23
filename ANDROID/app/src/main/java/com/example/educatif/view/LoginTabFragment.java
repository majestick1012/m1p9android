package com.example.educatif.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private LoadingDialog loadingDialog;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    EditText name,password;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = inflater.inflate(R.layout.login_tab_fragment,container,false);
        name = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        loadingDialog = new LoadingDialog(getActivity());
        VerifyLogin();
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.urlAPI)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        return root;
    }



    private void VerifyLogin() {

        loginController = LoginController.getInstanceLogin();
        ((Button)root.findViewById(R.id.button)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();
                HashMap<String,String> map = new HashMap<>();

                map.put("email",nameText);
                map.put("password",passwordText);

                Call<Login> call = retrofitInterface.executeLogin(map);
                loadingDialog.startLoadingDialog();

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        Login login = response.body();
                        if(login!=null && login.getSuccess()) {
                            Toast.makeText(root.getContext(),R.string.login_success,Toast.LENGTH_SHORT).show();
                            SharedPreferences sp= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed=sp.edit();
                            Ed.putString("id", login.getData().get_id() );
                            Ed.putString("email", login.getData().getEmail() );
                            Ed.putString("firstname", login.getData().getFirstname() );
                            Ed.putString("token", login.getData().getAuthToken());
                            Ed.commit();

                            Intent intent = new Intent(root.getContext(), ListLessonActivity.class);
                            startActivity(intent);
                            loadingDialog.dismissDialog();
                        }
                        else{
                            loadingDialog.dismissDialog();
                            Toast.makeText(root.getContext(),R.string.login_failed,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        // Error
                        loadingDialog.startLoadingDialog();
                        Toast.makeText(root.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}
