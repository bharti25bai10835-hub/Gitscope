# GitScope - System Architecture

## 1. Architecture Overview

GitScope follows a modular pipeline-based architecture. The application begins by reading Git repository history and converting it into structured commit and file-change data. This information is then passed through independent analysis modules to identify hotspots, coupling, ownership, risk, and potential impact.

The final analysis is displayed through the command-line interface and can also be written to a text report.

## 2. Architecture Diagram

```text
                    +----------------------+
                    |       Main.java      |
                    |    CLI Entry Point   |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |  GitHistoryParser    |
                    |  Git History Parsing |
                    +----------+-----------+
                               |
                               v
              +-----------------------------------+
              |          Data Model Layer         |
              |                                   |
              | Commit | FileChange | FileStats  |
              +----------------+------------------+
                               |
             +-----------------+-----------------+
             |                 |                 |
             v                 v                 v
    +----------------+ +----------------+ +----------------+
    | HotspotAnalyzer| |CouplingAnalyzer| |OwnershipAnalyzer|
    +-------+--------+ +-------+--------+ +-------+--------+
            |                  |                 |
            +------------------+-----------------+
                               |
                               v
                    +----------------------+
                    |      RiskScorer       |
                    |   Risk Calculation    |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |    ImpactAnalyzer     |
                    | Historical Impact     |
                    +----------+-----------+
                               |
                    +----------+-----------+
                    |                      |
                    v                      v
          +------------------+   +----------------------+
          | Command Line     |   |  ReportGenerator     |
          | Analysis Output  |   | Text Report Output    |
          +------------------+   +----------+-----------+
                                            |
                                            v
                                  +----------------------+
                                  | gitscope-report.txt  |
                                  +----------------------+

```

![System architecture diagram](diagrams/01_system_architecture.png)


## 3. Major Components
Main

Acts as the entry point of GitScope. It receives the repository path from the command line and coordinates the complete analysis workflow.

GitHistoryParser

Reads Git history from the specified repository and converts commit information into Java objects.

Model Layer

The model layer contains the main data structures used throughout the application:

- Commit - represents a Git commit.
- FileChange - represents changes made to a file.
- FileStats - stores accumulated file-level statistics.

HotspotAnalyzer

Analyzes file activity and identifies files that are frequently modified.

CouplingAnalyzer

Determines which files frequently change together by examining co-change information across commits.

OwnershipAnalyzer

Analyzes contributors associated with repository files.

RiskScorer

Combines repository activity factors to calculate a normalized file-level risk score and corresponding risk level.

ImpactAnalyzer

Uses historical co-change relationships to identify files that may be related to changes in a selected target file.

ReportGenerator

Generates a structured text report containing hotspot analysis results.

## 4. Data Flow

The overall processing flow is:

User provides a Git repository path.
Main starts the analysis.
GitHistoryParser extracts repository history.
Parsed information is stored using model classes.
Analysis modules process the collected information.
RiskScorer calculates file-level risk scores.
ImpactAnalyzer identifies historically related files.
Results are displayed through the CLI.
ReportGenerator writes the analysis report.

## 5. Architectural Characteristics

GitScope uses:

Modular package separation
Object-oriented design
Java Collections Framework
Sequential analysis pipeline
Separation of parsing, analysis, scoring, and reporting responsibilities
Command-line based execution
Independent testable components

## 6. Package Structure

src/
├── analyzer/
│   ├── CouplingAnalyzer.java
│   ├── HotspotAnalyzer.java
│   ├── ImpactAnalyzer.java
│   └── OwnershipAnalyzer.java
│
├── model/
│   ├── Commit.java
│   ├── FileChange.java
│   └── FileStats.java
│
├── parser/
│   └── GitHistoryParser.java
│
├── report/
│   └── ReportGenerator.java
│
├── scoring/
│   └── RiskScorer.java
│
└── Main.java

tests/
└── TestRunner.java

