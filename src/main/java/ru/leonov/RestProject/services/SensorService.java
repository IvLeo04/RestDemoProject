package ru.leonov.RestProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.leonov.RestProject.models.Sensor;
import ru.leonov.RestProject.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

}
