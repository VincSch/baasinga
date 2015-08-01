package com.vschwarzer.baasinga.web.common;

/**
 * Created by Vincent Schwarzer on 22.07.15.
 */
public final class Endpoints {

    /**
     * Authentication Endpoints
     */
    public static final String SignUp = "/signup";
    public static final String SignUp_Param_Register = "register";
    public static final String Login = "/login";
    public static final String Login_Error = "/loginerror";
    public static final String Logout = "/logout";

    /**
     * Profile Endpoints
     */
    public static final String Profile = "/profile";
    public static final String Profile_Param_ChangePw = "changepw";
    public static final String Profile_Param_Edit = "edit";
    public static final String Profile_Param_ChangeEmail = "changeemail";

    /**
     * Application Endpoints
     */
    public static final String Application = "/application";
    public static final String Application_New = "/application/new";
    public static final String Application_Edit = "/application/{appId}";
    public static final String Application_Details = "/application/details/{appId}";

    public static final String Application_Param_Save = "save";
    public static final String Application_Param_AddModel = "addModel";
    public static final String Application_Param_SaveModel = "saveModel";
    public static final String Application_Param_RemoveModel = "removeModel";
    public static final String Application_Param_AddAttribute = "addAttribute";
    public static final String Application_Param_RemoveAttribute = "removeAttribute";
    public static final String Application_Param_AddRelation = "addRelation";
    public static final String Application_Param_RemoveRelation = "removeRelation";

    public static final String Dashboard = "/dashboard";


}
