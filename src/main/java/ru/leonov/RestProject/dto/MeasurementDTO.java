package ru.leonov.RestProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.leonov.RestProject.models.Sensor;

import java.util.Optional;

public class MeasurementDTO {

    @Min(value = -100)
    @Max(value = 100)
    @NotNull(message = "Данное поле не может быть пустым")
    private float value;

    @NotNull(message = "Данное поле не может быть пустым")
    private boolean raining;


    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
