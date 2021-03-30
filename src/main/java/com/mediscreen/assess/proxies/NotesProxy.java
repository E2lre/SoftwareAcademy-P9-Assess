package com.mediscreen.assess.proxies;

//import com.mediscreen.assess.model.external.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${microservice-notes.name}")
//@FeignClient(name = "${microservice-notes.name}", url="${microservice-notes.url}")
//@FeignClient(name = "microservice-notes", url="localhost:9004/microservice-notes")
//@FeignClient(name = "microservice-notes", url="localhost:8085")
public interface NotesProxy {

/*    @GetMapping(value = "/patHistories")
    List<Note> getAllNotes();

    @GetMapping(value = "/patHistory/{id}")
    Note getNoteById(@PathVariable("id") long id);

    @GetMapping(value = "patientpatHistories/{patientId}")
    List<Note> getNoteByPatientId(@PathVariable("patientId") Long patientId);*/

    @RequestMapping(value = "patHistories/triggers/{patientId}")
    int getScoreByPatientIdAndTriggers(@PathVariable Long patientId,@RequestBody List<String> triggers);
}
