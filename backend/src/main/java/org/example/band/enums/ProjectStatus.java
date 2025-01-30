package org.example.band.enums;

public enum ProjectStatus {
	CREATED,             // 프로젝트 생성됨
	FILE_UPLOADING,      // 파일 업로드 중
	FILES_READY,         // 파일 업로드 완료
	ANALYZING,           // 분석 중
	ANALYSIS_COMPLETED,  // 분석 완료
	ERROR,              // 에러 발생
	ARCHIVED            // 보관됨
}