package com.thesisreading.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * request assist
 */
public class RequestUtils {

    /**
     * 从session中获得当前登录的userid
     */
    public static Integer getUser(HttpServletRequest request){
    	return (Integer) request.getSession().getAttribute("userid");
    }

    public static String getLoginId(HttpServletRequest request){
        return request.getHeader("Token");
    }


    /**
     * 页数
     */
    public static int getPage(HttpServletRequest request){
    	String page = request.getParameter("page");
    	return page == null ? 1 : Integer.parseInt(page);
    }

    /**
     * 页的大小
     */
    public static int getPageSize(HttpServletRequest request){
        String pageSize = request.getParameter("size");
        return  pageSize == null ? 10 : Integer.parseInt(pageSize);
    }


}
