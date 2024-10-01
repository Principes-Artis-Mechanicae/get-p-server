package es.princip.getp.application.project.apply;

import es.princip.getp.api.security.exception.ExpiredTokenException;
import es.princip.getp.api.security.exception.InvalidTokenException;
import es.princip.getp.application.project.apply.command.ApproveTeammateCommand;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TeamApprovalTokenService {

    private static final String TOKEN_TYPE = "팀원 승인";
    private static final String APPLICATION_ID_KEY = "applicationId";
    private static final String TEAMMATE_ID_KEY = "teammateId";

    private final Long expireTime;
    private final Key key;

    @Autowired
    public TeamApprovalTokenService(
        @Value("${spring.jwt.teammate-approval-token.expire-time}") final Long expireTime,
        @Value("${spring.jwt.secret}") final String secretKey
    ) {
        this.expireTime = expireTime;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generate(final ProjectApplicationId applicationId, final PeopleId teammateId) {
        final long now = System.currentTimeMillis();
        final Date expireTime = new Date(now + this.expireTime);

        return Jwts.builder()
            .claim(APPLICATION_ID_KEY, applicationId.getValue())
            .claim(TEAMMATE_ID_KEY, teammateId.getValue())
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private Claims parseClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (final ExpiredJwtException exception) {
            throw new ExpiredTokenException(TOKEN_TYPE);
        }
    }

    public ApproveTeammateCommand parse(final String token) {
        try {
            final Claims claims = parseClaims(token);
            final Long applicationId = claims.get(APPLICATION_ID_KEY, Long.class);
            final Long teammateId = claims.get(TEAMMATE_ID_KEY, Long.class);
            return new ApproveTeammateCommand(new ProjectApplicationId(applicationId), new PeopleId(teammateId));
        } catch (final JwtException | IllegalArgumentException exception) {
            throw new InvalidTokenException(TOKEN_TYPE);
        }
    }
}
