package com.natesing.journal.controller;

import com.natesing.journal.entity.JournalEntry;
import com.natesing.journal.entity.User;
import com.natesing.journal.service.JournalEntryService;
import com.natesing.journal.service.UserService;
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

    @Autowired
    UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> allEntries = byUserName.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry req, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(req, userName);
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

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String userName, @PathVariable ObjectId myId) {
        journalEntryService.deleteEntryById(myId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updatedJournalByID(
            @PathVariable String userName,
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry req) {
        JournalEntry old = journalEntryService.getJournalByID(myId).orElse(null);
        if (old != null) {
            old.setTitle(req.getTitle() != null && !req.getTitle().equals("") ? req.getTitle() : old.getTitle());
            old.setContent(req.getContent() != null && !req.getContent().equals("") ? req.getContent() : old.getContent());
            journalEntryService.updateEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
