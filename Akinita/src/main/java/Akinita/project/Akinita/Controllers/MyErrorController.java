package Akinita.project.Akinita.Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) { //Μέθοδος για τα controllers
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) { //Επιστροφή σελίδας για error 404
                return "error/error-404";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) { //Επιστροφή σελίδας για error 403
                return "error/error-403";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) { //Επιστροφή σελίδας για error 404
                return "error/error-500";
            }
        }
        return "error/error";
    }

}
