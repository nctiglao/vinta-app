package com.example.jim.superferry;

/**
 * Created by jim on 16/02/2018.
 */

public class Constants {
   // public static final String SERVER_URL = "http://192.168.43.75:4001";


    public static final String[] STATION = new String[]{"Plaza Mexico", "Mall of Asia" ,"Noveleta"};




    public static final String ERROR_GPS = "GPS signal not found.";
    public static final int REQUEST_PERMISSION_CAMERA = 3;
    public static final String DEFAULT_DATETIME = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATA = "na";

    public static final int REQUEST_PERMISSION_LOCATION = 1;
    public static final int REQUEST_PERMISSION_STORAGE = 2;

    public static final int REQUEST_CODE_GOOGLE_PLAY_SERVICES = 1;
    public static final int REQUEST_CODE_LOCATION = 2;

    public static final int LOCATION_INTERVAL = 0;
    public static final int LOCATION_DISTANCE = 0;

    public static String CODE = null;

    public static final String KEY_ID = "id";

    public static String SEARCH_HINT = "Search for items...";
    public static String SORT_TAG = "item_name";
    public static String SORT_STORE = "profile_name";
    public static String SEARCH_TAG_ALL_ITEM = "true";
    public static String SEARCH_TAG_TRENDING_SEARCH = "false";
    public static String SEARCH_TAG_NEW_ITEM = "false";
    public static String SEARCH_TAG_SALE_ITEM = "false";
    public static String SEARCH_TAG_POPULAR_ITEM = "false";
    public static String SEARCH_TAG_STORE = "false";

    //related to mainActivity
    public static String keyboardStat = null;
    public static String temp_name = null;
    public static String temp_username = null;
    public static int offSetTop = 0;
    public static String temp_create_account = null;

    public static Boolean new_create_account = false;
    public static Boolean new_login = false;

//--------Connection String---------//

/*    public static final String SERVER_URL = "http://192.168.1.6:4001";
    public static final String SERVER_URL1 = "https://caviteferry-api.cloudns.asia:8888";
    public static final String ACTIVATED = "https://caviteferry-api.cloudns.asia/mobile/account/activate";
    public static final String ACTIVATE_VALIDATE_CONNECTION = "https://caviteferry-api.cloudns.asia/mobile/account/activate/validate";
    public static final String ACTIVATE_CREATE_CONNECTION = "https://caviteferry-api.cloudns.asia/mobile/account/activate/create";
    public static final String LOGIN_CONNECTION = "https://caviteferry-api.cloudns.asia/mobile/login";
    public static final String PERMISSION_CONNECTION = "http://192.168.1.6:3000/mobile/checkpermission";
    public static final String CHKSEAT_CONNECTION = "https://caviteferry-api.cloudns.asia/mobile/seat";
    public static final String GET_PROFILE = "https://caviteferry-api.cloudns.asia/mobile/profile";
    public static final String GET_SCHEDULE = "https://caviteferry-api.cloudns.asia/mobile/vessel/schedule";
    public static final String LOGOUT_CONNECTION = "https://caviteferry-api.cloudns.asia/mobile/logout";
    public static final String FORGET_PASSWORD = "https://caviteferry-api.cloudns.asia/mobile/forgot_password";
    public static final String CONFIRM_PASS = "https://caviteferry-api.cloudns.asia/mobile/confirm_password";
    public static final String RIDE_HISTORY = "https://caviteferry-api.cloudns.asia/mobile/ride_history";*/



    public static final String URL = "http://192.168.43.118";

/*
    public static final String SERVER_URL = "http://192.168.1.6:4001";
    public static final String SERVER_URL1 = "http://192.168.43.118:8888";
    public static final String ACTIVATED = "http://192.168.43.118:3000/mobile/account/activate";
    public static final String ACTIVATE_VALIDATE_CONNECTION = "http://192.168.43.118:3000/mobile/account/activate/validate";
    public static final String ACTIVATE_CREATE_CONNECTION = "http://192.168.43.118:3000/mobile/account/activate/create";
    public static final String LOGIN_CONNECTION = "http://192.168.1.19:3000/mobile/login";
    public static final String PERMISSION_CONNECTION = "http://192.168.1.6:3000/mobile/checkpermission";
    public static final String CHKSEAT_CONNECTION = "http://192.168.1.19:3000/mobile/seat";
    public static final String GET_PROFILE = "http://192.168.1.19:3000/mobile/profile";
    public static final String GET_SCHEDULE = "http://192.168.1.19:3000/mobile/vessel/schedule";
    public static final String LOGOUT_CONNECTION = "http://192.168.1.19:3000/mobile/logout";
  //  public static final String FORGET_PASSWORD = "http://192.168.43.118:3000/mobile/forgot_password";
    public static final String CONFIRM_PASS = "http://192.168.43.118:3000/mobile/confirm_password";
    public static final String RIDE_HISTORY = "http://192.168.43.118:3000/mobile/ride_history";
    public static final String VERIFY_ACCOUNT ="http://192.168.1.19:3000/mobile/account_verification";
    public static final String VERIFY_CODE ="http://192.168.1.19:3000/mobile/code_verification";
    public static final String FORGOT_PASS="http://192.168.1.19:3000/mobile/forgot_password";*/


    public static final String SERVER_URL = "http://192.168.1.6:4001";
    public static final String SERVER_URL1 = URL+":8888";
    public static final String ACTIVATED = URL+":3000/mobile/account/activate";
    public static final String ACTIVATE_VALIDATE_CONNECTION = URL+":3000/mobile/account/activate/validate";
    public static final String ACTIVATE_CREATE_CONNECTION = URL+":3000/mobile/account/activate/create";
    public static final String LOGIN_CONNECTION = URL+":3000/mobile/login";
    public static final String PERMISSION_CONNECTION = "http://192.168.1.6:3000/mobile/checkpermission";
    public static final String CHKSEAT_CONNECTION = URL+":3000/mobile/seat";
    public static final String GET_PROFILE = URL+":3000/mobile/profile";
    public static final String GET_SCHEDULE = URL+":3000/mobile/vessel/schedule";
    public static final String LOGOUT_CONNECTION = URL+":3000/mobile/logout";
    //  public static final String FORGET_PASSWORD = "http://192.168.43.118:3000/mobile/forgot_password";
    public static final String CONFIRM_PASS = URL+":3000/mobile/confirm_password";
    public static final String RIDE_HISTORY = URL+":3000/mobile/ride_history";
    public static final String VERIFY_ACCOUNT =URL+":3000/mobile/account_verification";
    public static final String VERIFY_CODE =URL+":3000/mobile/code_verification";
    public static final String FORGOT_PASS=URL+":3000/mobile/forgot_password";


/*
    public static final String ACTIVATED = "http://192.168.254.15:3000/mobile/account/activate";
    public static final String ACTIVATE_VALIDATE_CONNECTION = "http://192.168.254.15:3000/mobile/account/activate/validate";
    public static final String ACTIVATE_CREATE_CONNECTION = "http://192.168.254.15:3000/mobile/account/activate/create";
    public static final String LOGIN_CONNECTION = "http://192.168.254.15:3000/mobile/login";
    public static final String CHKSEAT_CONNECTION = "http://192.168.254.15:3000/mobile/seat";*/


    public static  String SESSION_KEY = "";
    public static int SESSION_TIMER = 0;
    public static final String SESSION_USER = "SESSION_USER";
    public static final String SHARED_PREFERENCES = "SessionInfo";
    public static final String SHARED_PREFERENCES2 = "userInfo";
    public static final String SHARED_PREFERENCES3 = "scanKey";
    public static final String USER_NAME = "username";

    public static final String EMAIL = "email";
    public static final String PASS_ID = "pass_id";
    public static final String MOBILE = "mobile";
    public static final String ANDROID_ID = "android_id";

    public static  String STATION_ORIGIN2 ="";
    public static  String STATION_DEST2 ="";

    public static String PREF_DATA = "";
    public static String STATION_ORIGIN = "";
    public static String STATION_DEST = "";
    public static String SCAN_RESULT ="";
    public static String SESSION_USERS="";




}
