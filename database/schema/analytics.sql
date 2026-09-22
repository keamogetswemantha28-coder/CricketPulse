--Get top run scorer in a match
SELECT p.name, SUM(d.batter_runs) AS total_runs
FROM deliveries d
         JOIN players p ON d.batter_id = p.player_id
GROUP BY p.name
ORDER BY total_runs DESC;


-- Get top wicket taker but exclude runouts
SELECT p.name, COUNT(*) AS wickets
FROM deliveries d
         JOIN players p ON d.bowler_id = p.player_id
WHERE d.wicket_kind IS NOT NULL AND d.wicket_kind != 'run out'
GROUP BY p.name
ORDER BY wickets DESC;


-- Bowling economy rate (runs conceded per over)
-- ball / 6 Note: over count is an approximation (balls / 6), since wides/no-balls
-- wides and no balls are not excluded from the ball count yet
SELECT p.name,
       SUM(d.total_runs) AS runs_conceded,
       COUNT(*) AS balls_bowled,
       ROUND(SUM(d.total_runs) / (COUNT(*) / 6.0), 2) AS economy_rate
FROM deliveries d
         JOIN players p ON d.bowler_id = p.player_id
GROUP BY p.name
ORDER BY economy_rate ASC;