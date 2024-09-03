package es.princip.getp.application.auth.service;

import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.security.exception.ExpiredTokenException;
import es.princip.getp.api.security.exception.InvalidTokenException;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;

@Slf4j
public abstract class JwtTokenService {

    protected static final String TOKEN_TYPE_KEY = "type";
    protected static final String BEARER_TYPE = "Bearer";

    protected final Long expireTime;
    protected final Key key;
    protected final String tokenType;
    protected final String header;
    protected final LoadMemberPort loadMemberPort;

    protected JwtTokenService(
        final Long expireTime,
        final String secretKey,
        final String tokenType,
        final String header,
        final LoadMemberPort loadMemberPort
    ) {
        this.expireTime = expireTime;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.tokenType = tokenType;
        this.header = header;
        this.loadMemberPort = loadMemberPort;
    }

    /**
     * JWT 토큰을 생성한다.
     *
     * @param member 토큰을 생성할 회원
     * @return 생성된 토큰
     */
    public String generateJwtToken(final Member member) {
        final long now = System.currentTimeMillis();
        final Date expireTime = new Date(now + this.expireTime);

        return Jwts.builder()
            .setSubject(member.getEmail().getValue())
            .claim(TOKEN_TYPE_KEY, tokenType)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * JWT 토큰을 파싱하여 Authentication 객체를 생성한다.
     *
     * @param token 토큰
     * @return 생성된 Authentication
     */
    public Authentication getAuthentication(final String token) {
        final Claims claims = validateAndParseToken(token);
        final Email email = Email.from(claims.getSubject());
        final Member member = loadMemberPort.loadBy(email);
        final PrincipalDetails principalDetails = new PrincipalDetails(member);

        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        }
    }

    /**
     * 토큰 검증과 함께 파싱을 수행한다.
     *
     * @param token 토큰
     * @throws ExpiredTokenException 토큰이 만료된 경우
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     * @return 파싱된 Claims
     */
    protected Claims validateAndParseToken(final String token) {
        try {
            final Claims claims = parseClaims(token);

            if (!claims.get(TOKEN_TYPE_KEY).equals(tokenType)) {
                throw new InvalidTokenException(tokenType);
            }

            if (claims.getExpiration().toInstant().isBefore(new Date().toInstant())) {
                throw new ExpiredTokenException(tokenType);
            }

            return claims;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new InvalidTokenException(tokenType);
        }
    }

    /**
     * HttpServletRequest에서 토큰을 추출한다. 토큰이 없는 경우 null을 반환한다.
     *
     * @param request HttpServletRequest
     * @return 추출된 토큰
     */
    public String resolveToken(final HttpServletRequest request) {
        final String header = request.getHeader(this.header);

        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.replace(BEARER_TYPE, "").trim();
        }

        return null;
    }
}
