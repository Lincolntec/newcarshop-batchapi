package com.wordpress.carledwinti.newcarshop.batchapi.configuration.chuncklet;

import com.wordpress.carledwinti.newcarshop.batchapi.dto.CarroDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;

import java.util.Iterator;
import java.util.List;

public class CarroItemReader implements ItemReader<CarroDto>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroItemReader.class);

    private Iterator<CarroDto> carroInIterator;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        ExecutionContext ec = stepExecution.getJobExecution().getExecutionContext();
        List<CarroDto> carroDtoList = (List<CarroDto>) ec.get("carroInList");
        this.carroInIterator = carroDtoList.iterator();
        LOGGER.info("Iniciando READER");

    }

    @Override
    public CarroDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(this.carroInIterator != null && this.carroInIterator.hasNext()){
            return  this.carroInIterator.next();
        }
        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info(("Finalizando o READER"));
        return ExitStatus.COMPLETED;
    }


}
