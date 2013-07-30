package com.clouck.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class S3Converter {
	private static final Logger log = LoggerFactory.getLogger(S3Converter.class);
	
	@Autowired
	private ConversionService conversionService;
	
    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }
}
