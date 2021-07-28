package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Folders;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<Folders, Folders> {

    private static final Map<String, String> DEPT_NAMES =
            new HashMap<>();

    public Processor() {

    }

    @Override
    public Folders process(Folders folder) throws Exception {
        String folderName = folder.getName();

        folder.setName(folderName);
        System.out.println(folderName+" is processing .......");

        return folder;
    }
}
