package org.example.band.dto;

import org.example.band.entity.Project;

public record ProjectResponse(
	Long id,
	String title,
	String genre,
	String referenceFileName,
	String performanceFileName,
	String referenceFileUrl,
	String performanceFileUrl
) {
	public static ProjectResponse from(Project project) {
		String refName = null;
		String refUrl = null;
		if (project.getReferenceFile() != null) {
			refName = project.getReferenceFile().getOriginalFileName();
			refUrl = project.getReferenceFile().getFileUrl(); // S3 URL
		}

		String perfName = null;
		String perfUrl = null;
		if (project.getPerformanceFile() != null) {
			perfName = project.getPerformanceFile().getOriginalFileName();
			perfUrl = project.getPerformanceFile().getFileUrl();
		}

		return new ProjectResponse(
			project.getId(),
			project.getTitle(),
			project.getGenre(),
			refName,
			perfName,
			refUrl,
			perfUrl
		);
	}
}
