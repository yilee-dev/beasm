package yilee.beasm.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import yilee.beasm.member.domain.dtos.MemberContext;
import yilee.beasm.utils.JwtUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ApiLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberContext memberContext = (MemberContext) authentication.getPrincipal();

        Map<String, Object> claims = memberContext.claims();

        String accessToken = JwtUtils.generateToken(claims, 10);
        String refreshToken = JwtUtils.generateToken(claims, 60 * 24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        Gson gson = new GsonHttpMessageConverter().getGson();
        String jsonClaimStr = gson.toJson(claims);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(jsonClaimStr);
        writer.close();
    }
}
