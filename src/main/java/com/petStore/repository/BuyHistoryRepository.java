package com.petStore.repository;

import com.petStore.model.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {
}
