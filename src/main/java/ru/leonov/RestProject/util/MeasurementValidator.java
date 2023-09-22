package ru.leonov.RestProject.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.leonov.RestProject.dto.MeasurementDTO;
import ru.leonov.RestProject.services.SensorService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
       MeasurementDTO measurementsDTO = (MeasurementDTO) target;
       boolean bool = sensorService.findByName(measurementsDTO.getSensor().getName()).isPresent();
       if(!bool) {
           errors.rejectValue("sensor", "", "Сенсора с таким названием нету");
       }
    }
}
