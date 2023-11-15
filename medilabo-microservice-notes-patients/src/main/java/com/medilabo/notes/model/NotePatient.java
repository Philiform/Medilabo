package com.medilabo.notes.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "notesPatients")
public class NotePatient {

	@Id
	private String id;
	
	@Indexed(unique = true)
	private Integer patientId;
	
	private List<Note> listNote = new ArrayList<>();
}
