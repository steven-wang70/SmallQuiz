package com.challenge.demo;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class SiteBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "site_id")
	private Long siteId;

	@Column(nullable = false, name = "site_uuid")
	private UUID siteUUID;

	@Column(nullable = false, name = "url")
	private String url;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public UUID getSiteUUID() {
		return siteUUID;
	}

	public void setSiteUUID(UUID siteUUID) {
		this.siteUUID = siteUUID;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final SiteBase site = (SiteBase) o;
		return Objects.equals(siteId, site.siteId) &&
				Objects.equals(siteUUID, site.siteUUID) &&
				Objects.equals(url, site.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(siteId, siteUUID, url);
	}
}
