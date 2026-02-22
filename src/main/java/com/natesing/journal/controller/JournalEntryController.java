package com.natesing.journal.controller;

import com.natesing.journal.entity.JournalEntry;
import com.natesing.journal.service.JournalEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-journals")
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry req) {
        return journalEntryService.createEntry(req);
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalByID(@PathVariable ObjectId myId) {
        return journalEntryService.getJournalByID(myId);
    }

    @DeleteMapping("id/{myId}")
    public Boolean deleteEntryById(@PathVariable ObjectId myId) {
        return deleteEntryById(myId);
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry req) {
        return journalEntryService.updateJournalById(myId, req);
    }

}
