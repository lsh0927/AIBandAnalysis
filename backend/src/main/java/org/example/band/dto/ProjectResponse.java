package org.example.band.dto;

import org.example.band.entity.Project;

public record ProjectResponse(
	Long id,
	String title,
	String genre) {
	public static ProjectResponse from(Project project){
		return new ProjectResponse(project.getId(), project.getTitle(), project.getGenre());
	}
}
