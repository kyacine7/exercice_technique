package fr.arca_computing.exercice_technique.batch.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OneLineRead {
	
	private String origineName;
	private int value;
	private String timestamp;

}
