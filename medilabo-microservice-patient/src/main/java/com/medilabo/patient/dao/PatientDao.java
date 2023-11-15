package com.medilabo.patient.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.patient.model.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Integer> {

}
