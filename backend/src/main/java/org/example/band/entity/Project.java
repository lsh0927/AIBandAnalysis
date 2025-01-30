package org.example.band.entity;



import org.example.band.enums.ProjectStatus;

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
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String genre;

	@Enumerated(EnumType.STRING)
	private ProjectStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 레퍼런스 파일 (원곡)
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "reference_file_id")
	private AudioFile referenceFile;

	// 실제 연주 파일
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "performance_file_id")
	private AudioFile performanceFile;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
	private Analysis analysis;

	@Builder
	public Project(String title, String genre, User user) {
		this.title = title;
		this.genre = genre;
		this.user = user;
		this.status = ProjectStatus.CREATED;
	}

	// User 연관관계 편의 메서드
	public void setUser(User user) {
		this.user = user;
		if (user != null && !user.getProjects().contains(this)) {
			user.getProjects().add(this);
		}
	}

	// AudioFile 연관관계 편의 메서드
	public void setReferenceFile(AudioFile audioFile) {
		this.referenceFile = audioFile;
	}

	// AudioFile 연관관계 편의 메서드
	public void setPerformanceFile(AudioFile audioFile) {
		this.performanceFile = audioFile;
	}

	// Analysis 연관관계 편의 메서드
	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
		if (analysis != null && analysis.getProject() != this) {
			analysis.setProject(this);
		}
	}
}
