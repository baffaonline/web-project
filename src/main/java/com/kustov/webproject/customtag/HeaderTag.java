package com.kustov.webproject.customtag;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class HeaderTag extends TagSupport{
    private User user;
    private String contextPath;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public int doStartTag() throws JspException {
        try{
            switch (user.getType().getTypeName()){
                case "user":{
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=user_information&page=user\">" +
                                user.getUsername() + "</a>");
                    jspWriter.write("</li>");
                    jspWriter.write("<li class=\"nav-item\">");
                    MessageManager messageManager = new MessageManager();
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=logout\">" +
                            messageManager.getString("header.logout") + "</a>");
                    jspWriter.write("</li>");
                    break;
                }case "admin":{
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.write("<li class=\"nav-item\">");
                    MessageManager messageManager = new MessageManager();
                    PropertyManager propertyManager = new PropertyManager("pages");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            propertyManager.getProperty("path_page_admin") + "\">" +
                                user.getUsername() + "(" + messageManager.getString("header.admin") + ")" + "</a>");
                    jspWriter.write("</li>");
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=logout\">" +
                            messageManager.getString("header.logout") + "</a>");
                    jspWriter.write("</li>");
                    break;
                }case "guest":{
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.write("<li class=\"nav-item\">");
                    MessageManager messageManager = new MessageManager();
                    PropertyManager propertyManager = new PropertyManager("pages");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            propertyManager.getProperty("path_page_controller") + "?command=prepare_login" + "\">" +
                            messageManager.getString("header.login") + "</a>");
                    jspWriter.write("</li>");
                    jspWriter.write("<li class=\"nav-item\">");
                    jspWriter.write("<a class=\"nav-link navigation-bar-item\" href=\"" + contextPath +
                            "/MainController?command=country_setup\">" +
                            messageManager.getString("header.register") + "</a>");
                    break;
                }
            }
        }catch (IOException exc){
            throw new JspException(exc);
        }
        return SKIP_BODY;
    }
}
