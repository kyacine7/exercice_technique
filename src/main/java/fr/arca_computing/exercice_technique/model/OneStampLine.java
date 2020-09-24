package fr.arca_computing.exercice_technique.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class OneStampLine {
	
	private String origineName;
	private int value;
	private long timestamp;

}
