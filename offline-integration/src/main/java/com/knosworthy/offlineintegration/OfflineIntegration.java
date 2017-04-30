package com.knosworthy.offlineintegration;

import org.springframework.context.annotation.Configuration;

public interface OfflineIntegration {
	
	@Configuration
	public static class OfflineIntegrationConfiguration extends SpringContextJPAConfiguration {

		private static final String DefaultOfflineIntegrationDialectClassName = "org.hibernate.dialect.H2Dialect";
		private static final String DefaultOfflineIntegrationDataSourceDriverName = "org.h2.Driver";
		private static final String DefaultOfflineIntegrationDataSourceURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

		@Override
		public String getDataSourceDriverClassName() {
			return DefaultOfflineIntegrationDataSourceDriverName;
		}

		@Override
		public String getDataSourceURL() {
			return DefaultOfflineIntegrationDataSourceURL;
		}

		@Override
		public String getDataSourceUsername() {
			return "";
		}

		@Override
		public String getDataSourcePassword() {
			return "";
		}

		@Override
		public String[] getEntityPackagesToScan() {
			return new String[] {""};
		}

		@Override
		public String getDialectPropertyClassName() {
			return DefaultOfflineIntegrationDialectClassName;
		}
	}
}