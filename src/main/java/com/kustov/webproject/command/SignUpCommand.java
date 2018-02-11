package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.validator.SignUpValidator;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class SignUpCommand.
 */

public class SignUpCommand implements Command {

    /**
     * The receiver.
     */
    private UserReceiver receiver;

    /**
     * Instantiates a new sign up command.
     *
     * @param receiver the receiver
     */
    SignUpCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {

        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String pageMain = pageManager.getProperty("path_page_default");
        String thisPage = pageManager.getProperty("path_page_registration");

        String username = request.getParameter("username");
        User thisUser = (User)request.getSession().getAttribute("user");
        if (username == null || !PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, pageMain);
        }
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("firstName");
        String surname = request.getParameter("secondName");
        String birthday = request.getParameter("birthday");
        int countryId = Integer.parseInt(request.getParameter("country"));

        if (isAllValid(request, username, password, email, name, surname, birthday)) {
            try {
                if (!isNotDuplicatePasswordOrEmail(request, username, email)) {
                    setInformationToInput(username, password, email, name, surname, birthday, countryId, request);
                    return new CommandPair(CommandPair.DispatchType.FORWARD, thisPage);
                }
                User user = receiver.addUser(username, password, email, name, surname, birthday, countryId);
                request.getSession(true).setAttribute("user", user);
                request.getSession().removeAttribute("countries");
            } catch (ServiceException exc) {
                throw new CommandException();
            }
            page = pageMain;
            return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
        } else {
            page = thisPage;
            setInformationToInput(username, password, email, name, surname, birthday, countryId, request);
            return new CommandPair(CommandPair.DispatchType.FORWARD, page);
        }
    }

    /**
     * Sets the information to input.
     *
     * @param username    the username
     * @param password    the password
     * @param email       the email
     * @param name        the name
     * @param surname     the surname
     * @param releaseDate the release date
     * @param countryId   the country id
     * @param request     the request
     */
    private void setInformationToInput(String username, String password, String email, String name, String surname,
                                       String releaseDate, int countryId, HttpServletRequest request) {
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("email", email);
        request.setAttribute("name", name);
        request.setAttribute("surname", surname);
        request.setAttribute("releaseDate", releaseDate);
        request.setAttribute("country", countryId);
        request.setAttribute("isWrongInput", true);
    }

    /**
     * Checks if is not duplicate password or email.
     *
     * @param request  the request
     * @param username the username
     * @param email    the email
     * @return true, if is not duplicate password or email
     * @throws ServiceException the service exception
     */
    private boolean isNotDuplicatePasswordOrEmail(HttpServletRequest request,
                                                  String username, String email) throws ServiceException {
        boolean isNotDuplicate = true;
        if (receiver.findUserByUsername(username) != null) {
            isNotDuplicate = false;
            request.setAttribute("errorUsername", "This username is already taken");
        }
        if (receiver.findUserByEmail(email) != null) {
            isNotDuplicate = false;
            request.setAttribute("errorEmail", "This email is already taken");
        }
        return isNotDuplicate;
    }

    /**
     * Checks if is all valid.
     *
     * @param request  the request
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     * @return true, if is all valid
     */
    private boolean isAllValid(HttpServletRequest request, String username, String password, String email, String name,
                               String surname, String birthday) {
        SignUpValidator validator = new SignUpValidator();
        boolean isValid = true;
        if (!validator.checkUsername(username)) {
            request.setAttribute("errorUsername", "Username contains only letters, numbers and _ and starts with letter");
            isValid = false;
        }
        if (!validator.checkPassword(password)) {
            request.setAttribute("errorPassword", "Password contains only letters, numbers and _");
            isValid = false;
        }
        if (!validator.checkEmail(email)) {
            request.setAttribute("errorEmail", "Wrong email");
            isValid = false;
        }
        if (!validator.checkNameAndSurname(name, surname)) {
            request.setAttribute("errorName", "Wrong name or surname");
            isValid = false;
        }
        if (!validator.checkDate(birthday)) {
            request.setAttribute("errorBirthday", "Date needed to be in format yyyy-mm-dd");
            isValid = false;
        }
        return isValid;
    }
}
