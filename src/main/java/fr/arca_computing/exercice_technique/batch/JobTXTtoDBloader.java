package fr.arca_computing.exercice_technique.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import fr.arca_computing.exercice_technique.batch.itemProcessor.LoadTXTtoDBItemProcessor;
import fr.arca_computing.exercice_technique.batch.listener.JobCSVtoDBListener;
import fr.arca_computing.exercice_technique.batch.listener.WriterCSVtoDBListener;
import fr.arca_computing.exercice_technique.batch.utils.OneLineRead;
import fr.arca_computing.exercice_technique.model.LineData;
import fr.arca_computing.exercice_technique.repository.LineDataRepository;

@Configuration
@EnableBatchProcessing
public class JobTXTtoDBloader {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private LineDataRepository lineDataRepository;

	@Autowired
	public DataSource dataSource;


	//Will be used in ItemReader
	public LineMapper<OneLineRead> lineMapper() {
		DefaultLineMapper<OneLineRead> lineMapper = new DefaultLineMapper<OneLineRead>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("timestamp","value","origineName");
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<OneLineRead> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<OneLineRead>();
		beanWrapperFieldSetMapper.setTargetType(OneLineRead.class);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);	
		return lineMapper;
	}
	
	//Item Reader 
	@Bean
	public FlatFileItemReader<OneLineRead> flatFileReader(){
		
		FlatFileItemReader<OneLineRead> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setResource(new ClassPathResource("data-files/data.txt"));
		fileItemReader.setLineMapper(lineMapper());		
		
		fileItemReader.setLinesToSkip((int)lineDataRepository.count());
		
		return fileItemReader;
	}

	//Item Processor
	@Bean
	public LoadTXTtoDBItemProcessor flatFileProcessor(){
		return new LoadTXTtoDBItemProcessor();
	}

	
	//Will be used in writer 
	private DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/exercice_technique_db");
		dataSource.setUsername("root");
		dataSource.setPassword("password");
		
		return dataSource;
	}

	//Item Writer
	@Bean
	public ItemWriter<LineData> flatFileWriter(){

		JdbcBatchItemWriter<LineData> jdbcBatchItemWriter = new JdbcBatchItemWriter<LineData>();
		
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<LineData>());		
		jdbcBatchItemWriter.setSql(
				"INSERT INTO `line_data`"+
				"(`time`,`value`,`date`,`origine_name`) "+
				"VALUES(:time,:value, :date , :origineName); ");
		jdbcBatchItemWriter.setDataSource(dataSource);

		return jdbcBatchItemWriter;

	}


	// Step CSV to DB
	@Bean
	public Step stepCSVtoDB() {
		return stepBuilderFactory.get("stepCSVtoDB")
				.<OneLineRead,LineData> chunk(1000)
				.reader(flatFileReader())
				.processor(flatFileProcessor())
				.writer(flatFileWriter())
				.listener(witerListener())
				.build();
	}

	public ItemWriteListener<LineData>  witerListener() {
		return new WriterCSVtoDBListener();
	}

	// Job 
	@Bean
	public Job jobCSVtoDB() {
		
		return jobBuilderFactory.get("jobCSVtoDB")
				.start(stepCSVtoDB())
				.listener(jobListener())
				.build();
	}

	private JobExecutionListener jobListener() {
		return new JobCSVtoDBListener();
	}






}
