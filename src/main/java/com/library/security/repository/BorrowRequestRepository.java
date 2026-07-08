package com.library.security.repository;

import com.library.security.model.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {

    List<BorrowRequest> findByRequester_Id(Long userId);

    List<BorrowRequest> findByStatus(BorrowRequest.Status status);
}
