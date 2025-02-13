package org.example.band.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.band.config.JwtTokenProvider;
import org.example.band.dto.ProjectCreateRequest;
import org.example.band.dto.ProjectCreateResponse;
import org.example.band.dto.ProjectResponse;
import org.example.band.dto.ProjectUpdateRequest;
import org.example.band.repository.UserRepository;
import org.example.band.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ProjectController.class)
//인증관련은 제외
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;

	// JwtAuthenticationFilter에서 요구하는 빈들을 모의 객체로 등록
	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private UserRepository userRepository;  // 만약 필요하다면

	@DisplayName("프로젝트 조회 API 테스트")
	@Test
	void getProject() throws Exception {
		// given: 테스트용 더미 응답 생성
		ProjectResponse dummyResponse = new ProjectResponse(1L, "Test Project", "Rock");
		when(projectService.findProject(anyLong())).thenReturn(dummyResponse);

		// when & then: GET /api/project/1 요청 시 JSON 필드 검증
		mockMvc.perform(get("/api/project/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("Test Project"));
	}

	@DisplayName("프로젝트 생성 API 테스트")
	@Test
	void createProject() throws Exception {
		// given: 요청 본문(JSON) 및 더미 응답 생성
		String requestJson = "{\"title\":\"New Project\",\"genre\":\"Pop\"}";
		ProjectCreateResponse dummyResponse = new ProjectCreateResponse(1L, "New Project", "Pop");
		when(projectService.createProject(eq(1L), any(ProjectCreateRequest.class))).thenReturn(dummyResponse);

		// when & then: POST /api/project?userId=1 요청 시 상태 201과 응답 JSON 검증
		mockMvc.perform(post("/api/project?userId=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("New Project"))
			.andExpect(jsonPath("$.genre").value("Pop"));
	}

	@DisplayName("프로젝트 수정 API 테스트")
	@Test
	void updateProject() throws Exception {
		// given: 요청 본문(JSON) 및 더미 응답 생성
		String requestJson = "{\"updateType\":\"BASIC_INFO\",\"title\":\"Updated Title\",\"genre\":\"Jazz\",\"audioFile\":null}";
		ProjectResponse dummyResponse = new ProjectResponse(1L, "Updated Title", "Jazz");
		when(projectService.updateProject(eq(1L), any(ProjectUpdateRequest.class))).thenReturn(dummyResponse);

		// when & then: PUT /api/project/1 요청 시 상태 200과 응답 JSON 검증
		mockMvc.perform(put("/api/project/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("Updated Title"))
			.andExpect(jsonPath("$.genre").value("Jazz"));
	}

	@DisplayName("프로젝트 삭제 API 테스트")
	@Test
	void deleteProject() throws Exception {
		// given: 서비스의 deleteProject 메서드 호출을 모의 처리
		doNothing().when(projectService).deleteProject(1L);

		// when & then: DELETE /api/project/1 요청 시 상태 204(No Content) 검증
		mockMvc.perform(delete("/api/project/1"))
			.andExpect(status().isNoContent());

		// 삭제 메서드가 실제 호출되었는지 검증
		verify(projectService).deleteProject(1L);
	}
}
