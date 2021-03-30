package com.xafarr;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class EventToCsvWriter {

    public void writeEventsToCsv(List<Event> events, Path path) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            HeaderColumnNameMappingStrategy<Event> mappingStrategy =
                    new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(Event.class);
            StatefulBeanToCsv<Event> beanToCsv = new StatefulBeanToCsvBuilder<Event>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(events);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
