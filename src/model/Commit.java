package model;

import java.util.ArrayList;
import java.util.List;

public class Commit {

    private String hash;
    private String author;
    private String date;
    private List<FileChange> changes;

    public Commit(String hash, String author, String date) {
        this.hash = hash;
        this.author = author;
        this.date = date;
        this.changes = new ArrayList<>();
    }

    public void addChange(FileChange change) {
        changes.add(change);
    }

    public String getHash() {
        return hash;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public List<FileChange> getChanges() {
        return changes;
    }
}