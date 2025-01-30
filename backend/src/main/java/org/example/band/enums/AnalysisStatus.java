package org.example.band.enums;


public enum AnalysisStatus {
	CREATED,             // 분석 객체 생성됨
	WAITING,             // 분석 대기 중
	PREPROCESSING,       // 전처리 중
	ANALYZING_PITCH,     // 음정 분석 중
	ANALYZING_RHYTHM,    // 리듬 분석 중
	ANALYZING_ENSEMBLE,  // 앙상블 분석 중
	GENERATING_FEEDBACK, // AI 피드백 생성 중
	COMPLETED,          // 분석 완료
	FAILED              // 분석 실패
}