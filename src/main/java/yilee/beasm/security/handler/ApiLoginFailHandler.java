package yilee.beasm.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ApiLoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Gson gson = new GsonHttpMessageConverter().getGson();
        String json = gson.toJson(Map.of("error", "Login failed"));

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
    }
}
