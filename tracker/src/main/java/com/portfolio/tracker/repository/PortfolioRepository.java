package com.portfolio.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.tracker.entity.Portfolio;
import com.portfolio.tracker.entity.User;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    Portfolio findByIdAndUser(Long id, User user);
    List<Portfolio> findAllByUser(User user);
}
