package com.kustov.webproject.controller;

import com.kustov.webproject.command.Command;
import com.kustov.webproject.command.CommandFactory;
import com.kustov.webproject.command.EmptyCommand;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.pool.DBConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

@WebServlet("/jsp/MainController")
public class Controller extends HttpServlet{
    private static String pathPageDefault;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("pages");
        pathPageDefault = resourceBundle.getString("path_page_main");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<Command> optionalCommand = CommandFactory.defineCommand(req.getParameter("command"));
            Command command = optionalCommand.orElse(new EmptyCommand());
            String page = command.execute(req);

            if (!page.isEmpty()) {
                RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect(pathPageDefault);
            }
        }catch (CommandException exc){
            req.getSession().setAttribute("error", "Wrong command");
            resp.sendRedirect(pathPageDefault);
        }
    }

    @Override
    public void destroy() {
//        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
//        for (int i = 0; i < connectionPool.poolSize(); i++){
//            connectionPool.closeConnection(connectionPool.getConnection());
//        }
    }
}
