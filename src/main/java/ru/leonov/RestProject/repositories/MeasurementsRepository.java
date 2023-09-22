package ru.leonov.RestProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.RestProject.models.Measurements;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {

}
