CREATE TABLE teams(
                      team_id SERIAL PRIMARY KEY,
                      name TEXT UNIQUE NOT NULL
);

CREATE TABLE players(
                        player_id SERIAL PRIMARY KEY,
                        name TEXT NOT NULL,
                        team_id INTEGER NOT NULL REFERENCES teams(team_id)
);

ALTER TABLE players ADD CONSTRAINT unique_player_per_team UNIQUE (name, team_id);

CREATE TABLE matches(
                        match_id SERIAL PRIMARY KEY,
                        match_number INTEGER NOT NULL,
                        match_type TEXT NOT NULL,
                        result TEXT,
                        match_date DATE NOT NULL,
                        team1_id INTEGER NOT NULL REFERENCES teams(team_id),
                        team2_id INTEGER NOT NULL REFERENCES teams(team_id)
);

CREATE TABLE deliveries(
                           delivery_id SERIAL PRIMARY KEY,
                           match_id INTEGER NOT NULL REFERENCES matches(match_id),
                           innings_number INTEGER NOT NULL,
                           batting_team_id INTEGER NOT NULL REFERENCES teams(team_id),
                           over_number INTEGER NOT NULL,
                           actual_delivery_label TEXT NOT NULL,
                           batter_id INTEGER NOT NULL REFERENCES players(player_id),
                           bowler_id INTEGER NOT NULL REFERENCES players(player_id),
                           non_striker_id INTEGER NOT NULL REFERENCES players(player_id),
                           batter_runs INTEGER NOT NULL,
                           extra_runs INTEGER NOT NULL,
                           total_runs INTEGER NOT NULL,
                           extra_type TEXT,
                           player_out_id INTEGER REFERENCES players(player_id),
                           wicket_kind TEXT
);