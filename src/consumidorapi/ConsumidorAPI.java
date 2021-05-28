/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumidorapi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Fabio
 */
public class ConsumidorAPI {
    private static String URLBase = "http://api.olhovivo.sptrans.com.br/v2.1";
    private static String token = "e4278e5f4d2f90b3b75f301a83d41fe5d38e47c3a3d9d957175ac1dc2a421a5e";
    
    private static ConsumidorAPI instance;
    
    private CloseableHttpClient clientHttp;
    
    private ConsumidorAPI(){
        this.clientHttp = HttpClients.createDefault();
    }
    
    public static ConsumidorAPI getInstance(){
        if(instance == null){
            instance = new ConsumidorAPI();
        }
        return instance;
    }
    
    public void doLogin(){
        try{
            HttpPost httpPost = new HttpPost(ConsumidorAPI.URLBase +"/Login/Autenticar?token="+ConsumidorAPI.token);
            
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if(status >=200 && status <300){
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                }else{
                        throw new ClientProtocolException("Unexpected resounse status = " + status);
                    }
                }
            };
        String responseBody = this.clientHttp.execute(httpPost, responseHandler);
        System.out.println("-------------------------------------------------------");
        System.out.println(responseBody);
    }catch(IOException ex){
        Logger.getLogger(ConsumidorAPI.class.getName()).log(Level.SEVERE,null,ex);
    }
}
    public String doRequest(String path){
        
        String responseBody = null;
        try{
            HttpGet httpGet = new HttpGet(ConsumidorAPI.URLBase+path);
            
            ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if(status >=200 && status <300){
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                }else{
                        throw new ClientProtocolException("Unexpected resounse status =" + status);
                    }
                }
            };
            responseBody = this.clientHttp.execute(httpGet, responseHandler);
            System.out.println("--------------------------------------------");
        }catch(IOException ex){
        Logger.getLogger(ConsumidorAPI.class.getName()).log(Level.SEVERE,null,ex);
    }
        return responseBody;
    }

}