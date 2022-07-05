package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.Agency;

public interface AgencyService {
	Agency saveAgency(Agency agency);
	
	Agency getAgencyById(Long id);
	
	List<Agency> getAllAgency();

	List<Agency> getAllAgencyByOwnerId(Long ownerId);
	
	void deleteAgency(Long id);
}
