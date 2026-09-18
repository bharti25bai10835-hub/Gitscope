# GitScope - Project Statement

## Project Title

GitScope - Git Repository Risk Analyzer

## Problem Statement

Software repositories accumulate a large amount of development history over time. Some files are modified frequently, involve multiple contributors, or tend to change together with other files. These characteristics can make certain parts of a repository more difficult to maintain.

GitScope is a Java-based command-line tool that analyzes Git repository history to identify files that may require additional attention. It combines historical change activity, contributor information, file coupling, and historical impact relationships to produce file-level risk information.

## Objectives

The main objectives of GitScope are:

1. Parse Git repository history.
2. Identify frequently changed files and repository hotspots.
3. Analyze contributor ownership of files.
4. Measure relationships between files based on co-change history.
5. Estimate the potential impact of modifying important files.
6. Calculate a risk score using multiple repository factors.
7. Generate a readable analysis report.
8. Provide automated tests for major implemented components.

## Functional Requirements

### FR1 - Git History Parsing

The system shall read Git commit history and extract commit hashes, authors, changed files, additions, and deletions.

### FR2 - Hotspot Analysis

The system shall identify frequently changed files and rank them based on repository activity.

### FR3 - Coupling Analysis

The system shall identify files that frequently change together across commits.

### FR4 - Ownership Analysis

The system shall determine the contributors associated with repository files.

### FR5 - Risk Scoring

The system shall calculate a normalized risk score using file activity, coupling, and contributor information.

### FR6 - Impact Analysis

The system shall identify files historically related to the highest-ranked hotspot file based on co-change history.

### FR7 - Report Generation

The system shall generate a text-based report containing the analyzed hotspot information.

### FR8 - Automated Testing

The system shall provide automated tests for major implemented model, parser, analysis, and scoring components.

## Non-Functional Requirements

### NFR1 - Usability

The application shall be executable through command-line instructions.

### NFR2 - Reliability

The application shall handle cases such as empty Git history without unexpected termination.

### NFR3 - Maintainability

The implementation shall use separate packages for models, parsing, analysis, scoring, and reporting.

### NFR4 - Performance

The system shall process repository history using appropriate Java collections and efficient traversal of stored commit information.

### NFR5 - Error Handling

The application shall provide meaningful output when repository analysis or report generation cannot be completed.

### NFR6 - Readability

Analysis results shall be presented in a structured command-line format.

## Technologies Used

- Java
- Java Collections Framework
- Git
- Command Line Interface
- VS Code
- GitHub

## Expected Output

GitScope produces:

- Number of repository commits analyzed
- File hotspot rankings
- File change statistics
- Contributor counts
- File coupling information
- File-level risk scores and risk levels
- Historical impact relationships for the highest-ranked hotspot
- A generated text-based report

## Testing

The project includes an automated test runner covering:

- File change calculations
- File statistics
- Risk score calculation
- Risk level generation
- Git history parsing
- Commit hash extraction
- Commit author extraction
- Hotspot analysis
- Hotspot sorting

## Project Structure

```text
GitScope/
├── src/
│   ├── analyzer/
│   ├── model/
│   ├── parser/
│   ├── report/
│   ├── scoring/
│   └── Main.java
├── tests/
│   └── TestRunner.java
├── docs/
├── README.md
├── statement.md
└── .gitignore
```

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