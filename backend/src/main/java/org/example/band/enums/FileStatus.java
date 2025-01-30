package org.example.band.enums;

public enum FileStatus {
	UPLOADING,   // 업로드 중
	UPLOADED,    // 업로드 완료
	PROCESSING,  // 처리 중
	READY,       // 사용 가능
	ERROR,       // 에러 발생
	DELETED      // 삭제됨
}