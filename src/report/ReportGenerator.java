package report;

import model.FileStats;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    public void generateHotspotReport(
            List<FileStats> hotspots,
            String outputPath) {

        try (FileWriter writer = new FileWriter(outputPath)) {

            writer.write("GITSCOPE - CHANGE HOTSPOT REPORT\n");
            writer.write("================================\n\n");

            int limit = Math.min(10, hotspots.size());

            for (int i = 0; i < limit; i++) {

                FileStats stats = hotspots.get(i);

                writer.write(
                        (i + 1) + ". "
                        + stats.getFilePath()
                        + "\n"
                        + "   Commits: " + stats.getChangeCount()
                        + "\n"
                        + "   Additions: " + stats.getAdditions()
                        + "\n"
                        + "   Deletions: " + stats.getDeletions()
                        + "\n"
                        + "   Total changes: " + stats.getTotalChanges()
                        + "\n\n"
                );
            }

            System.out.println(
                    "Report generated: " + outputPath
            );

        } catch (IOException e) {

            System.out.println(
                    "Could not generate report: "
                    + e.getMessage()
            );
        }
    }
}