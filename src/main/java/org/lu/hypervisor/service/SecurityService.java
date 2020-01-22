package org.lu.hypervisor.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private SubjectService subjectService;
    private String secret;

    public SecurityService(SubjectService subjectService, Environment env) {
        this.subjectService = subjectService;
        secret = env.getProperty("HMAC256.secret");
    }

    public String tokenCreate(Subject subject) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        return JWT.create()
                .withSubject(subject.getId().toString())
                .withClaim("name", subject.getName())
                .withClaim("role", subject.getRole())
                .sign(algorithm);
    }

    public Subject tokenVerify(String token) throws NotAuthorizedException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return subjectService.getSubjectById(Long.parseLong(jwt.getSubject())).get();
        } catch (JWTVerificationException exception) {
            throw new NotAuthorizedException();
        }
    }
}
