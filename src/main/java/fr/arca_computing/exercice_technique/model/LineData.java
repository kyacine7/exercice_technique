package fr.arca_computing.exercice_technique.model;

import java.sql.Date;
import java.sql.Time;

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
@Table(indexes = {@Index(name = "IDX_origine_name", columnList = "origine_name"),
		@Index(name = "IDX_date", columnList = "date")}) 
public class LineData {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int id;
	
	@Column(name = "origine_name")
	private String origineName;
	
	private int value;
	
	@Column(name = "date")
	private Date date;
	
	private Time time;

}
