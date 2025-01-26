package com.holiday.request.repository;

import com.holiday.request.dto.model.DayDto;
import com.holiday.request.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
    List<Day> findByDateBetween(Date startDate, Date endDate);
    List<Day> findAllByOrderByDateAsc();
}