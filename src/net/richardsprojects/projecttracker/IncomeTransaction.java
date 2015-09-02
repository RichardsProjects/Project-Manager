package net.richardsprojects.projecttracker;

import java.util.Date;

public class IncomeTransaction {
	
	private double income;
	private Date date;
	
	public IncomeTransaction(double income, Date date) {
		this.income = income;
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public double getIncome() {
		return this.income;
	}

}
