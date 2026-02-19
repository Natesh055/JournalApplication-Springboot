package com.natesing.journal.controller;

import com.natesing.journal.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap();

    @GetMapping("/all-journals")
    public List<JournalEntry> getAllJournalEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public void createEntry(@RequestBody JournalEntry req) {
        journalEntries.put(req.getId(), req);
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalByID(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public Boolean deleteEntryById(@PathVariable Long myId) {
         journalEntries.remove(myId);
         return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateEntry(@PathVariable Long myId,@RequestBody JournalEntry req) {
        return journalEntries.put(myId,req);
    }

}
