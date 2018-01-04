package com.kustov.tagexample.controller;

import com.kustov.tagexample.command.Command;
import com.kustov.tagexample.command.CommandFactory;
import com.kustov.tagexample.command.EmptyCommand;
import com.kustov.tagexample.exception.CommandException;

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
