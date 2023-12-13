package es.upm.dit.apsv.gpsenabledtruckssimulator;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import java.util.function.Supplier;

import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.util.ResourceUtils;

@SpringBootApplication

public class GPSEnabledTrucksSimulatorApplication {

	private static final Logger log =

			LoggerFactory.getLogger(GPSEnabledTrucksSimulatorApplication.class);

	private static int n = 0;

	private static List<String> messages = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		SpringApplication.run(GPSEnabledTrucksSimulatorApplication.class, args);

		log.info("Generación de trazas activa...");

		File file = ResourceUtils.getFile("classpath:tracesJSON.json");

		System.out.println("Reading from: tracesJSON.json");

		FileReader fr = new FileReader(file);

		messages = new BufferedReader(fr).lines()

				.collect(Collectors.toList());

		fr.close();

	}

	@Bean("producer")

	public Supplier<String> checkTrace() {

		Supplier<String> traceSupplier = () -> {

			if (messages.size() > 0) {

				return messages.get(n++ % messages.size());

			}

			else
				return null;

		};

		return traceSupplier;

	}

}