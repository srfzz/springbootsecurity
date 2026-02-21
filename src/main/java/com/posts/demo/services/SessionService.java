package com.posts.demo.services;


import com.posts.demo.entities.SessionEntity;
import com.posts.demo.entities.UserEntity;
import com.posts.demo.repository.SessionRepository;
import com.posts.demo.repository.UserRepository;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    final Integer MAX_ALLOWED_SESSIONS = 2;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public void generateNewSession(UserEntity user,String refreshToken) {
        List<SessionEntity> sessions = sessionRepository.findByUser(user);
        if(sessions.size() >= MAX_ALLOWED_SESSIONS) {
            sessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity oldestSession = sessions.getFirst();
            sessionRepository.delete(oldestSession);
        }

        SessionEntity newSession=SessionEntity.builder().refreshToken(refreshToken).user(user).lastUsedAt(LocalDate.now()).build();
        sessionRepository.save(newSession);
    }
    public void validateSession(String refreshToken) {
        SessionEntity session=sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("Session Not Found"));
        session.setLastUsedAt(LocalDate.now());
        sessionRepository.save(session);
    }
}
