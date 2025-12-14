CREATE TABLE users (
                              id varchar(10) NOT NULL,
                              name varchar(20) NOT NULL,
                              "password" varchar(10) NOT NULL,
                              CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE users
    ADD COLUMN level SMALLINT NOT NULL DEFAULT 0,
    ADD COLUMN login INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN recommend INTEGER NOT NULL DEFAULT 0;
;