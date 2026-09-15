package parser;

import model.Commit;
import model.FileChange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitHistoryParser {

    public List<Commit> parse(String repositoryPath) {
        List<Commit> commits = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "git",
                    "-C",
                    repositoryPath,
                    "log",
                    "--numstat",
                    "--pretty=format:COMMIT|%H|%an|%ad"
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            Commit currentCommit = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("COMMIT|")) {

                    String[] parts = line.split("\\|", 4);

                    if (parts.length == 4) {
                        currentCommit = new Commit(
                                parts[1],
                                parts[2],
                                parts[3]
                        );

                        commits.add(currentCommit);
                    }

                } else if (currentCommit != null && !line.trim().isEmpty()) {

                    String[] parts = line.split("\\t");

                    if (parts.length == 3) {

                        try {
                            int additions = Integer.parseInt(parts[0]);
                            int deletions = Integer.parseInt(parts[1]);
                            String filePath = parts[2];

                            currentCommit.addChange(
                                    new FileChange(
                                            filePath,
                                            additions,
                                            deletions
                                    )
                            );

                        } catch (NumberFormatException ignored) {
                            // Ignore binary files where Git reports "-"
                        }
                    }
                }
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error reading Git history: " + e.getMessage());
        }

        return commits;
    }
}