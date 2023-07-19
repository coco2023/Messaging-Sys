CREATE TABLE kafkamessaging.users (
    user_id BIGSERIAL PRIMARY KEY,
    fname VARCHAR(32) NOT NULL,
    lname VARCHAR(32) NOT NULL,
    mobile VARCHAR(32) NOT NULL,
    created_at DATE NOT NULL
);

CREATE TABLE kafkamessaging.access_token (
    token_id BIGSERIAL PRIMARY KEY,    
    token VARCHAR(256) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES kafkamessaging.users(user_id),
    created_at DATE NOT NULL
);

CREATE TABLE kafkamessaging.contacts (
    contact_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES kafkamessaging.users(user_id),
    contact_user_id BIGINT NOT NULL REFERENCES kafkamessaging.users(user_id)
);

CREATE TABLE kafkamessaging.messages (
    message_id BIGSERIAL PRIMARY KEY,
    from_user_id BIGINT NOT NULL REFERENCES kafkamessaging.users(user_id),
    to_user_id BIGINT NOT NULL REFERENCES kafkamessaging.users(user_id),
    message VARCHAR(512) NOT NULL,
    sent_at DATE NOT NULL
);
