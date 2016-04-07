package pgd.dev.artproject.RestClient;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import pgd.dev.artproject.Model.GenericResponse;
import pgd.dev.artproject.exception.GagalLoginException;

/**
 * Created by endymuhardin on 4/6/16.
 */
public class AuthenticationRestClient {

    private static final String SERVER_URL = "http://10.0.3.2:8080";
    private RestTemplate restTemplate;

    public AuthenticationRestClient() {
        restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
                .setConnectTimeout(2 * 1000);
    }

    public GenericResponse login(String username, String password) throws GagalLoginException {
        String url = SERVER_URL + "/api/login";

        HashMap<String, String> requestData = new HashMap<>();
        requestData.put("username", username);
        requestData.put("password", password);

        try {
            // add message converter : GSON http message
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            return restTemplate.postForObject(url, requestData, GenericResponse.class);
        } catch (Exception err){
            throw new GagalLoginException("Server tidak bisa dihubungi");
        }
    }

}
