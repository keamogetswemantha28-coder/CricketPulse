-- Runs scored per over (innings 1)
select over_number, sum(total_runs) as runs
from t
where innings_number = 1
group by over_number
order by over_number;


-- Cumulative score and run rate progression, over by over (innings 1)
-- Step 1: get per-over totals, correctly ordered
overTotals = select over_number, sum(total_runs) as runs
             from t
             where innings_number = 1
             group by over_number
             order by over_number;

-- Step 2: apply cumulative sum and compute run rate
select over_number, runs,
       cumsum(runs) as cumulative_runs,
       cumsum(runs) / (over_number + 1.0) as run_rate
from overTotals;