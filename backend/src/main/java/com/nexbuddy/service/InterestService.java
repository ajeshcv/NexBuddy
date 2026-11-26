package com.nexbuddy.service;

import com.nexbuddy.dto.user.InterestDto;
import com.nexbuddy.entity.Interest;
import com.nexbuddy.repository.InterestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InterestService {

    private final InterestRepository interestRepository;

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Transactional(readOnly = true)
    public List<InterestDto> getAllInterests() {
        return interestRepository.findAllByOrderByCategoryAscInterestNameAsc().stream()
                .map(InterestService::toDto)
                .toList();
    }

    public static InterestDto toDto(Interest interest) {
        return new InterestDto(
                interest.getInterestId(),
                interest.getInterestName(),
                interest.getCategory(),
                interest.getIcon()
        );
    }
}
