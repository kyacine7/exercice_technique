package fr.arca_computing.exercice_technique.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import fr.arca_computing.exercice_technique.model.LineData;

public class WriterCSVtoDBListener implements  ItemWriteListener<LineData> {

	@Override
	public void beforeWrite(List<? extends LineData> items) {
//        System.out.println("Prepared to write");
		
	}

	@Override
	public void afterWrite(List<? extends LineData> items) {
//        System.out.println("Writen");		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends LineData> items) {
        System.out.println("on write error : " + exception.getMessage());		
	}

}
