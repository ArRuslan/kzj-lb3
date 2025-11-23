package ua.nure.kz.utils;

import jakarta.servlet.http.HttpServletRequest;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.service.UserService;

public class SessionUtil {
    public static UserDTO getUserFromSession(HttpServletRequest req, UserService service) {
        UserDTO userIdFromSess = (UserDTO)req.getSession().getAttribute("user");
        if(userIdFromSess != null) {
            UserDTO user = service.findUserById(userIdFromSess.getId());
            req.getSession().setAttribute("user", user);
            return user;
        }

        return null;
    }
}
