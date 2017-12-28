5;
CREATE SEQUENCE con_leg_seq 
start with 0
minvalue 0
increment by 1;

CREATE TABLE Image (

id integer PRIMARY KEY,

link text

);



CREATE TABLE Character (

id integer PRIMARY KEY,

name text not NULL,

type text not NULL,

description text,

image_id integer REFERENCES Image ON DELETE CASCADE,

father_id integer,

mother_id integer,

UNIQUE(name, type)

);



CREATE TABLE Country (

id integer PRIMARY KEY,

name text UNIQUE not NULL,

description text

);



CREATE TABLE Author (

id integer PRIMARY KEY,

name text not NULL,

surname text

);



CREATE TABLE Legend (

id integer PRIMARY KEY,

name text UNIQUE not NULL,

author_id integer REFERENCES Author ON DELETE CASCADE,

country_id integer REFERENCES Country ON DELETE CASCADE,

image_id integer REFERENCES Image ON DELETE CASCADE,

description text

);



CREATE TABLE Artifact (

id integer PRIMARY KEY,

image_id integer REFERENCES Image ON DELETE CASCADE,

description text

);



CREATE TABLE Event (

id integer PRIMARY KEY,

description text UNIQUE,

image_id integer

);



CREATE TABLE Users (

id integer PRIMARY KEY,

login text UNIQUE not NULL,

password text not NULL,

date_of_check date not NULL CONSTRAINT first CHECK(date_of_check <= 'now')

);



CREATE TABLE Award (

id integer PRIMARY KEY,

description text UNIQUE not NULL

);



CREATE TABLE Note (

id integer PRIMARY KEY,

owner_id integer,

image_id integer REFERENCES Image ON DELETE CASCADE,

date date not NULL CONSTRAINT first CHECK(date <= 'now'),

description text

);



CREATE TABLE Phrase (

id integer PRIMARY KEY,

description text

);



CREATE TABLE Rating (

id integer PRIMARY KEY,

object_id integer,

one integer,

two integer,

three integer,

four integer,

five integer

);



CREATE TABLE User_Legend (

id integer PRIMARY KEY,
user_id integer REFERENCES Users ON DELETE CASCADE,

legend_id integer REFERENCES Legend ON DELETE CASCADE,

UNIQUE(user_id, legend_id)

);



CREATE TABLE User_Award (

id integer PRIMARY KEY,
user_id integer REFERENCES Users ON DELETE CASCADE,

award_id integer REFERENCES Award ON DELETE CASCADE,

UNIQUE(user_id, award_id)

);



CREATE TABLE Character_Legend (

id integer PRIMARY KEY,
legend_id integer REFERENCES Legend ON DELETE CASCADE,

character_id integer REFERENCES Character ON DELETE CASCADE,

UNIQUE(legend_id, character_id)

);



CREATE TABLE Character_Artifact (

id integer PRIMARY KEY,
character_id integer REFERENCES Character ON DELETE CASCADE,

art_id integer REFERENCES Artifact ON DELETE CASCADE,

UNIQUE(character_id, art_id)

);



CREATE TABLE Country_Legend (

id integer PRIMARY KEY default nextval('con_leg_seq'),
country_id integer REFERENCES Country ON DELETE CASCADE,

legend_id integer REFERENCES Legend ON DELETE CASCADE,

UNIQUE(country_id, legend_id)

);



CREATE TABLE Country_Image (

id integer PRIMARY KEY,
country_id integer REFERENCES Country ON DELETE CASCADE,

image_id integer REFERENCES Image ON DELETE CASCADE,

UNIQUE(country_id, image_id)

);



CREATE TABLE Question_Answer (

id integer PRIMARY KEY,
question_id integer REFERENCES Phrase ON DELETE CASCADE,

answer_id integer REFERENCES Phrase ON DELETE CASCADE,

UNIQUE(question_id, answer_id)

);



CREATE TABLE Answer_Event (

id integer PRIMARY KEY,
answer_id integer REFERENCES Phrase ON DELETE CASCADE,

event_id integer REFERENCES Event ON DELETE CASCADE,

UNIQUE(answer_id, event_id)

);






CREATE OR REPLACE FUNCTION con_leg()

RETURNS TRIGGER AS $$
BEGIN

INSERT INTO Country_Legend(id, country_id, legend_id)
 VALUES(nextval('con_leg_seq'), new.country_id, new.id);

RETURN new;

END;

$$ LANGUAGE plpgsql;



CREATE TRIGGER con_leg_trigger AFTER INSERT ON Legend FOR EACH ROW EXECUTE PROCEDURE con_leg();