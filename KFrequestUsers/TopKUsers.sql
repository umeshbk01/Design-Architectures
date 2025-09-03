SELECT user_id, COUNT(*) as logins
FROM UserLogins
WHERE login_date = CURDATE()
GROUP BY user_id
ORDER BY logins DESC
LIMIT K;