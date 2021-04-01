package com.mediscreen.assess.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "${microservice-notes.name}")
public interface NotesProxy {

    @RequestMapping(value = "patHistories/triggers/{patientId}")
    int getScoreByPatientIdAndTriggers(@PathVariable("patientId") Long patientId,@RequestBody List<String> triggers);

}
