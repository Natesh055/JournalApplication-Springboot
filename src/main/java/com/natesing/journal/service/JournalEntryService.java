package com.natesing.journal.service;

import com.natesing.journal.entity.JournalEntry;
import com.natesing.journal.entity.User;
import com.natesing.journal.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry entry, String userName) {
        User byUserName = userService.findByUserName(userName);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved = journalRepository.save(entry);
        byUserName.getJournalEntries().add(saved);
        userService.saveEntry(byUserName);
    }
    public void updateEntry(JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        journalRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries() {
        List<JournalEntry> journalEntries = journalRepository.findAll();
        return journalEntries;
    }

    public Optional<JournalEntry> getJournalByID(ObjectId myId) {
        return journalRepository.findById(myId);
    }

    public Boolean deleteEntryById(ObjectId myId, String userName) {
        User byUserName = userService.findByUserName(userName);
        byUserName.getJournalEntries().removeIf(x->x.getId().equals(myId));
        userService.saveEntry(byUserName);
        journalRepository.deleteById(myId);
        return true;
    }

    public JournalEntry updateJournalById(ObjectId myId, JournalEntry journalEntry) {
        JournalEntry newEntry = new JournalEntry();
        newEntry.setDate(LocalDateTime.now());
        if (journalEntry.getTitle() != null)
            newEntry.setTitle(journalEntry.getTitle());

        if (journalEntry.getContent() != null)
            newEntry.setContent(journalEntry.getContent());

        journalRepository.save(journalEntry);
        return newEntry;
    }
}
