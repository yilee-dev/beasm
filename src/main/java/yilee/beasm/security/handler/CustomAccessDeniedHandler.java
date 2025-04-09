package yilee.beasm.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Gson gson = new GsonHttpMessageConverter().getGson();
        String json = gson.toJson(Map.of("error", "ACCESS_DENIED"));

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
    }
}
