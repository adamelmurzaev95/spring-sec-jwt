package com.adamelmurzaev.springsecurirysuleymanov.rest;

import com.adamelmurzaev.springsecurirysuleymanov.model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    public List<Developer> developerList = Stream.of(
            new Developer(1L, "Adam", "Elmurzaev"),
            new Developer(2L, "Mans", "Mazhiev"),
            new Developer(3L, "Albert", "Enshtein")
    ).collect(Collectors.toList());

    @GetMapping()
    @PreAuthorize("hasAuthority('developers:read')")
    public List<Developer> getAll(){
        return developerList;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public Developer getById(@PathVariable long id){
        return developerList.stream().filter(developer -> developer.getId() == id)
                .findAny()
                .orElse(null);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public Developer create(@RequestBody Developer developer){
        developerList.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void deleteById(@PathVariable long id){
        Developer developer = getById(id);
        developerList.remove(developer);
    }

}
