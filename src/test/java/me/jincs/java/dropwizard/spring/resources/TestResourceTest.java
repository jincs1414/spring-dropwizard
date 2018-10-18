package me.jincs.java.dropwizard.spring.resources;

import me.jincs.java.dropwizard.spring.SpringDropwizardApplication;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by jinchengsong on 2018/10/18.
 */
public class TestResourceTest {

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        SpringDropwizardApplication.main(new String[]{"server","dropwizard.yaml"});
    }

    @org.junit.Test
    public void sayHelloWithName() throws Exception {
        String url = "http://127.0.0.1:8888/application/test/sayHello?name=1";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        assertEquals(result,"{\"name\":\"1\"}");
    }

    @org.junit.Test
    public void sayHelloWithOutName() throws Exception {
        String url = "http://127.0.0.1:8888/application/test/sayHello";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        assertEquals(result,"{\"name\":\"hah\"}");
    }

}