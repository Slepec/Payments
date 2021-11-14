package com.my.payment.command;

import com.my.payment.constants.Message;
import com.my.payment.constants.Path;
import com.my.payment.db.DBManager;
import com.my.payment.db.Role;
import com.my.payment.db.Status;
import com.my.payment.db.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationCommand implements Command{
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("RegistrationCommand starts");
        boolean continueReg = true;
        String email=request.getParameter("email");
        logger.trace("Request parameter email ==> "+email);
        String login=request.getParameter("login");
        logger.trace("Request parameter login ==> "+login);
        String pass=request.getParameter("pass");
        logger.trace("Request parameter pass ==> "+pass);
        String passR=request.getParameter("pass-repeat");
        logger.trace("Request parameter pass-repeat ==> "+passR);
        String forward = Path.ERROR_PAGE;
        if(!checkEmail(email)) {
            logger.warn(Message.INVALID_EMAIL);
            request.setAttribute("emailVal", Message.INVALID_EMAIL);
            continueReg=false;
        }
        if(!checkLogin(login)) {
            logger.warn(Message.INVALID_LOGIN);
            request.setAttribute("loginVal",Message.INVALID_LOGIN);
            continueReg=false;
        }
        if(!checkPass(pass)) {
            logger.warn(Message.INVALID_PASS);
            request.setAttribute("passVal",Message.INVALID_PASS);
            continueReg=false;
        }
        if(!checkPass(passR) || !pass.equals(passR)  ) {
            logger.warn(Message.DIFFERENT_PASS);
            request.setAttribute("repeatPVal",Message.DIFFERENT_PASS);
            continueReg=false;
        }
        DBManager dbManager = DBManager.getInstance();
        if(dbManager.findUser(login)!=null)
        {
            logger.warn(Message.LOGIN_EXISTS);
            request.setAttribute("loginExist",Message.LOGIN_EXISTS);
            continueReg=false;
        }
        if(continueReg) {
            User user = new User(login, Role.USER.getId(), pass, email, Status.ACTIVE);
            logger.warn("Formed user ==> "+user);
            if (dbManager.addUser(user)) {
                logger.warn(Message.USER_CREATED);
                request.setAttribute("isSuccess",Message.USER_CREATED);
            } else {
                request.setAttribute("isSuccess", Message.CANNOT_CREATE_USER);
            }
        }
        else logger.warn(Message.CANNOT_CREATE_USER);
        forward=Path.REGISTRATION_PAGE;
        logger.warn("forward ==> "+forward);
        return forward;
    }
    private boolean checkEmail(String text)
    {
        if(text==null) return false;
        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(text);
        return m.find() && m.group().length() <= 45;
    }
    private boolean checkLogin(String text)
    {
        if(text==null) return false;
        Pattern p = Pattern.compile("^[\\w_-]{3,20}$");
        Matcher m = p.matcher(text);
        return m.find() && m.group().length() <= 20;
    }
    private boolean checkPass(String text)
    {
        if(text==null) return false;
        return text.length() <= 45 && text.length() >5;
    }
}
