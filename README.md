# CricketPulse 🏏

**An educational Data Engineering project focused on collecting, storing, processing, and analysing cricket match data.**

CricketPulse explores how raw cricket match data can be transformed into structured, useful information. Using historical SA20 match data from [Cricsheet](https://cricsheet.org/), the project uses Java, JSON, PostgreSQL, and SQL to build a foundation for cricket statistics and time-series analytics.

## 🎯 Project Goals

- Collect cricket match data from JSON files.
- Validate and process raw match data.
- Store match information in a structured PostgreSQL database.
- Query match data to explore player and team statistics.
- Explore time-series analytics using DolphinDB.

## 📊 Data Source

CricketPulse uses historical SA20 cricket match data from Cricsheet.

The data is provided in JSON format. Each match file contains information about teams, players, innings, overs, deliveries, and runs.

## ⚙️ Current Capabilities

The project currently supports:

- Loading a single cricket match from a local JSON file.
- Ingesting match data into PostgreSQL.
- Storing data across a relational schema containing teams, players, matches, and deliveries.
- Querying the stored data directly using SQL.

The ingestion workflow is:

`Cricsheet JSON → Java ingestion pipeline → PostgreSQL → SQL queries`

## 🛠️ Technology Stack

- **Java** — application logic and data ingestion.
- **JSON** — source data format.
- **PostgreSQL** — relational data storage.
- **JDBC** — Java-to-database connectivity.
- **SQL** — querying and analysing stored data.
- **DolphinDB** — time-series analytics exploration.
- **Maven** — project build and dependency management.

## 🚧 Development Roadmap

CricketPulse is under active development. Planned areas of work include:

- Expanding data ingestion beyond a single match.
- Strengthening data validation.
- Developing and expanding cricket analytics.
- Exploring time-series analytics with DolphinDB.
- Simulating live-match data using historical deliveries.
- Building a REST API and dashboard.
- Exploring Docker for containerisation.

Features will be marked as complete as they are implemented and tested.

## 🚀 Getting Started

### Prerequisites

- Java 21 or later
- Maven
- PostgreSQL

### 1. Create the database

Open `psql` as your PostgreSQL user and create the database:

```sql
CREATE DATABASE cricketpulse;
```

### 2. Load the database schema

From the project root, run the schema script using `psql`:

```powershell
psql -U postgres -d cricketpulse -f database/schema/schema.sql
```

This creates the `teams`, `players`, `matches`, and `deliveries` tables, including their relationships and constraints.

### 3. Configure database credentials

Set the environment variables in PowerShell, replacing the example password with your local PostgreSQL password:

```powershell
$env:CRICKETPULSE_DB_USER = "postgres"
$env:CRICKETPULSE_DB_PASSWORD = "your_local_password"
```

Alternatively, configure these variables in your IDE's run configuration.

### 4. Build the project

```powershell
mvn compile
```

### 5. Run the ingestion pipeline

Run `MatchLoader` using the project's configured Maven execution setup:

```powershell
mvn exec:java "-Dexec.mainClass=za.co.keamogetswe.cricketpulse.MatchLoader"
```

The application reads a sample match JSON file and loads its data into PostgreSQL.

A successful run should display:

```text
Match loaded successfully!
```

### 6. Verify the loaded data

Connect to the `cricketpulse` database and run:

```sql
SELECT COUNT(*) FROM deliveries;
```

This checks how many delivery records are stored in the database.

### 7. Explore the analytics queries

Sample analytics queries are available in:

`database/queries/analytics.sql`

Run these queries against the database to explore the available cricket statistics.

## 🌱 Learning Focus

CricketPulse is also a practical learning project. It helps me develop my understanding of:

- Data ingestion and pipeline design.
- Relational database modelling.
- Data validation and processing.
- SQL-based analytics.
- Time-series data.

My current focus is strengthening the data engineering foundation before expanding the project with additional features.

---

*This project is a work in progress. Its documentation and capabilities will evolve as development continues.*

### 7. Run analytics queries
Sample queries (top run scorers, wicket takers, economy rate) are in
`database/queries/analytics.sql` — run them against `cricketpulse` in `psql` or any
SQL client.

