package com.challenge.demo;

import java.util.ArrayList;
import java.util.List;

public class SiteDTO extends SiteBase{
	
	public static SiteDTO build(SitePersist site) {
		final SiteDTO obj = new SiteDTO();
		obj.setSiteId(site.getSiteId());
		obj.setSiteUUID(site.getSiteUUID());
		obj.setUrl(site.getUrl());
		obj.setUpdatedAt(site.getUpdatedAt());
		obj.setCreatedAt(site.getCreatedAt());

		return obj;
	}

	public static List<SiteDTO> build(List<SitePersist> sites) {
		final List<SiteDTO> ret = new ArrayList<>();

		for (SitePersist site : sites) {
			ret.add(build(site));
		}

		return ret;
	}

	public static SitePersist transform(final SiteDTO incomingSite) {
		final SitePersist newSite = new SitePersist();
		newSite.setSiteId(incomingSite.getSiteId());
		newSite.setSiteUUID(incomingSite.getSiteUUID());
		newSite.setUrl(incomingSite.getUrl());

		return newSite;
	}

}
