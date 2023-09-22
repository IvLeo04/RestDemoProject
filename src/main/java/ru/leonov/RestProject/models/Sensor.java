package ru.leonov.RestProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {
    @Id
    @Column(name = "name")
    @NotNull(message = "Название сенсора не может быть пустым")
    @Size(min = 3, max = 30, message = "Название сенсора должно быть размера от 3 до 30 символов")
    private String name;

    @OneToMany(mappedBy = "sensor")
    @JsonIgnore
    private List<Measurements> list;

    public Sensor() {}

    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurements> getList() {
        return list;
    }

    public void setList(List<Measurements> list) {
        this.list = list;
    }
}
