package by.cascade.chatcot.connector;

import by.cascade.chatcot.storage.databaseprocessing.DataBaseException;
import by.cascade.chatcot.storage.databaseprocessing.user.UserAdapter;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doOperation(req, resp);
        }
        catch (DataBaseException e) {
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
    private void doOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataBaseException {
        UserAdapter userAdapter = null;
        try {
            userAdapter = new UserMySqlAdapter();

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String repPassword = request.getParameter("repPassword");

            boolean equal = password.equals(repPassword);
            boolean check = !userAdapter.checkLogin(login);
            if (equal) {
                if (check) {
                    userAdapter.addUser(login, password);
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