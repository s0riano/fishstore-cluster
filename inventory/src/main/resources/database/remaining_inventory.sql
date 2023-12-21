SELECT
    c.catch_id,
    c.seafood_type,
    c.kilos AS total_caught,
    COALESCE(SUM(s.kilos), 0) AS total_sold,
    (c.kilos - COALESCE(SUM(s.kilos), 0)) AS remaining_inventory
FROM
    "catch" c
        LEFT JOIN
    sale s ON c.catch_id = s.catch_id
GROUP BY
    c.catch_id;
