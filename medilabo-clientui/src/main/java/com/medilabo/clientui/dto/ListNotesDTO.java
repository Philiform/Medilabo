package com.medilabo.clientui.dto;

import java.util.List;

import com.medilabo.clientui.beans.NoteBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListNotesDTO {

	private Integer patientId;
	
	private List<NoteBean> notes;
	
}
