package com.bol.core.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "connections" /* ,catalog = "bol_start" */) 
public class Connections {
	
	private Long id;
	private Long userId;
	private Long ConnectedUserId;
	private String isFollowed;
	private String isFollowBack;
	private String isConnected;
	private ConnUser user;
	private ConnUser connectedUser;
	public Connections() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Connections(Long id, Long userId, Long connectedUserId,String isFollowBack, String isFollowed, String isConnected) {
		super();
		this.id = id;
		this.userId = userId;
		this.ConnectedUserId = connectedUserId;
		this.isFollowed = isFollowed;
		this.isConnected = isConnected;
		this.isFollowBack=isFollowBack;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id" ,unique = true,nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="connected_user_id")
	public Long getConnectedUserId() {
		return ConnectedUserId;
	}
	public void setConnectedUserId(Long connectedUserId) {
		ConnectedUserId = connectedUserId;
	}
	
	@Column(name="is_followed",length = 255)
	public String getIsFollowed() {
		return isFollowed;
	}
	public void setIsFollowed(String isFollowed) {
		this.isFollowed = isFollowed;
	}
	
	@Column(name="is_connected",length = 255)
	public String getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public ConnUser getUser() {
		return user;
	}

	public void setUser(ConnUser user) {
		this.user = user;
	}

	@JsonProperty(access = Access.READ_ONLY)
	@Transient
	public ConnUser getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(ConnUser connectedUser) {
		this.connectedUser = connectedUser;
	}


	@Column(name="is_follow_back")
	public String getIsFollowBack() {
		return isFollowBack;
	}



	public void setIsFollowBack(String isFollowBack) {
		this.isFollowBack = isFollowBack;
	}



	@Override
	public String toString() {
		return "Connections [id=" + id + ", userId=" + userId + ", ConnectedUserId=" + ConnectedUserId + ", isFollowed="
				+ isFollowed + ", isFollowBack=" + isFollowBack + ", isConnected=" + isConnected + ", user=" + user
				+ ", connectedUser=" + connectedUser + "]";
	}
	
	

}
