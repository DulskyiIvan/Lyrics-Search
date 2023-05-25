package edu.geekhub.example.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ExceptionHandlerController implements ErrorController {

    private final ExceptionHandlerService exceptionHandlerService;

    public ExceptionHandlerController(ExceptionHandlerService exceptionHandlerService) {
        this.exceptionHandlerService = exceptionHandlerService;
    }

    @GetMapping
    public String handleError(HttpServletRequest request) {
        return exceptionHandlerService.differentStatusCode(request);
    }
}
