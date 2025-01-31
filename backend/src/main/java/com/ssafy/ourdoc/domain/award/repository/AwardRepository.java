package com.ssafy.ourdoc.domain.award.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.award.entity.Award;

public interface AwardRepository extends JpaRepository<Award, Long>, AwardQueryRepository {
}
