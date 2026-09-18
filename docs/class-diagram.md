# GitScope - Class Diagram

## 1. Overview

The GitScope class structure is divided into model, parser, analyzer, scoring, reporting, and application layers.

The classes communicate through Java objects and collections, allowing each component to perform a specific responsibility.

## 2. Class Diagram

```text
+----------------------+
|        Main          |
+----------------------+
| + main(String[])     |
+----------+-----------+
           |
           v
+----------------------+
|  GitHistoryParser    |
+----------------------+
| + parse(String)      |
+----------+-----------+
           |
           v
+----------------------+
|       Commit         |
+----------------------+
| - hash               |
| - author             |
| - fileChanges        |
+----------------------+
| + getHash()          |
| + getAuthor()        |
| + getFileChanges()   |
+----------+-----------+
           |
           v
+----------------------+
|     FileChange       |
+----------------------+
| - filePath           |
| - additions          |
| - deletions          |
+----------------------+
| + getFilePath()      |
| + getAdditions()     |
| + getDeletions()     |
| + getTotalChanges()  |
+----------------------+


+----------------------+
|      FileStats       |
+----------------------+
| - filePath           |
| - changeCount        |
| - additions          |
| - deletions          |
+----------------------+
| + addChanges()       |
| + getChangeCount()   |
| + getTotalChanges()  |
+----------------------+


       Commit data
           |
   +-------+--------+----------------+
   |       |        |                |
   v       v        v                v
+--------+ +--------+ +----------+ +----------------+
|Hotspot | |Coupling| |Ownership | |Impact          |
|Analyzer| |Analyzer| |Analyzer  | |Analyzer        |
+--------+ +--------+ +----------+ +----------------+
|+analyze| |+analyze| |+analyze  | |+analyze        |
+---+----+ +---+----+ +----+-----+ +-------+--------+
    |          |           |                |
    v          v           v                v
FileStats   Coupling    Ownership       Impact
            Map         Map             Map


                    +------------------+
                    |    RiskScorer    |
                    +------------------+
                    | +calculateRisk() |
                    | +getRiskLevel()  |
                    +--------+---------+
                             |
                             v
                       Risk Information


                    +----------------------+
                    |   ReportGenerator    |
                    +----------------------+
                    | +generateHotspotReport|
                    +----------+-----------+
                               |
                               v
                     gitscope-report.txt

```

![UML class diagram](diagrams/04_class_diagram.png)


## 3. Class Responsibilities
Main

Main is the application entry point. It coordinates the complete GitScope analysis process.

GitHistoryParser

GitHistoryParser reads Git repository history and converts the extracted information into Commit objects.

Commit

Commit represents a Git commit and stores information such as the commit hash, author, and associated file changes.

FileChange

FileChange represents changes made to an individual file within a commit.

It stores additions and deletions and provides the total number of changes.

FileStats

FileStats stores accumulated statistics for a repository file.
It tracks the number of changes, additions, deletions, and total changes.

HotspotAnalyzer

HotspotAnalyzer processes commit information and produces a list of file statistics used to identify frequently changed files.

CouplingAnalyzer

CouplingAnalyzer examines co-change relationships between files and stores the results using maps.

OwnershipAnalyzer

OwnershipAnalyzer analyzes contributors associated with repository files.

ImpactAnalyzer

ImpactAnalyzer analyzes historical relationships between a selected target file and other repository files.

RiskScorer

RiskScorer calculates a normalized risk score from repository characteristics and assigns a corresponding risk level.

ReportGenerator

ReportGenerator writes analysis information to a text-based report file

## 4. Package Relationships

+-------------+
|   model     |
+------+------+
       |
       v
+-------------+
|   parser    |
+------+------+
       |
       v
+-------------+
|  analyzer   |
+------+------+
       |
       v
+-------------+
|  scoring    |
+------+------+
       |
       v
+-------------+
|   report    |
+-------------+

Main coordinates all packages.

## 5. Object-Oriented Design

GitScope uses object-oriented principles by separating responsibilities into dedicated classes.

Encapsulation

Repository data is stored inside model classes and accessed through methods such as getters and analysis methods.

Separation of Responsibilities

Each major operation is handled by a dedicated class:

Parsing → GitHistoryParser
Hotspot detection → HotspotAnalyzer
Coupling → CouplingAnalyzer
Ownership → OwnershipAnalyzer
Impact → ImpactAnalyzer
Risk calculation → RiskScorer
Reporting → ReportGenerator

Reusability

Analysis components receive structured data rather than directly depending on the command-line interface, allowing them to be tested independently.

## 6. Main Relationships

The primary relationships between classes are:

Main
 |
 +--> GitHistoryParser
 |
 +--> HotspotAnalyzer
 |
 +--> CouplingAnalyzer
 |
 +--> OwnershipAnalyzer
 |
 +--> RiskScorer
 |
 +--> ImpactAnalyzer
 |
 +--> ReportGenerator


GitHistoryParser
 |
 +--> List<Commit>
              |
              +--> List<FileChange>


HotspotAnalyzer
 |
 +--> List<FileStats>


RiskScorer
 |
 +--> Risk score + Risk level


ReportGenerator
 |
 +--> gitscope-report.txt

 ## 7. Design Summary

The class structure keeps GitScope modular by separating data representation from processing logic.

This structure makes the application easier to maintain, test, and extend with additional analysis modules.