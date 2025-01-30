package org.example.band.enums;

public enum ProblemType {
	PITCH_DEVIATION,      // 음정이 벗어난 경우
	RHYTHM_MISMATCH,      // 박자가 맞지 않는 경우
	TIMING_OFFSET,        // 전체적인 타이밍이 밀리거나 당겨진 경우
	VOLUME_INCONSISTENCY, // 볼륨이 일정하지 않은 경우
	TEMPO_VARIATION,      // 템포가 일정하지 않은 경우
	TECHNICAL_ERROR,      // 연주 기술적 문제 (프레이징, 아티큘레이션 등)
	ENSEMBLE_ISSUE        // 다른 악기와의 조화가 맞지 않는 경우
}