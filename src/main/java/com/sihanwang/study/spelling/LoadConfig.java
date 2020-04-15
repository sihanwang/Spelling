package com.sihanwang.study.spelling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadConfig {

	@Bean(name="YoudaoUtil")
	public FanyiV3Util getFanyiV3Util()
	{
		return new FanyiV3Util();
	}
}
