package net.richardsprojects.projecttracker;

public enum ProjectType {
	PERSONAL_PROJECT, FREELANCE_JOB, PREMIUM_PLUGIN;
	
	public static ProjectType getType(int type) {
		if(type == 1) {
			return ProjectType.PERSONAL_PROJECT;
		} else if(type == 2) {
			return ProjectType.FREELANCE_JOB;
		} else if(type == 3) {
			return ProjectType.PREMIUM_PLUGIN;
		} else {
			return null;
		}
	}
	
	public static int getNumber(ProjectType type) {
		if(type == ProjectType.PERSONAL_PROJECT) {
			return 1;
		} else if(type == ProjectType.FREELANCE_JOB) {
			return 2;
		} else if(type == ProjectType.PREMIUM_PLUGIN) {
			return 3;
		} else {
			return 0;
		}
	}
	
	public static String getName(ProjectType type) {
		if(type == ProjectType.PERSONAL_PROJECT) {
			return "Personal Project";
		} else if(type == ProjectType.FREELANCE_JOB) {
			return "Freelance Job";
		} else if(type == ProjectType.PREMIUM_PLUGIN) {
			return "Premium Plugin";
		} else {
			return null;
		}
	}
}
