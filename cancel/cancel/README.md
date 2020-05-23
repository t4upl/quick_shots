This project shows how to cancel  long running query using hibernate. For this example PostgreSQL 9.5.8 is used.

In order to simulate long running query a postgres function is used. Run the code below to add function to your database:

```
CREATE OR REPLACE FUNCTION public.wait_for_me(nubmer_of_sec integer)
  RETURNS integer AS
$BODY$
DECLARE passed BOOLEAN;
BEGIN
        PERFORM pg_sleep(nubmer_of_sec);

        RETURN 666;
END;
$BODY$
```

 

