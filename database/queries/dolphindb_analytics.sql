-- Runs scored per over
select over_number, sum(total_runs) as runs
from t
where innings_number = 1
group by over_number
order by over_number;


-- Cumulative score and run rate progression
overTotals = select over_number, sum(total_runs) as runs
             from t
             where innings_number = 1
             group by over_number
             order by over_number;

-- Step2: Apply cumulative sum and compute run rate
select over_number, runs,
       cumsum(runs) as cumulative_runs,
       cumsum(runs) / (over_number + 1.0) as run_rate
from overTotals;