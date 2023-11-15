package com.medilabo.clientui.dto;

import com.medilabo.clientui.beans.NoteBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteDTO {

	private Integer patientId;
	
	private NoteBean noteBean;
	
}
