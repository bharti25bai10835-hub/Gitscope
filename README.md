# GitScope

GitScope is a Java-based command-line Git repository risk analyzer. It examines repository history and identifies files that may require additional attention based on change activity, contributor information, coupling, and historical impact relationships.

## Problem Statement

Software repositories accumulate a large amount of development history over time. Some files are modified frequently, involve multiple contributors, or tend to change together with other files.

GitScope analyzes this historical information and combines multiple repository-level factors to identify potential hotspots and calculate file-level risk indicators.

## Objectives

- Parse Git commit history.
- Identify frequently changed files.
- Analyze contributor distribution.
- Measure file coupling using co-change history.
- Perform historical impact analysis.
- Calculate file-level risk scores.
- Generate a text-based analysis report.
- Provide automated tests for major components.

## Features

- Git commit history parsing
- Hotspot detection
- File change statistics
- Contributor/ownership analysis
- File coupling analysis
- Risk scoring
- Risk level classification
- Historical impact analysis
- Text report generation
- Automated testing

## Project Structure

```text
GitScope/
├── src/
│   ├── analyzer/
│   │   ├── CouplingAnalyzer.java
│   │   ├── HotspotAnalyzer.java
│   │   ├── ImpactAnalyzer.java
│   │   └── OwnershipAnalyzer.java
│   ├── model/
│   │   ├── Commit.java
│   │   ├── FileChange.java
│   │   └── FileStats.java
│   ├── parser/
│   │   └── GitHistoryParser.java
│   ├── report/
│   │   └── ReportGenerator.java
│   ├── scoring/
│   │   └── RiskScorer.java
│   └── Main.java
├── tests/
│   └── TestRunner.java
├── docs/
├── README.md
├── statement.md
└── .gitignore

## Requirements
Java JDK 17 or later
Git
A Git repository to analyze

## Compilation

From the GitScope project root:
rm -rf out
mkdir out

javac -d out src/model/*.java src/parser/*.java src/analyzer/*.java src/scoring/*.java src/report/*.java src/Main.java tests/TestRunner.java

## Running GitScope

Provide the path of a Git repository as the command-line argument:
java -cp out Main <repository-path>
Example:
java -cp out Main ~/Documents/Roadwise-Smart-Transportation-System
The program analyzes the repository and displays:

Number of commits analyzed
File risk analysis
Change statistics
Contributor counts
Coupling information
Risk scores and risk levels
Historical impact relationships

A text report is also generated as:
gitscope-report.txt

## Running Tests

Compile the project using the compilation command above and run:
java -cp out TestRunner <repository-path>
Example:
java -cp out TestRunner ~/Documents/Roadwise-Smart-Transportation-System
The test runner verifies major components and reports the number of passed and failed tests.

## Example Output
==============================================
                  GITSCOPE
        Git Repository Risk Analyzer
==============================================

Commits analyzed: 19

========== FILE RISK ANALYSIS ==========

1. lib/screens/home/home_page.dart
   Changes: 4 | Lines: 1016
   Contributors: 2 | Coupling: 16
   Risk Score: 0.71 [MEDIUM]

...

========== IMPACT ANALYSIS ==========

Target file: lib/screens/home/home_page.dart
lib/screens/parking/parking_page.dart | Co-change: 50.0%
lib/app/app.dart | Co-change: 50.0%

Report generated: gitscope-report.txt

Analysis complete.

## Testing Results

The current test suite contains 10 checks covering:

FileChange calculations
FileStats change counting
FileStats total change calculations
Risk score range validation
Risk level generation
Git history parsing
Commit hash extraction
Commit author extraction
Hotspot analysis
Hotspot sorting

The current test run completes with:
Passed: 10
Failed: 0

## Technologies Used

Java
Java Collections Framework
Git
Command Line Interface
VS Code
GitHub

## Limitations

GitScope relies on the historical information available in a Git repository. Its risk score is an analytical indicator based on repository history and should not be treated as a definitive measurement of software quality or project risk.

The current version is command-line based and does not provide graphical visualization of repository relationships.

## Future Enhancements

Possible future improvements include:

Interactive command-line options
Additional risk factors
Configurable risk-weighting parameters
More detailed historical trend analysis
Exporting reports in additional formats
Visualization of file relationships