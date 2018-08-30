package com.example.jim.superferry.socketconn;

import android.app.Application;

import com.example.jim.superferry.Constants;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;



/**
 * Created by jim on 21/03/2018.
 */

public class SocketConnection extends Application {
    private Socket mSocket;
    {
        try{
            mSocket = IO.socket(Constants.SERVER_URL);
        }catch (URISyntaxException e){
            throw new RuntimeException(e);

        }

    }
    public Socket getSocket(){
        return mSocket;
    }
}
