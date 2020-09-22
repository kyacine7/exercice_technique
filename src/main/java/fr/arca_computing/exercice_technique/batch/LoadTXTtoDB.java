package fr.arca_computing.exercice_technique.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import fr.arca_computing.exercice_technique.itemProcessor.LoadTXTtoDBItemProcessor;
import fr.arca_computing.exercice_technique.model.LineData;
import fr.arca_computing.exercice_technique.model.OneStampLine;
import fr.arca_computing.exercice_technique.repository.LineDataRepository;

@Configuration
@EnableBatchProcessing
public class LoadTXTtoDB {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private LineDataRepository lineDataRepository;
	
	@Autowired
	public DataSource dataSource;

	//Will be used in ItemReader
	public LineMapper<OneStampLine> lineMapper() {
		DefaultLineMapper<OneStampLine> lineMapper = new DefaultLineMapper<OneStampLine>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("timestamp","value","origineName");
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<OneStampLine> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<OneStampLine>();
		beanWrapperFieldSetMapper.setTargetType(OneStampLine.class);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);	
		return lineMapper;
	}
	
	//Item Reader 
	@Bean
	public FlatFileItemReader<OneStampLine> flatFileReader(){
		
		FlatFileItemReader<OneStampLine> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setName("FFIR1");
		fileItemReader.setResource(new ClassPathResource("data-files/data.txt"));
		fileItemReader.setLineMapper(lineMapper());		

		return fileItemReader;
	}

	//Item Processor
	@Bean
	public LoadTXTtoDBItemProcessor flatFileProcessor(){
		return new LoadTXTtoDBItemProcessor();
	}

	
	//Will be used in writer 



	//Item Writer
	@Bean
	public ItemWriter<LineData> flatFileWriter(){

		JdbcBatchItemWriter<LineData> writer = new JdbcBatchItemWriter<LineData>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<LineData>());
		writer.setSql("INSERT INTO line_data(origine_name,value,timestamp) VALUES (:origineName,:value,:timestamp)");
		writer.setDataSource(dataSource);
		
		return writer;

//		return new ItemWriter<LineData>() {
//			@Override
//			public void write(List<? extends LineData> items) throws Exception {
//				lineDataRepository.saveAll(items);
//			}};
	}


	// Step CSV to DB
	@Bean
	public Step stepCSVtoDB() {
		return stepBuilderFactory.get("stepCSVtoDB")
				.<OneStampLine,LineData> chunk(10000)
				.reader(flatFileReader())
				.processor(flatFileProcessor())
				.writer(flatFileWriter())
				.build();
	}

	// Job 
	@Bean
	public Job jobCSVtoDB() {
		
		return jobBuilderFactory.get("jobCSVtoDB")
//				.incrementer(new RunIdIncrementer())
//				.preventRestart()
				.flow(stepCSVtoDB())
				.end()
				.build();
	}






}
