package com.medilabo.notes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.notes.model.NotePatient;


@Repository
public interface NotePatientDao extends MongoRepository<NotePatient, String> {

	Optional<NotePatient> findByPatientId(Integer patientId);

	@Aggregation(pipeline = {
			"{$match : {'listNote._id' : UUID('?0')}}",
			"{$project : {'_id' : 0, 'patientId' : 1}}"})
	List<Integer> findPatientIdByNoteId(String uuid);

	@Aggregation(pipeline = {
			"{$match : {'listNote._id' : UUID('?0')}}"})
	Optional<NotePatient> findNotePatientByNoteId(String uuid);

}
