# GitScope - System Workflow

## 1. Workflow Overview

GitScope processes a Git repository through a sequence of parsing, analysis, scoring, impact analysis, and reporting stages.

## 2. Workflow Diagram

```text
                    +----------------------+
                    |        START         |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    | User provides Git    |
                    | repository path      |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    | Validate command     |
                    | line arguments        |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    | GitHistoryParser      |
                    | reads Git history     |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    | Is Git history       |
                    | available?           |
                    +----------+-----------+
                         |           |
                       NO|           |YES
                         |           |
                         v           v
              +----------------+  +----------------------+
              | Display "No    |  | Create Commit and    |
              | Git history"   |  | FileChange objects   |
              +-------+--------+  +----------+-----------+
                      |                       |
                      v                       v
                    +-----+        +----------------------+
                    | END |        | Hotspot Analysis     |
                    +-----+        +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Coupling Analysis    |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Ownership Analysis   |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Calculate File Risk  |
                                  | Scores                |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Select Highest       |
                                  | Activity File        |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Impact Analysis      |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Display Results      |
                                  | in CLI               |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Generate Text Report |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | gitscope-report.txt  |
                                  +----------+-----------+
                                             |
                                             v
                                  +----------------------+
                                  | Analysis Complete    |
                                  +----------+-----------+
                                             |
                                             v
                                           +-----+
                                           | END |
                                           +-----+

## 3. Detailed Workflow
Step 1 - Input

The user provides the path of a Git repository through the command line.

Example:
java -cp out Main ~/path/to/repository

Step 2 - Git History Parsing

GitHistoryParser reads the repository history and extracts commit information including:

Commit hash
Author
Changed files
Additions
Deletions

The information is stored using the project model classes.

Step 3 - Hotspot Analysis

HotspotAnalyzer processes the parsed commits and determines which files have been modified frequently.

The resulting files are ranked according to their change activity.

Step 4 - Coupling Analysis

CouplingAnalyzer examines commits to determine which files frequently change together.

This provides historical co-change relationships between files.

Step 5 - Ownership Analysis

OwnershipAnalyzer determines the contributors associated with each file based on repository history.

Step 6 - Risk Scoring

RiskScorer combines file activity, change volume, coupling, and contributor information to calculate a normalized risk score.

Each analyzed file receives a corresponding risk level.

Step 7 - Impact Analysis

The highest-ranked hotspot is selected as the target file.

ImpactAnalyzer identifies other files that have historically changed together with the target file.

Step 8 - Result Display

The analysis results are displayed through the command-line interface.

The output includes:

File activity
Contributors
Coupling
Risk score
Risk level
Impact relationships
Step 9 - Report Generation

ReportGenerator writes the hotspot information to:

gitscope-report.txt

Step 10 - Completion

The application displays:
Analysis complete.

## 4. Error Handling Paths

GitScope handles several basic error conditions:

Missing command-line repository path
Empty or unavailable Git history
Report generation failure

These conditions produce an appropriate message rather than continuing with invalid analysis data.

