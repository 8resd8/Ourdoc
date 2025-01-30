package com.ssafy.ourdoc.domain.user.repository;

import com.ssafy.ourdoc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginIdAndPassword(String loginId, String password);
    Optional<User> findByLoginId(String loginId);

}
