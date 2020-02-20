package com.studio.harbour.jdbc;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.studio.harbour.jdbc.json.ProfileData;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.repository.ProfileRowMapper;
import com.studio.harbour.jdbc.repository.UserRowMapper;

@Configuration
@EnableJdbcRepositories
@EnableJdbcAuditing
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    @Bean
    DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)     
                .build();
    }

    @Bean
    NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
	}
    
	@Override
	public JdbcCustomConversions jdbcCustomConversions() {
		return new JdbcCustomConversions(Arrays.asList(new Converter<Clob, String>() {

			@Override
			public String convert(Clob source) {				
				try {
					int length = Math.toIntExact(source.length());
					return length > 0? source.getSubString(1, length): null;
				} catch (SQLException e) {
					throw new IllegalStateException("Failed to convert CLOB to String.", e);
				}
			}
			
		}));
	}
	
	@Bean
	QueryMappingConfiguration rowMappers() {
		return new DefaultQueryMappingConfiguration()
				.registerRowMapper(ProfileData.class, new ProfileRowMapper())
				.registerRowMapper(UserData.class, new UserRowMapper()); 					
	}
}
