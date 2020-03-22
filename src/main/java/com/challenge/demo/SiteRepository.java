package com.challenge.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SiteRepository extends JpaRepository<SitePersist, Long> {

	@Query(value = "SELECT s.* FROM site s WHERE s.site_uuid = ?1", nativeQuery = true)
	SitePersist findByUuid(UUID siteUUID);
}