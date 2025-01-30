package org.example.band.enums;

public enum Provider {
	KAKAO {
		@Override
		public String getAttributeKey() {
			return "id";
		}

		@Override
		public String getEmailAttribute() {
			return "kakao_account.email";
		}

		@Override
		public String getNameAttribute() {
			return "properties.nickname";
		}

		@Override
		public String getProfileImageAttribute() {
			return "properties.profile_image";
		}
	};

	public abstract String getAttributeKey();
	public abstract String getEmailAttribute();
	public abstract String getNameAttribute();
	public abstract String getProfileImageAttribute();
}