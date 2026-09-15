package model;

public class FileChange {

    private String filePath;
    private int additions;
    private int deletions;

    public FileChange(String filePath, int additions, int deletions) {
        this.filePath = filePath;
        this.additions = additions;
        this.deletions = deletions;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getAdditions() {
        return additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getTotalChanges() {
        return additions + deletions;
    }
}