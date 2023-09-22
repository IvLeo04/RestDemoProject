package ru.leonov.RestProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leonov.RestProject.models.Measurements;
import ru.leonov.RestProject.models.Sensor;
import ru.leonov.RestProject.repositories.MeasurementsRepository;
import ru.leonov.RestProject.repositories.SensorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurements measurements) {
        measurementsRepository.save(measurements);
    }

    public List<Measurements> findAllRainyDays() {
        return measurementsRepository.findAll().stream().filter(Measurements::getRaining).collect(Collectors.toList());
    }
}
