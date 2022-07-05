package com.yoga.bus.service;

import com.yoga.bus.models.Agency;
import com.yoga.bus.payload.request.AgencyRequest;

public interface AgencyService {

	Agency updatingAgency(Long id, AgencyRequest agencyDetail);

	Agency addNewAgency(AgencyRequest agencyRequest);

}
