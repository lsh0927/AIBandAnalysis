package org.example.band.controller;

import org.example.band.dto.ProjectCreateRequest;
import org.example.band.dto.ProjectCreateResponse;
import org.example.band.dto.ProjectResponse;
import org.example.band.dto.ProjectUpdateRequest;
import org.example.band.service.ProjectService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	// GET /api/project/{id} 엔드포인트 구현
	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
		ProjectResponse response = projectService.findProject(id);
		return ResponseEntity.ok(response);
	}

	// POST /api/project
	@PostMapping
	public ResponseEntity<ProjectCreateResponse> createProject(
		@RequestParam Long userId,
		@RequestBody ProjectCreateRequest request) {
		ProjectCreateResponse response = projectService.createProject(userId, request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// PUT /api/project/{id}
	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponse> updateProject(
		@PathVariable Long id,
		@RequestBody ProjectUpdateRequest request) {
		ProjectResponse response = projectService.updateProject(id, request);
		return ResponseEntity.ok(response);
	}

	// DELETE /api/project/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
		projectService.deleteProject(id);
		return ResponseEntity.noContent().build();
	}
}

