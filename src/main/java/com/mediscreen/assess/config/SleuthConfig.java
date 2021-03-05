package com.mediscreen.assess.config;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Configuration;

/**** Used by zipkin for logging****/
@Configuration
public class SleuthConfig {


    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}