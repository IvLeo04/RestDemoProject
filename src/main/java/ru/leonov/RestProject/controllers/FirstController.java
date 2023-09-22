package ru.leonov.RestProject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.leonov.RestProject.dto.MeasurementDTO;
import ru.leonov.RestProject.dto.SensorDTO;
import ru.leonov.RestProject.models.Measurements;
import ru.leonov.RestProject.models.Sensor;
import ru.leonov.RestProject.services.MeasurementsService;
import ru.leonov.RestProject.services.SensorService;
import ru.leonov.RestProject.util.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class FirstController {

    private final SensorService sensorService;
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    private final SensorValidator sensorValidator;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public FirstController(SensorService sensorService, MeasurementsService measurementsService, ModelMapper modelMapper, SensorValidator sensorValidator, MeasurementValidator measurementValidator) {
        this.sensorService = sensorService;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/sensor")
    public List<SensorDTO> findAllSensor() {
        return sensorService.findAll().stream()
                .map(sensor -> modelMapper.map(sensor, SensorDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/measurements")
    public List<MeasurementDTO> findAllMeasurement() {
        return measurementsService.findAll().stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());
    }
    @PostMapping("/sensor/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fieldError : errors) {
                stringBuilder.append("Name: ")
                        .append(fieldError.getField())
                        .append(", message = ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorNotCreatedException(stringBuilder.toString());
        }

        sensorService.save(modelMapper.map(sensorDTO, Sensor.class));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                      BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fieldError : errors) {
                stringBuilder.append("Name: ")
                        .append(fieldError.getField())
                        .append(", message = ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(stringBuilder.toString());
        }
        measurementsService.save(modelMapper.map(measurementDTO, Measurements.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/measurements/rainyDaysCount")
    public List<MeasurementDTO> findAllRainyDays() {
        return measurementsService.findAllRainyDays().stream()
                .map(measurements -> modelMapper.map(measurements, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handlerExceptionSensor(SensorNotCreatedException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handlerExceptionMeasurement(MeasurementNotCreatedException e) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
