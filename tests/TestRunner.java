import analyzer.HotspotAnalyzer;
import model.Commit;
import model.FileStats;
import model.FileChange;
import parser.GitHistoryParser;
import scoring.RiskScorer;

import java.util.List;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java TestRunner <repository-path>");
            return;
        }

        System.out.println("========== GITSCOPE TESTS ==========\n");

        testFileChange();
        testFileStats();
        testRiskScorer();
        testGitParser(args[0]);
        testHotspotAnalyzer(args[0]);

        System.out.println("\n====================================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("====================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testFileChange() {

        FileChange change =
                new FileChange("Test.java", 10, 5);

        check(
                change.getTotalChanges() == 15,
                "FileChange total changes"
        );
    }

    private static void testFileStats() {

        FileStats stats =
                new FileStats("Test.java");

        stats.addChanges(10, 5);
        stats.addChanges(4, 1);

        check(
                stats.getChangeCount() == 2,
                "FileStats change count"
        );

        check(
                stats.getTotalChanges() == 20,
                "FileStats total changes"
        );
    }

    private static void testRiskScorer() {

        RiskScorer scorer = new RiskScorer();

        double score =
                scorer.calculateRisk(10, 500, 10, 1);

        check(
                score >= 0.0 && score <= 1.0,
                "Risk score range"
        );

        check(
                scorer.getRiskLevel(score) != null,
                "Risk level generation"
        );
    }

    private static void testGitParser(String repositoryPath) {

        GitHistoryParser parser =
                new GitHistoryParser();

        List<Commit> commits =
                parser.parse(repositoryPath);

        check(
                !commits.isEmpty(),
                "Git history parsing"
        );

        if (!commits.isEmpty()) {

            check(
                    commits.get(0).getHash() != null,
                    "Commit hash extraction"
            );

            check(
                    commits.get(0).getAuthor() != null,
                    "Commit author extraction"
            );
        }
    }

    private static void testHotspotAnalyzer(String repositoryPath) {

        GitHistoryParser parser =
                new GitHistoryParser();

        List<Commit> commits =
                parser.parse(repositoryPath);

        HotspotAnalyzer analyzer =
                new HotspotAnalyzer();

        List<FileStats> hotspots =
                analyzer.analyze(commits);

        check(
                !hotspots.isEmpty(),
                "Hotspot analysis"
        );

        if (hotspots.size() >= 2) {

            check(
                    hotspots.get(0).getChangeCount()
                            >= hotspots.get(1).getChangeCount(),
                    "Hotspot sorting"
            );
        }
    }

    private static void check(
            boolean condition,
            String testName) {

        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }
}