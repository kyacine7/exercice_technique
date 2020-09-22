package fr.arca_computing.exercice_technique.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(indexes = {@Index(name = "IDX_MYIDX1", columnList = "origine_name,timestamp,id")}) 
public class LineData {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	@Column(name = "origine_name")
	private String origineName;
	private Integer value;
	private Timestamp timestamp;

}
