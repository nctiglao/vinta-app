package com.example.jim.superferry;


/**
 * Created by jim on 16/02/2018.
 */

public class Messsage {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;

    private int mType;
    private String mMessage;

    private Messsage(){

    }

    public int getType(){
        return mType;
    }

    public String getMessage(){
        return mMessage;
    }

    public static class Builder {
        private final int mType;
        private String mUsername;
        private String mMessage;

        public Builder(int type) {
            mType = type;
        }

        public Builder username(String username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Messsage build(){
            Messsage message = new Messsage();
            message.mMessage = mMessage;
            return message;
        }

    }
}
