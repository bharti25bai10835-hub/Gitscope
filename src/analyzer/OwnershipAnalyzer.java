package analyzer;

import model.Commit;
import model.FileChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnershipAnalyzer {

    public Map<String, Map<String, Integer>> analyze(List<Commit> commits) {

        Map<String, Map<String, Integer>> ownershipMap = new HashMap<>();

        for (Commit commit : commits) {

            String author = commit.getAuthor();

            for (FileChange change : commit.getChanges()) {

                String filePath = change.getFilePath();

                ownershipMap
                        .computeIfAbsent(filePath, k -> new HashMap<>())
                        .merge(author, 1, Integer::sum);
            }
        }

        return ownershipMap;
    }
}