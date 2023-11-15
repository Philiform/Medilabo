package com.medilabo.disease.assessment.service;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface DiseasesService.
 */
public interface DiseasesService {

	/**
	 * Creates the data.
	 */
	void createData();
	
	/**
	 * Gets the all terms by disease.
	 *
	 * @param disease the disease
	 * @return the all terms by disease
	 */
	List<String> getAllTermsByDisease(final String disease);

}
