package com.dasa.utils;

import com.dasa.domain.DadoPopulacional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatasetReader {

    private static final String SEPARATOR = ";";

    private final Reader source;

    public DatasetReader(final Reader source) {
        this.source = source;
    }

    public List<DadoPopulacional> readDataset() {
        try (BufferedReader datasetOrigin = new BufferedReader(source)) {

            Function<String, DadoPopulacional> mapper = line -> {
                String[] split = line.split(SEPARATOR);
                return new DadoPopulacional(split[0], split[1], split[2],split[3]);
            };
            return datasetOrigin
                    .lines()
                    .map(mapper)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}