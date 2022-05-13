package com.example.educatif.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccesHTTP extends AsyncTask<String,Integer,Long> {
    public void setTypesend(String typesend) {
        this.typesend = typesend;
    }

    private String typesend;
    private ArrayList<NameValuePair> parametres;
    private String ret=null;
    public AsyncResponse delegate = null;

    /**
     * constructeur
     */
    public AccesHTTP(){
        parametres = new ArrayList<>();
    }

    /**
     * ajout d'un paramètre POST
     * @param nom
     * @param valeur
     * @return
     */
    public void addParam(String nom,String valeur){
        parametres.add(new BasicNameValuePair(nom,valeur));
    }

    @Override
    protected Long doInBackground(String... strings) {

        //HttpClient httpclient = CreateHttpClient.createHttpClient1();


        HttpClient cnxHttp = CreateHttpClient.getNewHttpClient();

        //Object paramCnx = new HttpPost(strings[0]);
        //HttpGet paramCnx = new HttpGet(strings[0]);
        try{
            Object paramCnx = new HttpPost(strings[0]);
            HttpResponse response;
            if(!typesend.equals("GET")){
                //encodage des parametres si post
                ((HttpPost)paramCnx).setEntity(new UrlEncodedFormEntity(parametres));
                //connexion et envoie de parametre,attente de reponse post
                //HttpResponse response = cnxHttp.execute(paramCnx);
                 response = cnxHttp.execute(((HttpPost)paramCnx));
            }
            else {
                 paramCnx = new HttpGet(strings[0]);
                //connexion et envoie de parametre,attente de reponse get
                response = cnxHttp.execute(((HttpGet)paramCnx));
            }


            //transformation de la reponse
            ret= EntityUtils.toString(response.getEntity());

            Log.d("Method","*************"+typesend+"**********");

        }catch (UnsupportedEncodingException e){
            Log.d("Erreur encodage","******"+e.toString()+"******");
        }catch (ClientProtocolException e){
            Log.d("Erreur de protocol","******"+e.toString()+"******");
        }catch (IOException e){
            Log.d("Erreur d'I/O","******"+e.toString()+"******");
        }
        return null;
    }
    @Override
    protected void onPostExecute(Long result){
        delegate.ProcessFinish((ret.toString()));
    }
}
