package com.ywxt.Domain.Godaddy;

import com.ywxt.Annotation.MarkCloumn;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "godaddy_domain")
public class GodaddyDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private String userName;
    private String accessKeyId;
    private String domain;
    @MarkCloumn
    private String domainId;
    private String nameServers;
    private Date createdAt;
    private Date expires;
    private Date renewDeadline;
    private boolean renewable;
    private boolean renewAuto;
    private boolean transferProtected;
    private boolean expirationProtected;
    private boolean holdRegistrar;
    private boolean locked;
    private boolean privacy;
    // ACTIVE/CANCELLED_REDEEMABLE
    private String status;
    // 是否报警
    @Transient
    private boolean isAlertExpired = false;
    // 是否弃用
    @Transient
    private boolean isAlertMarked = false;


    public GodaddyDomain() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Date getRenewDeadline() {
        return renewDeadline;
    }

    public void setRenewDeadline(Date renewDeadline) {
        this.renewDeadline = renewDeadline;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public boolean isRenewAuto() {
        return renewAuto;
    }

    public void setRenewAuto(boolean renewAuto) {
        this.renewAuto = renewAuto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTransferProtected() {
        return transferProtected;
    }

    public void setTransferProtected(boolean transferProtected) {
        this.transferProtected = transferProtected;
    }

    public boolean isExpirationProtected() {
        return expirationProtected;
    }

    public void setExpirationProtected(boolean expirationProtected) {
        this.expirationProtected = expirationProtected;
    }

    public boolean isHoldRegistrar() {
        return holdRegistrar;
    }

    public void setHoldRegistrar(boolean holdRegistrar) {
        this.holdRegistrar = holdRegistrar;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public boolean isAlertExpired() {
        return isAlertExpired;
    }

    public void setAlertExpired(boolean alertExpired) {
        isAlertExpired = alertExpired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getNameServers() {
        return nameServers;
    }

    public void setNameServers(String nameServers) {
        this.nameServers = nameServers;
    }

    public boolean isAlertMarked() {
        return isAlertMarked;
    }

    public void setAlertMarked(boolean alertMarked) {
        isAlertMarked = alertMarked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
