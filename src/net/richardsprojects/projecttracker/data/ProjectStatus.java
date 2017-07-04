package net.richardsprojects.projecttracker.data;

public enum ProjectStatus {
	IN_PROGRESS, PRIORITY, FINISHED;
	
	public static ProjectStatus getType(int type) {
		if(type == 1) {
			return ProjectStatus.IN_PROGRESS;
		} else if(type == 2) {
			return ProjectStatus.PRIORITY;
		} else if(type == 3) {
			return ProjectStatus.FINISHED;
		} else {
			return null;
		}
	}
	
	public static int getNumber(ProjectStatus type) {
		if(type == ProjectStatus.IN_PROGRESS) {
			return 1;
		} else if(type == ProjectStatus.PRIORITY) {
			return 2;
		} else if(type == ProjectStatus.FINISHED) {
			return 3;
		} else {
			return 0;
		}
	}
	
	public static String getName(ProjectStatus type) {
		if(type == ProjectStatus.IN_PROGRESS) {
			return "In Progress";
		} else if(type == ProjectStatus.PRIORITY) {
			return "Priority";
		} else if(type == ProjectStatus.FINISHED) {
			return "Completed";
		} else {
			return null;
		}
	}

	public static ProjectStatus getEnumFromName(String name) {
		if(name.equals("In Progress")) {
			return ProjectStatus.IN_PROGRESS;
		} else if(name.equals("Priority")) {
			return ProjectStatus.PRIORITY;
		} else if(name.equals("Completed")) {
			return ProjectStatus.FINISHED;
		} else {
			return null;
		}
	}
}