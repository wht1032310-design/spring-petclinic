/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import java.util.Map;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configures the JPA persistence unit with EclipseLink instead of Spring Boot's default
 * Hibernate provider.
 */
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "org.springframework.samples.petclinic")
class EclipseLinkJpaConfiguration {

	@Bean
	@DependsOnDatabaseInitialization
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("org.springframework.samples.petclinic.owner",
				"org.springframework.samples.petclinic.vet");
		entityManagerFactory.setPersistenceUnitName("petclinic");
		entityManagerFactory.setPersistenceProviderClass(PersistenceProvider.class);
		entityManagerFactory.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		entityManagerFactory.setJpaPropertyMap(Map.of("eclipselink.ddl-generation", "none", "eclipselink.weaving",
				"false", "eclipselink.logging.level", "INFO"));
		return entityManagerFactory;
	}

	@Bean
	PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
