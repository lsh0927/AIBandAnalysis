package org.example.band.dto;

import org.example.band.entity.Project;

public record ProjectCreateResponse(
	Long id,
	String title,
	String genre
) {
	public static ProjectCreateResponse from(Project project){
		return new ProjectCreateResponse(
			project.getId(), project.getTitle(), project.getGenre()
		);
	}
}