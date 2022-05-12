package com.example.educatif.model;

import android.util.Log;

import com.example.educatif.Utils.AccesHTTP;
import com.example.educatif.Utils.AsyncResponse;

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
 private static final String SERVERADDR = "http://testnodeekaly.herokuapp.com/";//https://192.168.15.199:3000/";
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

    public void sendRequest(){
        AccesHTTP accesHTTP = new AccesHTTP();

        accesHTTP.delegate = this;
        accesHTTP.execute(SERVERADDR);

    }

}
