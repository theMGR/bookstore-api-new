package com.codesimple.bookstore.entity;

import java.time.LocalDateTime;

import com.codesimple.bookstore.common.Constant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String gender;
	private String emailId;
	private String phoneNumber;
	private String userType = Constant.USER_TYPE.NORMAL;
	private String password;
	private Boolean isActive = true;
	private Integer loginCount = 0;
	private String ssoType;
	private LocalDateTime loginAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public String getSsoType() {
		return ssoType;
	}

	public void setSsoType(String ssoType) {
		this.ssoType = ssoType;
	}

	public LocalDateTime getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(LocalDateTime loginAt) {
		this.loginAt = loginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	public void onSave() {
		// create at and update at
		LocalDateTime currentDateTime = LocalDateTime.now();

		this.createdAt = currentDateTime;
		this.updatedAt = currentDateTime;
	}

	@PostPersist
	public void onUpdate() {
		// update at
		LocalDateTime currentDateTime = LocalDateTime.now();

		this.updatedAt = currentDateTime;
	}

}
