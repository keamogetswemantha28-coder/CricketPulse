## 🏏 Overview

CricketPulse is a data engineering project focused on collecting, storing, transferring, and analysing cricket match data. It explores how raw match data can be transformed into structured information and useful cricket statistics.

Using historical SA20 match data from [Cricsheet](https://cricsheet.org/), the project combines Java, PostgreSQL, SQL, and DolphinDB to explore data ingestion, relational storage, cross-database data transfer, and time-series analytics.

## 🎯 Project Goals

- Ingest cricket match data from JSON files.
- Store structured match data in PostgreSQL.
- Transfer data from PostgreSQL into DolphinDB.
- Query and analyse cricket data using SQL and DolphinDB.
- Develop a foundation for more advanced cricket analytics.

## 📊 Data Source

CricketPulse uses historical SA20 match data from Cricsheet.

The source data is provided in JSON format, with information about matches, teams, players, innings, overs, deliveries, and runs.

## ⚙️ Current Capabilities

- **Data ingestion:** Load a cricket match from a local JSON file using Java.
- **Relational storage:** Store match information in PostgreSQL using a structured schema.
- **SQL analytics:** Query stored cricket data to explore match and player statistics.
- **Cross-database data transfer:** Transfer data from PostgreSQL into DolphinDB.
- **Time-series analytics:** Use DolphinDB queries to explore cricket data.

## 🔄 Data Pipeline

The current workflow connects the following stages:

**Cricsheet JSON → Java ingestion → PostgreSQL → Data transfer process → DolphinDB → Analytics queries**

This workflow demonstrates how data can move from raw source files through relational storage into a time-series analytics environment.

## 🛠️ Technology Stack

- **Java** — data ingestion and application logic.
- **JSON** — source data format.
- **JDBC** — Java database connectivity.
- **PostgreSQL** — relational storage.
- **SQL** — querying and analysing structured data.
- **DolphinDB** — time-series data storage and analytics.
- **Maven** — build and dependency management.

## 🚧 Future Development

- Expand ingestion to support multiple matches.
- Strengthen data validation and error handling.
- Expand cricket statistics and analytical queries.
- Explore replaying historical deliveries as a simulated live match.
- Develop a REST API and dashboard.
- Explore Docker containerisation.

## 🌱 Learning Focus

CricketPulse is a practical learning project through which I am developing my understanding of data engineering concepts, including ingestion, relational data modelling, cross-database data movement, and analytics.

The project is being developed incrementally, with a focus on understanding how data moves between systems and how it can be transformed into useful information.

