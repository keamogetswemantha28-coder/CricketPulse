## Overview

CricketPulse is a data engineering platform that collects cricket match data, stores, 
processes, and validates it, and provides useful cricket statistics through an API 
or dashboard.

## Track
WTC-XVETXBD4

## Goal
- Collect cricket match data.
- Process and validate the data.
- Store the data in a structured way.
- Generate useful cricket statistics.

## Purpose
The purpose of CricketPulse is to collect and process cricket match data so that it 
can be used to generate useful statistics and insights about matches and players.

## Data used

### Data Source
- CricketPulse uses Cricket match data  that comes from CricSheet.
- The data consist of SA20 matches.

### Data Format
The match data is provided in JSON format. Each JSON file represents
a single cricket match and contains information about the match,
teams, players, innings, overs, deliveries, and runs.

## Current Status

CricketPulse is under active development. Progress so far:

- [x] Understand the data structure (Cricsheet JSON format)
- [x] Design a normalized PostgreSQL schema (teams, players, matches, deliveries)
- [x] Build a Java/JDBC ingestion pipeline (JSON → PostgreSQL)
- [x] Write SQL analytics queries (top run scorers, wicket takers, economy rate)
- [x] Time-series analytics with DolphinDB
- [ ] Streaming replay (simulating a live match from historical data)
- [ ] REST API
- [ ] Dashboard
- [ ] Docker containerization

The project currently supports ingesting a single match at a time from a local JSON
file into PostgreSQL, and querying the resulting data directly via SQL. The API and
dashboard are planned but not yet prioritized — current focus is on the data
engineering and time-series analytics core.

## Setup

### Prerequisites
- [Java 21+ (JDK)](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/) (tested on version 18)

### 1. Install PostgreSQL
Download and install PostgreSQL for your OS. During setup, you'll be asked to set a
password for the `postgres` superuser — remember it, you'll need it below.

### 2. Start the PostgreSQL server
If it's not already running as a background service, start it manually
(adjust the path/version to match your install):
```powershell
& "C:\Program Files\PostgreSQL\<version>\bin\pg_ctl.exe" start -D "C:\Program Files\PostgreSQL\<version>\data"
```

### 3. Create the database
Connect with `psql` and create the database:
```powershell
& "C:\Program Files\PostgreSQL\<version>\bin\psql.exe" -U postgres
```
```sql
CREATE DATABASE cricketpulse;
```

### 4. Load the schema
Exit `psql` (`\q`), then run the provided schema file against your new database:
```powershell
& "C:\Program Files\PostgreSQL\<version>\bin\psql.exe" -U postgres -d cricketpulse -f database\schema\schema.sql
```
This creates all four tables (`teams`, `players`, `matches`, `deliveries`) with their
constraints and relationships.

### 5. Set environment variables
The app reads database credentials from environment variables — they are never
hardcoded in the source. Set these before running the project:
```powershell
$env:CRICKETPULSE_DB_USER = "postgres"
$env:CRICKETPULSE_DB_PASSWORD = "<the password you set in step 1>"
```
(If running from an IDE like IntelliJ, set these in your Run Configuration's
Environment Variables field instead.)

### 6. Run the ingestion pipeline
Build and run `MatchLoader`, which reads a sample match JSON file and loads it into
PostgreSQL:
```powershell
mvn compile
mvn exec:java -Dexec.mainClass="za.co.keamogetswe.cricketpulse.MatchLoader"
```
On success, you should see `Match loaded successfully!`. You can then verify the
data directly in `psql`:
```sql
SELECT COUNT(*) FROM deliveries;
```

### 7. Run analytics queries
Sample queries (top run scorers, wicket takers, economy rate) are in
`database/queries/analytics.sql` — run them against `cricketpulse` in `psql` or any
SQL client.

