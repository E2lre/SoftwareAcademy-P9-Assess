package com.mediscreen.assess.proxies;

import com.mediscreen.assess.model.external.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${microservice-patients.name}")
//@FeignClient(name = "${microservice-patients.name}", url="${microservice-patients.url}")
//@FeignClient(name = "microservice-patientsv2", url="localhost:9004/microservice-patientsv2")
//@FeignClient(name = "microservice-patientsv2", url="localhost:8084")
public interface PatientProxy {

    @GetMapping(value = "/patients")
    List<Patient> listPatients();

    @GetMapping(value = "/patient/{id}")
    Patient getPatientById(@PathVariable("id") long id);
}

