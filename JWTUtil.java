package Security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil
{


    private final String secret = "MyJwtSecretKey1234567890";

    private final long expiration = 3600000;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUsername(String token)
    {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateToken(String token)
    {
        try
        {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            return true;

        }
        catch (Exception e)
        {

            return false;

        }
    }


}
