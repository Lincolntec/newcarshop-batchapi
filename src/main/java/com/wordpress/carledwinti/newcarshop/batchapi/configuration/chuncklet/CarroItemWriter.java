package com.wordpress.carledwinti.newcarshop.batchapi.configuration.chuncklet;

import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import com.wordpress.carledwinti.newcarshop.batchapi.repository.CarroRepository;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.CsvFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class CarroItemWriter implements ItemWriter<Carro>, StepExecutionListener{

    private static final Logger    LOGGER = LoggerFactory.getLogger(CarroItemWriter.class);
    private CsvFileUtils csvSavedCards;

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        this.csvSavedCards = new CsvFileUtils("savadCards", false);

        LOGGER.info("Finalizando WRITER...");

    }

    @Override
    public void write(List<? extends Carro> carroOutList) {

        List<? extends  Carro> savedCarroList = this.carroRepository.saveAll(carroOutList);

        savedCarroList.stream().forEach(carro -> {
            try {
                this.csvSavedCards.writer(carro);
            }catch (IOException e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        try {
            this.csvSavedCards.closeWriter();
        }catch (IOException e){

        }
        return null;
    }


}
