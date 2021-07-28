package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.model.Folders;
import com.techprimers.springbatchexample1.model.User;
import com.techprimers.springbatchexample1.utils.FileListFromFolder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableBatchProcessing
@Import(DataSourceAutoConfiguration.class)
public class SpringBatchConfig {
    
    @Autowired
    FileListFromFolder fileListFromFolder;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Folders> itemReader,
                   ItemProcessor<Folders, Folders> itemProcessor,
                   ItemWriter<Folders> itemWriter
    ) {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.afterPropertiesSet();

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Folders, Folders>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor)
                .build();
        System.out.println(step.toString());


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

   /* @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("myJob").start(step1).build();
    }*/

   /* @Bean
    protected Step step1(ItemReader<Folders> reader,
                         ItemProcessor<Folders, Folders> processor,
                         ItemWriter<Folders> writer) {
        return steps.get("ETL-file-load")
                .<Folders, Folders> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }*/



    @Bean
    public FlatFileItemReader<Folders> itemReader() throws IOException {



        String maindirpath = "C:\\Users\\ankit\\Documents\\FIFA 21";
        String destPath = "D:\\Study\\RCM-CLI BatchJobs\\src\\main\\resources\\out.txt";

        System.out.println("ItemReader......"+maindirpath + "   " + destPath);

        fileListFromFolder.createWriters(maindirpath,destPath);

        FlatFileItemReader<Folders> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("D:\\Study\\RCM-CLI BatchJobs\\src\\main\\resources\\out.txt"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLineMapper(lineMapper());

        System.out.println(flatFileItemReader.toString());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Folders> lineMapper() {

        DefaultLineMapper<Folders> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter("\n");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name");

        BeanWrapperFieldSetMapper<Folders> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Folders.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
