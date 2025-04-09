package yilee.beasm.member.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yilee.beasm.utils.JwtUtils;

import java.util.Date;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ApiRefreshController {

    @RequestMapping("/api/members/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authorization, @RequestParam("refreshToken") String refreshToken) {
        if (refreshToken == null) {
            throw new RuntimeException("NULL REFRESH");
        }

        if (authorization == null) {
            throw new RuntimeException("INVALID STRING");
        }

        log.info("refreshToken={}", refreshToken);

        String accessToken = authorization.substring(7);

        if (checkExpiredToken(accessToken) == false) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        Map<String, Object> claims = JwtUtils.validateToken(refreshToken);
        String newAccessToken = JwtUtils.generateToken(claims, 10);

        String newRefreshToken = checkTime((Long) claims.get("exp")) == true ? JwtUtils.generateToken(claims, 60 * 24) : refreshToken;

        Map<String, Object> tokenResult = Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
        return tokenResult;
    }

    private boolean checkTime(Long exp) {
        Date expDate = new Date((long) exp * (1000));

        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        return leftMin < 5;
    }

    private boolean checkExpiredToken(String accessToken) {
        try {
            JwtUtils.validateToken(accessToken);
        } catch (Exception exception) {
            if (exception.getClass() == ExpiredJwtException.class) {
                return true;
            }
        }
        return false;
    }


}
