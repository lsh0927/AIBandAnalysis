package org.example.band.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.band.enums.AnalysisStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analysis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@Enumerated(EnumType.STRING)
	private AnalysisStatus status;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> analysisData;

	@OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
	private List<ProblemSection> problemSections = new ArrayList<>();

	@Builder
	public Analysis(Project project) {
		this.project = project;
		this.status = AnalysisStatus.CREATED;
		this.analysisData = new HashMap<>();
	}

	// Project 연관관계 편의 메서드
	public void setProject(Project project) {
		this.project = project;
		if (project != null && project.getAnalysis() != this) {
			project.setAnalysis(this);
		}
	}

	// ProblemSection 연관관계 편의 메서드
	public void addProblemSection(ProblemSection problemSection) {
		this.problemSections.add(problemSection);
		if (problemSection.getAnalysis() != this) {
			problemSection.setAnalysis(this);
		}
	}

	// ProblemSection 연관관계 편의 메서드
	public void removeProblemSection(ProblemSection problemSection) {
		this.problemSections.remove(problemSection);
		if (problemSection.getAnalysis() == this) {
			problemSection.setAnalysis(null);
		}
	}
}