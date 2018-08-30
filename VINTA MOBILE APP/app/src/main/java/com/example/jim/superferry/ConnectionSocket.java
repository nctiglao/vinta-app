package com.example.jim.superferry;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by jim on 28/02/2018.
 */

public class ConnectionSocket extends Application {
        private Socket mSocket;

    {
        try{
            mSocket = IO.socket(Constants.SERVER_URL);
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
    public Socket getSocket() {return mSocket;}
}
