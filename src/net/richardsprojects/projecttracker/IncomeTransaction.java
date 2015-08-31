package net.richardsprojects.projecttracker;

import java.util.Date;

public class IncomeTransaction {
	
	private int income;
	private Date date;
	
	public IncomeTransaction(int income, Date date) {
		this.income = income;
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getIncome() {
		return this.income;
	}

}
