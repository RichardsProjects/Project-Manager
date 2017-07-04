package net.richardsprojects.projecttracker.data;

import java.util.Date;

public class IncomeTransaction {
	
	private final double income;
	private final Date date;
	
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
