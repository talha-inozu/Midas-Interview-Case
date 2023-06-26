create table IF NOT EXISTS instrument(
    symbol                  varchar                          not null,
    id                      bigserial
        constraint instrument_pkey
            primary key
);
