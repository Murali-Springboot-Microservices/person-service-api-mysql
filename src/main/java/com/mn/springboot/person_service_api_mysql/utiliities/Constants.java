package com.mn.springboot.person_service_api_mysql.utiliities;

public class Constants {

    public static final String ID_NOT_FOUND_DB = "Did not find Id in Database";
    public static final String DB_SUCCESS = "Successfully Processed Database Transactions";

    public enum Status {
        SUCCESS,
        FAILED,
        PARTIAL
    }
}
