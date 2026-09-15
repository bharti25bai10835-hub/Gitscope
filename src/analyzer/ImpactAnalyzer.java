package analyzer;

import model.Commit;
import model.FileChange;

import java.util.*;

public class ImpactAnalyzer {

    public Map<String, Double> analyze(
            List<Commit> commits,
            String targetFile) {

        Map<String, Integer> coChangeCounts = new HashMap<>();
        int targetCommitCount = 0;

        for (Commit commit : commits) {

            boolean targetChanged = false;

            for (FileChange change : commit.getChanges()) {
                if (change.getFilePath().equals(targetFile)) {
                    targetChanged = true;
                    break;
                }
            }

            if (!targetChanged) {
                continue;
            }

            targetCommitCount++;

            Set<String> filesInCommit = new HashSet<>();

            for (FileChange change : commit.getChanges()) {
                filesInCommit.add(change.getFilePath());
            }

            filesInCommit.remove(targetFile);

            for (String file : filesInCommit) {
                coChangeCounts.merge(file, 1, Integer::sum);
            }
        }

        Map<String, Double> impactMap = new HashMap<>();

        if (targetCommitCount == 0) {
            return impactMap;
        }

        for (Map.Entry<String, Integer> entry : coChangeCounts.entrySet()) {

            double percentage =
                    (entry.getValue() * 100.0) / targetCommitCount;

            impactMap.put(
                    entry.getKey(),
                    Math.round(percentage * 10.0) / 10.0
            );
        }

        return impactMap;
    }
}