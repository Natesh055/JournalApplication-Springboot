package com.natesing.journal.controller;

import com.natesing.journal.entity.JournalEntry;
import com.natesing.journal.service.JournalEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-journals")
    public ResponseEntity<?> getAllJournalEntries() {
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry req) {
        try {
            journalEntryService.createEntry(req);
            return new ResponseEntity<>("User created succesfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to create User", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalByID(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalByID = journalEntryService.getJournalByID(myId);
        if (journalByID.isPresent()) {
            return new ResponseEntity<>(journalByID.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId) {
        JournalEntry old = journalEntryService.getJournalByID(myId).orElse(null);
        if (old != null) {
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updatedJournalByID(@PathVariable ObjectId myId, @RequestBody JournalEntry req) {
        JournalEntry old = journalEntryService.getJournalByID(myId).orElse(null);
        if (old != null) {
            old.setTitle(req.getTitle() != null && !req.getTitle().equals("") ? req.getTitle() : old.getTitle());
            old.setContent(req.getContent() != null && !req.getContent().equals("") ? req.getContent() : old.getContent());
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
