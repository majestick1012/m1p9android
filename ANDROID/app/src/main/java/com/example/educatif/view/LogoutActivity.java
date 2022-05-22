package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.Login;
import com.example.educatif.model.Preferences;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class LogoutActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private LoadingDialog loadingDialog;
    private SharedPreferences sharedPreferences;
    private String base_Url="https://m1p9android-jm.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        loadingDialog = new LoadingDialog(LogoutActivity.this);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        logout();
    }

    public void logout()
    {
        String bearerToken = "Bearer ";
        String id = "";

        String tokenStored = sharedPreferences.getString("token", null);
        String userId = sharedPreferences.getString("id", null);
        if(tokenStored != null && !tokenStored.isEmpty()){
            bearerToken += tokenStored;
        }
        if(userId != null && !userId.isEmpty()){
            id = userId;
        }


        HashMap<String,String> map = new HashMap<>();
        map.put("id", id);
        Log.d("tag", bearerToken);
        Log.d("tag", id);

        Call<Login> call = retrofitInterface.executeLogout(bearerToken, map);
        loadingDialog.startLoadingDialog();

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if(login!=null && login.getSuccess()) {
                    Toast.makeText(getBaseContext(),R.string.logout_success,Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor Ed = sharedPreferences.edit();
                    Ed.remove("id" );
                    Ed.remove("email");
                    Ed.remove("firstname");
                    Ed.remove("token");
                    Ed.commit();

                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    loadingDialog.dismissDialog();
                }
                else{
                    loadingDialog.dismissDialog();
                    Toast.makeText(getBaseContext(),R.string.login_failed,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                // Error
                loadingDialog.startLoadingDialog();
                Toast.makeText(getBaseContext(),R.string.login_failed,Toast.LENGTH_LONG).show();
            }
        });
    }
}