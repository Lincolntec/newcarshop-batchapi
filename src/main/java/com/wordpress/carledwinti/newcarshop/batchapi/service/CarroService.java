package com.wordpress.carledwinti.newcarshop.batchapi.service;

import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import com.wordpress.carledwinti.newcarshop.batchapi.repository.CarroRepository;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroService.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private CarroRepository carroRepository;

    public BatchStatus batchExecute(){

        LOGGER.info("Iniciado o Job" + DateUtils.getNow());

        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));

        try {

            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(map));

            while (jobExecution.isRunning()){
                LOGGER.info("Job em execução");
            }

            LOGGER.info(DateUtils.getNow());

            return jobExecution.getStatus();

        }catch (Exception e){

            LOGGER.info("Falha ao tentar executar o JOB " + e.getMessage());

            return BatchStatus.FAILED;

        }

    }
    public List<Carro> findAll(){
        return carroRepository.findAll();
    }

}
