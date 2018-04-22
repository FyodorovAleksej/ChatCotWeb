package by.cascade.chatcot.connector;

import by.cascade.chatcot.mail.MailException;
import by.cascade.chatcot.mail.MailSender;
import by.cascade.chatcot.mail.RegistrantKeyMap;
import by.cascade.chatcot.storage.databaseprocessing.DataBaseException;
import by.cascade.chatcot.storage.databaseprocessing.user.UserAdapter;
import by.cascade.chatcot.storage.databaseprocessing.user.UserModel;
import by.cascade.chatcot.storage.databaseprocessing.user.mysql.UserMySqlAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for register page
 */
public class RegisterServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RegisterServlet.class);
    private static final String MAIL = "mailOffline.properties";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doOperation(req, resp);
        }
        catch (DataBaseException | MailException e) {
            LOGGER.catching(e);
        }
    }

    /**
     * performing registration
     *
     * @param request  - request from browser
     * @param response - response to browser
     * @throws ServletException - servlet exception
     * @throws IOException      - exception of write/read
     */
    private void doOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataBaseException, MailException {
        UserAdapter userAdapter = null;
        try {
            userAdapter = new UserMySqlAdapter();

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String repPassword = request.getParameter("repPassword");
            String email = request.getParameter("email");

            boolean equal = password.equals(repPassword);
            boolean check = !userAdapter.checkLogin(login);
            check = check && !RegistrantKeyMap.getInstance().checkLogin(login) &&
                    !RegistrantKeyMap.getInstance().checkEmail(email);
            if (equal) {
                if (check) {
                    String uuid = RegistrantKeyMap.getInstance().addValue(new UserModel(-1, email, login, password));
                    MailSender sender = new MailSender(MAIL);
                    sender.sendUrl("register: ","register" + "?uuid=" + uuid, email);
                    response.sendRedirect("http://localhost:8080");
                } else {
                    request.setAttribute("registerResult", "user is already login");
                    request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("registerResult", "passwords are not equals");
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            }
        } catch (DataBaseException e) {
            LOGGER.catching(e);
            throw new RuntimeException(e);
        }
        finally {
            if (userAdapter != null) {
                userAdapter.shutdown();
            }
        }
    }
}