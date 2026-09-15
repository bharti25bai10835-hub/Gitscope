package analyzer;

import model.Commit;
import model.FileChange;

import java.util.*;

public class CouplingAnalyzer {

    public Map<String, Map<String, Integer>> analyze(List<Commit> commits) {

        Map<String, Map<String, Integer>> couplingMap = new HashMap<>();

        for (Commit commit : commits) {

            List<FileChange> changes = commit.getChanges();

            for (int i = 0; i < changes.size(); i++) {

                String fileA = changes.get(i).getFilePath();

                for (int j = i + 1; j < changes.size(); j++) {

                    String fileB = changes.get(j).getFilePath();

                    couplingMap
                            .computeIfAbsent(fileA, k -> new HashMap<>())
                            .merge(fileB, 1, Integer::sum);

                    couplingMap
                            .computeIfAbsent(fileB, k -> new HashMap<>())
                            .merge(fileA, 1, Integer::sum);
                }
            }
        }

        return couplingMap;
    }
}