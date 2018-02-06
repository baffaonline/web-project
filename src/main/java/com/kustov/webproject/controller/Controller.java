package com.kustov.webproject.controller;

import com.kustov.webproject.command.Command;
import com.kustov.webproject.command.CommandFactory;
import com.kustov.webproject.command.CommandPair;
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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/MainController")
@MultipartConfig
public class Controller extends HttpServlet {
    private static String pathPageDefault;
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PropertyManager pageManager = new PropertyManager("pages");
        pathPageDefault = pageManager.getProperty("path_page_default");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        commandDefine(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        commandDefine(req, resp);
    }

    private void commandDefine(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF8");
            boolean isUpdated = (boolean) req.getSession().getAttribute("isUpdated");
            if (!isUpdated) {
                Optional<Command> optionalCommand = CommandFactory.defineCommand(req.getParameter("command"));
                Command command = optionalCommand.orElse(new EmptyCommand());
                CommandPair commandPair = command.execute(req);
                if (commandPair.getDispatchType() == CommandPair.DispatchType.FORWARD) {
                    RequestDispatcher dispatcher;
                    dispatcher = req.getRequestDispatcher(commandPair.getPage());
                    dispatcher.forward(req, resp);
                } else {
                    String page = commandPair.getPage();
                    PropertyManager propertyManager = new PropertyManager("pages");
                    if (!propertyManager.getProperty("path_page_default").equals(page)) {
                        req.getSession().setAttribute("isUpdated", true);
                    }
                    req.getSession().setAttribute("pagePath", page);
                    resp.sendRedirect(page);
                }
            } else {
                req.getSession().setAttribute("isUpdated", false);
                String page = (String) req.getSession().getAttribute("pagePath");
                RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                dispatcher.forward(req, resp);
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
        for (int i = 0; i < connectionPool.poolSize(); i++) {
            try {
                connectionPool.closeConnection(connectionPool.getConnection());
            } catch (ConnectionException exc) {
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}
