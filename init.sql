USE CUSCATLAN;

INSERT INTO CUSTOMER (
    FIRTS_NAME,
    LAST_NAME,
    AGE,
    BIRTHDAY,
    USER_CREATED,
    DATE_CREATED,
    USER_UPDATED,
    DATE_UPDATED
) VALUES (
             'Juan',
             'Pérez',
             30,
             '1993-05-02',
             'system',
             NOW(),
             'system',
             NOW()
         );