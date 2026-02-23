package com.natesing.journal.entity;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private ObjectId userId;
    @Indexed(unique = true)
    @Nonnull
    private String userName;
    @Nonnull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
