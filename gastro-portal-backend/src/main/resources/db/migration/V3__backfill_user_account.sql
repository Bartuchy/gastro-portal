INSERT INTO user_account (
    user_id,
    username,
    password,
    is_enabled,
    is_non_locked,
    is_using2fa,
    secret,
    role_id
)
SELECT
    u.id,
    u.username,
    u.password,
    COALESCE(u.is_enabled, FALSE),
    COALESCE(u.is_non_locked, TRUE),
    COALESCE(u.is_using2fa, FALSE),
    u.secret,
    u.role_id
FROM users u
         LEFT JOIN user_account ua ON ua.user_id = u.id
WHERE ua.user_id IS NULL;
