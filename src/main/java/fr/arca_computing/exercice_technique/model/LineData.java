package fr.arca_computing.exercice_technique.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(indexes = {@Index(name = "IDX_MYIDX1", columnList = "origine_name")}) 
public class LineData {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "origine_name")
	private Origine origineName;
	
	private Integer value;
	
	@ManyToOne
    @JoinColumn(name = "date")
	private Dates dates;
	
	private Time time;

}
