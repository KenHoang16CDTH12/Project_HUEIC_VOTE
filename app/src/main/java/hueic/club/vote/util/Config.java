package hueic.club.vote.util;

/**
 * Created by kennguyen on 25/12/2016.
 */

public class Config {
    public static final String TIETMUC_OBJECT = "tietmuc_object";
    public static final String URL_LIST_TIETMUC = "http://anhquoctran.xyz:8084/core/api/list-tiet-muc";//sua sau
    public static final String URL_VOTE = "http://anhquoctran.xyz:8084/core/api/vote";//sua sau
    public static final String PARAM_ID_SINGER = "singerid";
    public static final String PARAM_IMEI = "imei";
    public static final long TIME_RELOAD = 10000;
    public static final int STATUS_DANGDIEN = 1;
    public static final int STATUS_CHUANBIDIEN = 2;
    public static final int STATUS_DADIEN = 3;
    public static final int STATUS_CHUADIEN = 4;

    public static final int M_OUT_OF_RANGE = 301;
    public static final int M_EXITED = 302;
    public static final int M_SUCCESS = 303;
    public static final int M_FAILED_UPDATE = 304;


}
