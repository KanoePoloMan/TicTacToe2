-- SELECT * FROM games_multiplayer
-- WHERE o = '38a2af1a-9e32-42c6-912b-913a0ff593eb' AND (state = 'WIN_X' OR state = 'WIN_O' OR state = 'DRAW')
-- UNION ALL
-- SELECT * FROM games_multiplayer
-- WHERE x = '38a2af1a-9e32-42c6-912b-913a0ff593eb' AND (state = 'WIN_X' OR state = 'WIN_O' OR state = 'DRAW')
-- ORDER BY date;
-- DELETE FROM games_multiplayer;

SELECT uuid, wins / GREATEST((loses + draws), 1.0) AS ratio FROM user_stats
ORDER BY 2 DESC
LIMIT 4;