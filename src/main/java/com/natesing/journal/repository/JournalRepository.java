package com.natesing.journal.repository;

import com.natesing.journal.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.ObjectInput;

public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {
}
