package org.example.band.entity;

import org.example.band.enums.InstrumentType;
import org.example.band.enums.ProblemType;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problem_sections")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSection extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "analysis_id")
	private Analysis analysis;

	private Integer startTime;

	private Integer endTime;

	@Enumerated(EnumType.STRING)
	private InstrumentType instrumentType;

	@Enumerated(EnumType.STRING)
	private ProblemType problemType;

	@Column(columnDefinition = "text")
	private String description;

	@OneToOne(mappedBy = "problemSection", cascade = CascadeType.ALL)
	private AIFeedback feedback;

	@Builder
	public ProblemSection(Analysis analysis, Integer startTime, Integer endTime,
		InstrumentType instrumentType, ProblemType problemType,
		String description) {
		this.analysis = analysis;
		this.startTime = startTime;
		this.endTime = endTime;
		this.instrumentType = instrumentType;
		this.problemType = problemType;
		this.description = description;
	}

	// Analysis 연관관계 편의 메서드
	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
		if (analysis != null && !analysis.getProblemSections().contains(this)) {
			analysis.getProblemSections().add(this);
		}
	}

	// AIFeedback 연관관계 편의 메서드
	public void setFeedback(AIFeedback feedback) {
		this.feedback = feedback;
		if (feedback != null && feedback.getProblemSection() != this) {
			feedback.setProblemSection(this);
		}
	}
}