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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    
    @Autowired
    FileListFromFolder fileListFromFolder;
    @Autowired
    FileWriter myWriter;
    @Autowired
    PrintWriter printWriter;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Folders> itemReader,
                   ItemProcessor<Folders, Folders> itemProcessor,
                   ItemWriter<Folders> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Folders, Folders>chunk(3)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        System.out.println(step.toString());


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<Folders> itemReader() throws IOException {

        String maindirpath = "C:\\Users\\ankit\\Documents\\Adobe";
        FileWriter myWriter = new FileWriter("C:\\Users\\ankit\\Downloads\\com.batchProcessing 2\\FileSystem\\src\\out.txt");
        printWriter = new PrintWriter(myWriter);
        File maindir = new File(maindirpath);

        if (maindir.exists() && maindir.isDirectory()) {
            File arr[] = maindir.listFiles();
            fileListFromFolder.RecursivePrint(arr, 0, 0,printWriter);
            myWriter.close();
            printWriter.close();

        }

        FlatFileItemReader<Folders> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("D:\\Study\\spring-batch-example-1\\src\\main\\resources\\FileName.txt"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Folders> lineMapper() {

        DefaultLineMapper<Folders> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter("\t");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name");

        BeanWrapperFieldSetMapper<Folders> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Folders.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
