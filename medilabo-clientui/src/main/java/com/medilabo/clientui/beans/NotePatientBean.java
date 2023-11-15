package com.medilabo.clientui.beans;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotePatientBean {

	private String id;
	
	private Integer patientId;
	
	private List<NoteBean> listNote = new ArrayList<>();
}
