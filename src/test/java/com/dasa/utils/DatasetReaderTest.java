package com.dasa.utils;

import com.dasa.domain.DadoPopulacional;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DatasetReaderTest {

    private DatasetReader reader;

    @Before
    public void init() {
        reader = createDatasetReader();
    }

    @Test
    public void readsDataset() {

        final List<DadoPopulacional> dadosDadoPopulacionalList = reader.readDataset();
        assertThat(dadosDadoPopulacionalList, hasSize(10));
    }

    private DatasetReader createDatasetReader() {
        try {
            final Path path = Paths.get("src/test/resources/datasets", "dados_populacionais.csv");
            Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            return new DatasetReader(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}