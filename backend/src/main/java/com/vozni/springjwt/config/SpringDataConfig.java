package com.vozni.springjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * On serializing Page, Jackson warning:
 * For a stable JSON structure, please use Spring Data's PagedModel (globally via @EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO))
 *
 * the JSON structure of PageImpl is not guaranteed to be stable across Spring versions.
 *
 * this will globally configure page serialization (VIA_DTO)
 */
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@Configuration
public class SpringDataConfig {}