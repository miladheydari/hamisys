package milad.hamisystem;

import android.app.Application;
import android.util.Log;


import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.Server;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

import milad.hamisystem.di.ApplicationComponent;
import milad.hamisystem.di.DaggerApplicationComponent;

public class HamisysApplication extends Application {
    Server mServer;

    @Override
    public void onCreate() {
        super.onCreate();
        mServer = AndServer.serverBuilder().port(1221).registerHandler("/questions", new RequestHandler() {
            @Override
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {

                String tContents = "";

                try {
                    InputStream stream = getAssets().open("questions.json");

                    int size = stream.available();
                    byte[] buffer = new byte[size];
                    stream.read(buffer);
                    stream.close();
                    tContents = new String(buffer);
                } catch (IOException e) {
                    // Handle exceptions here
                }

                response.setEntity(new StringEntity(tContents, "UTF-8"));
                response.setStatusCode(200);
            }
        }).listener(new Server.ServerListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onStopped() {


            }

            @Override
            public void onError(Exception e) {


            }
        }).build();

        mServer.startup();


        com.blankj.utilcode.util.Utils.init(this);
        component = DaggerApplicationComponent.builder().application(this).build();

    }

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

}