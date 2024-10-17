package com.hhplusconcert.user.usecase;

import com.hhplusconcert.user.repository.UserRepository;
import com.hhplusconcert.user.repository.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeCashService {

    private final UserRepository userRepository;

    public Output execute(Long userId, Input input) {
        User user = userRepository.findById(userId);

        user.chargeAmount(input.getAmount());
        userRepository.save(user);

        return new Output(user.getAmount());
    }

    @Getter
    @AllArgsConstructor
    public static class Input {
        Integer amount;
    }

    @Getter
    @AllArgsConstructor
    public static class Output {
        Integer cash;
    }
}
