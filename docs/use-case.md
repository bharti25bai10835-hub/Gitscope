# GitScope - Use Case Diagram

## 1. Actors

GitScope has one primary actor:

### Repository Analyst

The Repository Analyst is the user who provides a Git repository and uses GitScope to analyze its development history.

## 2. Use Case Diagram

```text
                         +----------------------+
                         |   Repository Analyst |
                         +----------+-----------+
                                    |
             +----------------------+----------------------+
             |          |            |          |          |
             v          v            v          v          v
      +-----------+ +--------+ +---------+ +--------+ +----------+
      | Provide   | | Parse  | | Analyze | | Analyze| | Generate |
      | Repository| | Git    | | Files   | | Impact | | Report   |
      | Path      | | History| |         | |        | |          |
      +-----------+ +--------+ +---------+ +--------+ +----------+
                           |         |
                           |         |
                           v         v
                    +----------------------+
                    | Analyze Repository   |
                    | Characteristics      |
                    +----------+-----------+
                               |
             +-----------------+------------------+
             |                 |                  |
             v                 v                  v
      +------------+    +-------------+    +-------------+
      | Hotspot    |    | Coupling    |    | Ownership   |
      | Detection  |    | Analysis    |    | Analysis    |
      +------+-----+    +------+------+    +------+------+
             |                 |                  |
             +-----------------+------------------+
                               |
                               v
                       +---------------+
                       | Risk Scoring  |
                       +-------+-------+
                               |
                               v
                       +---------------+
                       | View Results  |
                       | in CLI        |
                       +---------------+

```

![UML use case diagram](diagrams/05_use_case.png)


## 3. Use Case Descriptions
UC1 - Provide Repository Path

Actor: Repository Analyst

The user provides the path of the Git repository to be analyzed.

UC2 - Parse Git History

Actor: Repository Analyst

GitScope reads the repository history and extracts commit, author, file-change, addition, and deletion information.

UC3 - Analyze Hotspots

Actor: Repository Analyst

The system identifies files with high historical change activity.

UC4 - Analyze Coupling

Actor: Repository Analyst

The system identifies files that frequently change together.

UC5 - Analyze Ownership

Actor: Repository Analyst

The system identifies contributors associated with repository files.

UC6 - Calculate Risk

Actor: Repository Analyst

The system calculates a normalized risk score using the collected repository characteristics.

UC7 - Analyze Impact

Actor: Repository Analyst

The system identifies files historically related to a selected high-activity file.

UC8 - View Analysis Results

Actor: Repository Analyst

The user views hotspot rankings, contributor information, coupling information, risk scores, and impact relationships through the command-line interface.

UC9 - Generate Report

Actor: Repository Analyst

The system generates a text-based report containing the analysis results.

## 4. Use Case Relationships

The main workflow follows this relationship:
Provide Repository Path
          |
          v
   Parse Git History
          |
          v
 Analyze Repository
          |
    +-----+-----+-----+
    |           |     |
    v           v     v
 Hotspots   Coupling Ownership
    |           |     |
    +-----+-----+-----+
          |
          v
     Risk Scoring
          |
          v
    Impact Analysis
          |
          v
     View Results
          |
          v
    Generate Report

## 5. System Boundary

All analysis operations are performed within GitScope.

External input:

Git repository history
Repository path supplied by the user

External output:

Command-line analysis results
gitscope-report.txt
