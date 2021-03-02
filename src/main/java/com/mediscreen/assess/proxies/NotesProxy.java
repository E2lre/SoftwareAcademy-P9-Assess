package com.mediscreen.assess.proxies;

import com.mediscreen.assess.model.external.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-notes", url="localhost:9004/microservice-notes") //for docker
//@FeignClient(name = "microservice-notes", url="localhost:8085")
public interface NotesProxy {

    @GetMapping(value = "/patHistories")
    List<Note> getAllNotes();

    @GetMapping(value = "/patHistory/{id}")
    Note getNoteById(@PathVariable long id);

    @GetMapping(value = "patientpatHistories/{patientId}")
    List<Note> getNoteByPatientId(@PathVariable Long patientId);

}
