package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.UserCounters;
import com.eventsvk.repositories.UserCountersRepository;
import com.eventsvk.services.User.UserCountersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCountersServiceImpl implements UserCountersService {
    public final UserCountersRepository userCountersRepository;

    @Override
    @Transactional
    public void saveUserCounters(UserCounters userCounters) {
        if (userCounters != null) {
            userCountersRepository.save(userCounters);
        }
    }
}
