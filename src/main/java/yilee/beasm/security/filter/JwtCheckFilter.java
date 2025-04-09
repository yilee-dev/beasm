package yilee.beasm.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import yilee.beasm.member.domain.dtos.MemberContext;
import yilee.beasm.member.domain.dtos.MemberDto;
import yilee.beasm.utils.JwtUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtCheckFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String method = request.getMethod();
        if (method.contains("OPTIONS")) {
            return true;
        }

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/members/")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorization = request.getHeader("Authorization");
            String accessToken = authorization.substring(7);
            Map<String, Object> claims = JwtUtils.validateToken(accessToken);

            String email = (String) claims.get("email");
            String nickname = (String) claims.get("nickname");
            String password = (String) claims.get("password");
            List<String> roles = (List<String>) claims.get("roles");

            MemberDto memberDto = new MemberDto(email, nickname, password, roles);
            List<GrantedAuthority> memberRoles = roles.stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole)).collect(Collectors.toList());

            MemberContext memberContext = new MemberContext(memberDto, memberRoles);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());

            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            Gson gson = new GsonHttpMessageConverter().getGson();
            String msg = gson.toJson(Map.of("error", "Invalidate Token"));
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }

    }
}
