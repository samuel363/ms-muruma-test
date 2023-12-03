CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user"
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4
(
),
    name VARCHAR
(
    100
) not null,
    email VARCHAR
(
    100
) UNIQUE not null,
    password VARCHAR
(
    100
) not null,
    created timestamp not null DEFAULT now
(
),
    modified timestamp not null DEFAULT now
(
),
    last_login timestamp not null DEFAULT now
(
),
    token text,
    is_active boolean not null
    );

CREATE TABLE IF NOT EXISTS phone
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4
(
),
    number INT not null,
    city_code VARCHAR
(
    100
) not null,
    country_code VARCHAR
(
    100
) not null,
    user_id UUID,
    CONSTRAINT fk_user FOREIGN KEY
(
    user_id
) REFERENCES "user"
(
    id
) ON DELETE CASCADE
    );

COMMIT;