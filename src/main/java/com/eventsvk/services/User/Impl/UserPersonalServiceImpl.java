package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.UserPersonal;
import com.eventsvk.repositories.UserPersonalRepository;
import com.eventsvk.services.User.UserPersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPersonalServiceImpl implements UserPersonalService {
    private final UserPersonalRepository userPersonalRepository;

    @Override
    public void saveUserPersonal(UserPersonal userPersonal) {
        if (userPersonal != null) {
            userPersonalRepository.save(userPersonal);
        }
    }
}
