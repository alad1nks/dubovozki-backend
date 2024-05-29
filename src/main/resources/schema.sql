CREATE TABLE IF NOT EXISTS bus (
    id                  INT             NOT NULL    PRIMARY KEY,
    day_of_week         INT             NOT NULL,
    day_time            BIGINT          NOT NULL,
    day_time_string     VARCHAR(5)      NOT NULL,
    direction           VARCHAR(3)      NOT NULL,
    station             VARCHAR(3)      NOT NULL
);
