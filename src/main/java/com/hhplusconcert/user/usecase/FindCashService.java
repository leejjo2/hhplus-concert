package com.hhplusconcert.user.usecase;

import com.hhplusconcert.user.repository.UserRepository;
import com.hhplusconcert.user.repository.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCashService {

    private final UserRepository userRepository;

    public Output execute(Long userId) {
        User user = userRepository.findById(userId);
        return new Output(user.getAmount());
    }

    @Getter
    @AllArgsConstructor
    public static class Output {
        Integer cash;
    }
}
