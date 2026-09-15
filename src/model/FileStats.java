package model;

public class FileStats {

    private String filePath;
    private int changeCount;
    private int additions;
    private int deletions;

    public FileStats(String filePath) {
        this.filePath = filePath;
    }

    public void addChanges(int additions, int deletions) {
        this.changeCount++;
        this.additions += additions;
        this.deletions += deletions;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getChangeCount() {
        return changeCount;
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
