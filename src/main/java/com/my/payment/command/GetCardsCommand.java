package com.my.payment.command;

import com.my.payment.constants.Path;
import com.my.payment.db.DBManager;
import com.my.payment.db.entity.Card;
import com.my.payment.db.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetCardsCommand implements Command{
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Card> cards;
        HttpSession s = request.getSession();
        User user = (User) s.getAttribute("currUser");
        DBManager dbManager = DBManager.getInstance();
        cards= dbManager.getCardsForUser(user);
        request.setAttribute("listCards",cards);
        LOG.trace("Obtained cards ==> "+cards);
        String forward = Path.CARDS_PAGE;
        return forward;
    }
}
