package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Statistics {
	
	private LocalDate min;
	private LocalDate max;
	private Float avg;
	
	public Statistics(LocalDate min, LocalDate max, Float avg) {
		super();
		this.min = min;
		this.max = max;
		this.avg = avg;
	}

	public LocalDate getMin() {
		return min;
	}

	public void setMin(LocalDate min) {
		this.min = min;
	}

	public LocalDate getMax() {
		return max;
	}

	public void setMax(LocalDate max) {
		this.max = max;
	}

	public Float getAvg() {
		return avg;
	}

	public void setAvg(Float avg) {
		this.avg = avg;
	}
	
	
	
	

}
