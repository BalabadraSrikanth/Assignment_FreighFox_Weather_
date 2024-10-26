package com.Assignment.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Assignment.Entity.WeatherInfo;

@Repository	
	public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
	    Optional<WeatherInfo> findByPincodeAndForDate(String pincode, LocalDate forDate);
	}


