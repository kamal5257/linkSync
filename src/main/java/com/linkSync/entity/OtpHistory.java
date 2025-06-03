package com.linkSync.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "otp_history")
public class OtpHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;
    @Column(name = "receiver_detail")
    private String  receiverDetail;
    @Column(name = "otp")
    private String  otp;
    @Column(name = "txn_id")
    private String  txnId;
    @Column(name = "expiry")
    private Date    expiry;
    @Column(name = "verified")
    private Boolean verified;
}
