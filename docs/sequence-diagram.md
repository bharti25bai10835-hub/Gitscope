# GitScope - Sequence Diagram

## 1. Overview

The sequence below shows the interaction between the user, the main application, the parser, analysis components, scoring component, impact analyzer, and report generator during a GitScope execution.

## 2. Sequence Diagram

```text
User              Main          GitHistoryParser       Analyzers       RiskScorer       ImpactAnalyzer    ReportGenerator
 |                 |                   |                   |                |                 |                |
 |  Repository     |                   |                   |                |                 |                |
 |---- path ------>|                   |                   |                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |--- parse(path) -->|                   |                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |<-- List<Commit> --|                   |                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |---- analyze(commits) ---------------->|                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |<--- hotspot results ------------------|                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |---- analyze(commits) ---------------->|                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |<--- coupling results -----------------|                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |---- analyze(commits) ---------------->|                |                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |<--- ownership results ----------------|                |                 |                | 
 |                 |                   |                   |                |                 |                |
 |                 |-------------------------------- calculateRisk() ------>|                 |                |
 |                 |<---------------- risk score ---------------------------|                 |                |
 |                 |                   |                   |                |                 |                |
 |                 |------------------------------------------------------->|                 |                |
 |                 |                                                        |                 |                |
 |                 |-------------------------- analyze(commits,target) ---------------------->|                |
 |                 |<-------------------------- impact results -------------------------------|                |
 |                 |                   |                   |                |                 |                |
 |                 |------------------------------------------------ generateHotspotReport() ---------------->|
 |                 |                                                                                           |
 |                 |<-------------------------------- report generated -----------------------------------------|
 |                 |                   |                   |                |                 |                |
 |<--- CLI results-|                   |                   |                |                 |                |
 |                 |                   |                   |                |                 |                |

 ## 3. Detailed Interaction Sequence
Step 1 - Repository Input

The user provides the path of the Git repository through the command line.

Main receives the repository path.

Step 2 - Parse Git History

Main calls:

GitHistoryParser.parse(repositoryPath)

The parser returns a list of Commit objects containing the extracted repository history.

Step 3 - Hotspot Analysis

Main passes the parsed commits to:

HotspotAnalyzer.analyze(commits)

The analyzer produces a list of FileStats objects representing file activity.

Step 4 - Coupling Analysis

Main passes the same commit information to:

CouplingAnalyzer.analyze(commits)

The analyzer returns file co-change relationships.

Step 5 - Ownership Analysis

Main calls:

OwnershipAnalyzer.analyze(commits)

The resulting data represents contributors associated with repository files.

Step 6 - Risk Calculation

For each selected hotspot, Main collects the relevant statistics and passes them to:

RiskScorer.calculateRisk(...)

The scorer returns a normalized risk score.

RiskScorer.getRiskLevel(...) is then used to determine the corresponding risk level.

Step 7 - Impact Analysis

The highest-ranked hotspot is selected as the target file.

Main calls:

ImpactAnalyzer.analyze(commits, targetFile)

The analyzer returns historically related files and their co-change percentages.

Step 8 - Result Display

Main displays the analysis results through the command-line interface.

The output contains file statistics, contributors, coupling, risk scores, risk levels, and impact relationships.

Step 9 - Report Generation

Main calls:

ReportGenerator.generateHotspotReport(...)

The report generator writes the hotspot information to:

gitscope-report.txt

Step 10 - Completion

After the analysis and report generation are complete, the application displays:

Analysis complete.

```

![Execution workflow diagram](diagrams/02_workflow.png)


## 4. Interaction Summary

The complete interaction can be summarized as:

User
 |
 | Repository Path
 v
Main
 |
 | parse()
 v
GitHistoryParser
 |
 | Commit data
 v
Analysis Components
 |
 +--> HotspotAnalyzer
 |
 +--> CouplingAnalyzer
 |
 +--> OwnershipAnalyzer
 |
 v
RiskScorer
 |
 | Risk information
 v
ImpactAnalyzer
 |
 | Impact relationships
 v
ReportGenerator
 |
 v
gitscope-report.txt
 |
 v
CLI Results

## 5. Error Interaction

If the repository path is missing from the command line, Main displays the usage message and stops.

If no Git history is found, the application displays:

No Git history found.

and stops before performing further analysis.

If report generation encounters an I/O problem, ReportGenerator reports the failure instead of silently ignoring it.

