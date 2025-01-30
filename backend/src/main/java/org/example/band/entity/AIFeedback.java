package org.example.band.entity;

import org.example.band.enums.FeedbackType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "ai_feedback")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AIFeedback extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "problem_section_id")
	private ProblemSection problemSection;

	@Column(columnDefinition = "text")
	private String feedback;

	@Enumerated(EnumType.STRING)
	private FeedbackType type;

	@Builder
	public AIFeedback(ProblemSection problemSection, String feedback, FeedbackType type) {
		this.problemSection = problemSection;
		this.feedback = feedback;
		this.type = type;
	}

	// ProblemSection 연관관계 편의 메서드
	public void setProblemSection(ProblemSection problemSection) {
		this.problemSection = problemSection;
		if (problemSection != null && problemSection.getFeedback() != this) {
			problemSection.setFeedback(this);
		}
	}
}