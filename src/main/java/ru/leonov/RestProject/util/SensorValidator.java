package ru.leonov.RestProject.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.leonov.RestProject.dto.SensorDTO;
import ru.leonov.RestProject.models.Sensor;
import ru.leonov.RestProject.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        if(sensorService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name", "", "Сенсор с таким именем уже есть");
    }
}
