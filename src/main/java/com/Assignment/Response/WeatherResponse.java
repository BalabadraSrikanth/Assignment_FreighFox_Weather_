package com.Assignment.Response;
import java.util.*;

public class WeatherResponse {
	
	    private Main main;
	    private List<Weather> weather;

	    public static class Main {
	        private Double temp;
	        private Double humidity;
			public Double getTemp() {
				return temp;
			}
			public void setTemp(Double temp) {
				this.temp = temp;
			}
			public Double getHumidity() {
				return humidity;
			}
			public void setHumidity(Double humidity) {
				this.humidity = humidity;
			}

	        
	    }

	    public static class Weather {
	        private String description;

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

	       
	    }

		public Main getMain() {
			return main;
		}

		public void setMain(Main main) {
			this.main = main;
		}

		public List<Weather> getWeather() {
			return weather;
		}

		public void setWeather(List<Weather> weather) {
			this.weather = weather;
		}

	    
	}


