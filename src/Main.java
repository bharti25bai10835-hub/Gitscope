import analyzer.CouplingAnalyzer;
import analyzer.HotspotAnalyzer;
import analyzer.ImpactAnalyzer;
import analyzer.OwnershipAnalyzer;
import model.Commit;
import model.FileStats;
import parser.GitHistoryParser;
import report.ReportGenerator;
import scoring.RiskScorer;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Main <repository-path>");
            return;
        }

        String repositoryPath = args[0];

        GitHistoryParser parser = new GitHistoryParser();
        List<Commit> commits = parser.parse(repositoryPath);

        if (commits.isEmpty()) {
            System.out.println("No Git history found.");
            return;
        }

        System.out.println("==============================================");
        System.out.println("                  GITSCOPE");
        System.out.println("        Git Repository Risk Analyzer");
        System.out.println("==============================================");

        System.out.println("\nCommits analyzed: " + commits.size());

        // 1. Hotspot analysis
        HotspotAnalyzer hotspotAnalyzer = new HotspotAnalyzer();
        List<FileStats> hotspots =
                hotspotAnalyzer.analyze(commits);

        // 2. Coupling analysis
        CouplingAnalyzer couplingAnalyzer =
                new CouplingAnalyzer();

        Map<String, Map<String, Integer>> coupling =
                couplingAnalyzer.analyze(commits);

        // 3. Ownership analysis
        OwnershipAnalyzer ownershipAnalyzer =
                new OwnershipAnalyzer();

        Map<String, Map<String, Integer>> ownership =
                ownershipAnalyzer.analyze(commits);

        // 4. Risk scoring
        RiskScorer riskScorer = new RiskScorer();

        System.out.println("\n========== FILE RISK ANALYSIS ==========\n");

        int limit = Math.min(10, hotspots.size());

        for (int i = 0; i < limit; i++) {

            FileStats stats = hotspots.get(i);

            String file = stats.getFilePath();

            int couplingCount = 0;

            if (coupling.containsKey(file)) {
                couplingCount =
                        coupling.get(file)
                                .values()
                                .stream()
                                .mapToInt(Integer::intValue)
                                .sum();
            }

            int contributorCount = 0;

            if (ownership.containsKey(file)) {
                contributorCount =
                        ownership.get(file).size();
            }

            double risk =
                    riskScorer.calculateRisk(
                            stats.getChangeCount(),
                            stats.getTotalChanges(),
                            couplingCount,
                            contributorCount
                    );

            String riskLevel =
                    riskScorer.getRiskLevel(risk);

            System.out.println(
                    (i + 1) + ". " + file
            );

            System.out.println(
                    "   Changes: " + stats.getChangeCount()
                            + " | Lines: "
                            + stats.getTotalChanges()
            );

            System.out.println(
                    "   Contributors: "
                            + contributorCount
                            + " | Coupling: "
                            + couplingCount
            );

            System.out.println(
                    "   Risk Score: "
                            + risk
                            + " [" + riskLevel + "]"
            );

            System.out.println();
        }

        // 5. Impact analysis for highest hotspot
        String targetFile =
                hotspots.get(0).getFilePath();

        ImpactAnalyzer impactAnalyzer =
                new ImpactAnalyzer();

        Map<String, Double> impact =
                impactAnalyzer.analyze(
                        commits,
                        targetFile
                );

        System.out.println(
                "========== IMPACT ANALYSIS ==========\n"
        );

        System.out.println(
                "Target file: " + targetFile
        );

        if (impact.isEmpty()) {

            System.out.println(
                    "No historically related files found."
            );

        } else {

            impact.entrySet()
                    .stream()
                    .sorted(
                            Map.Entry.<String, Double>
                                    comparingByValue()
                                    .reversed()
                    )
                    .limit(5)
                    .forEach(entry ->
                            System.out.println(
                                    entry.getKey()
                                            + " | Co-change: "
                                            + entry.getValue()
                                            + "%"
                            )
                    );
        }

        // 6. Generate report
        ReportGenerator reportGenerator =
                new ReportGenerator();

        reportGenerator.generateHotspotReport(
                hotspots,
                "gitscope-report.txt"
        );

        System.out.println(
                "\nAnalysis complete."
        );
    }
}