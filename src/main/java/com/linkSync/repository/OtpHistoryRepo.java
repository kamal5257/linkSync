package com.linkSync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkSync.entity.OtpHistory;

@Repository
public interface OtpHistoryRepo
    extends
    JpaRepository<OtpHistory, Integer>
{
    OtpHistory findByTxnIdAndOtp( String txnId, String otp );

    OtpHistory findByTxnId( String txnId );
}
