package analyzer;

import model.Commit;
import model.FileChange;
import model.FileStats;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotspotAnalyzer {

    public List<FileStats> analyze(List<Commit> commits) {

        Map<String, FileStats> statsMap = new HashMap<>();

        for (Commit commit : commits) {

            for (FileChange change : commit.getChanges()) {

                String filePath = change.getFilePath();

                FileStats stats = statsMap.get(filePath);

                if (stats == null) {
                    stats = new FileStats(filePath);
                    statsMap.put(filePath, stats);
                }

                stats.addChanges(
                        change.getAdditions(),
                        change.getDeletions()
                );
            }
        }

        List<FileStats> results = new ArrayList<>(statsMap.values());

        results.sort(
                Comparator.comparingInt(FileStats::getChangeCount)
                        .reversed()
        );

        return results;
    }
}