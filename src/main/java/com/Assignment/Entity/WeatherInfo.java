package com.Assignment.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherInfo {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String pincode;
	    private Double latitude;
	    private Double longitude;
	    private String weatherDescription;
	    private Double temperature;
	    private Double humidity;
	    private LocalDate forDate;
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPincode() {
			return pincode;
		}
		public void setPincode(String pincode) {
			this.pincode = pincode;
		}
		public Double getLatitude() {
			return latitude;
		}
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		public Double getLongitude() {
			return longitude;
		}
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		public String getWeatherDescription() {
			return weatherDescription;
		}
		public void setWeatherDescription(String weatherDescription) {
			this.weatherDescription = weatherDescription;
		}
		public Double getTemperature() {
			return temperature;
		}
		public void setTemperature(Double temperature) {
			this.temperature = temperature;
		}
		public Double getHumidity() {
			return humidity;
		}
		public void setHumidity(Double humidity) {
			this.humidity = humidity;
		}
		public LocalDate getForDate() {
			return forDate;
		}
		public void setForDate(LocalDate forDate) {
			this.forDate = forDate;
		}

	    
	


}
