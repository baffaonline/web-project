package com.kustov.webproject.controller;

import com.kustov.webproject.command.Command;
import com.kustov.webproject.command.CommandFactory;
import com.kustov.webproject.command.EmptyCommand;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/jsp/MainController")
public class Controller extends HttpServlet {
    private static String pathPageDefault;
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PropertyManager pageManager = new PropertyManager("pages");
        pathPageDefault = pageManager.getProperty("path_page_default");
        LOGGER.log(Level.INFO, "Ok");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        commandDefine(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        commandDefine(req, resp);
    }

    private void commandDefine(HttpServletRequest req, HttpServletResponse resp){
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
        } catch (CommandException | IOException | ServletException exc) {
            req.getSession().setAttribute("error", exc.getMessage());
            LOGGER.log(Level.ERROR, exc.getMessage());
            System.out.println(exc.getMessage());
            try {
                resp.sendRedirect(pathPageDefault);
            } catch (IOException exception) {
                this.destroy();
            }
        }
    }

    @Override
    public void destroy() {
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        for (int i = 0; i < connectionPool.poolSize(); i++){
            try {
                connectionPool.closeConnection(connectionPool.getConnection());
            }catch (ConnectionException exc){
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}
