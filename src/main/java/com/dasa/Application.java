package com.dasa;

import com.dasa.domain.DadoPopulacional;
import com.dasa.repository.DadosPopulacionaisRepository;
import com.dasa.utils.DatasetReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Application {


    @Autowired
    DadosPopulacionaisRepository dadosPopulacionaisRepository;

    @PostConstruct
    public void init() {

        final Path path = Paths.get("src/main/resources/datasets", "dados_populacionais.csv");
        try {
            Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));

            DatasetReader datasetReader = new DatasetReader(reader);

            final List<DadoPopulacional> dadoPopulacionals = datasetReader.readDataset();

            dadosPopulacionaisRepository.deleteAll();

            for(DadoPopulacional d : dadoPopulacionals) {
                dadosPopulacionaisRepository.save(d);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao popular a Base de Dados", e);
        }


    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}