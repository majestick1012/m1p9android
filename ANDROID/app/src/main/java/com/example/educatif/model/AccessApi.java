package com.example.educatif.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.educatif.Utils.AccesHTTP;
import com.example.educatif.Utils.AsyncResponse;
import com.example.educatif.view.SplashScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessApi implements AsyncResponse {


    public AccessApi(){
     super();
    }
    /**
     * Get api result
     * @param output
     */
    public static String hello;
 private static final String SERVERADDR = "http://testnodeekaly.herokuapp.com/finduser";//"https://192.168.15.199:3000/finduser";
    @Override
    public void ProcessFinish(String output){
        Log.d("Serveur","************"+output+"***************");
        try{
            //JSONObject info = new JSONObject(output);
            String info = output;
            hello = output;
            Log.d("resultats","************"+info+"***************");
        }catch (Exception e){
            e.printStackTrace();
            Log.d("erreur json","************"+e.toString()+"***************");
        }
    }

    public String ValueOutputPut(String output){
        Log.d("reponse processfinish 2","********************"+output+"*************");

        return output;
    }

    public void findUser(String name,String password) throws InterruptedException {
        AccesHTTP accesHTTP = new AccesHTTP();
        accesHTTP.setTypesend("POST");
        accesHTTP.addParam("name",name);
        accesHTTP.addParam("password",password);
        accesHTTP.delegate = this;
        accesHTTP.execute(SERVERADDR);
        accesHTTP.delegate.wait();
    }

    public Intent findUser(Login login, Context context){
        Intent intent = null;
        AccesHTTP accesHTTP = new AccesHTTP();
        accesHTTP.setTypesend("POST");
        accesHTTP.addParam("name",login.getName());
        accesHTTP.addParam("password",login.getPassword());
        accesHTTP.delegate = this;
        accesHTTP.execute(SERVERADDR);
        while(accesHTTP.isExecutedinbackground){}
        if(!accesHTTP.getRet().equals("null")){
            Log.d("getretvalue","**************"+accesHTTP.getRet()+"*************");
             intent = new Intent(context, SplashScreenActivity.class);
        }

        Toast.makeText(context, AccessApi.hello, Toast.LENGTH_SHORT).show();
        Log.d("reponse","********************"+hello+"*************");
        return intent;
    }

    public void sendRequest(){

        AccesHTTP accesHTTP = new AccesHTTP();
        accesHTTP.setTypesend("GET");
        String getMethod = "http://testnodeekaly.herokuapp.com/";
        accesHTTP.addParam("name","Admin");
        accesHTTP.addParam("password","admin");
        accesHTTP.delegate = this;
        accesHTTP.execute(getMethod);
        while(accesHTTP.isExecutedinbackground){}
    }


}
