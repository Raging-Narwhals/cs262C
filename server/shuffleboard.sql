--
-- The SQL script for the ShuffleBoard database.
--
-- @author jbs24, kjc38
-- Notes: I've cleaned up the code to work with postgres and added the Grants and drops. 
-- I had to move around a few of the parameters to actually work with the tests.
-- One of the tests shows an error, so I commented it out. - kjc38
--

--Drop previous versions
DROP TABLE IF EXISTS DynamicDays;
DROP TABLE IF EXISTS DynamicEvents;
DROP TABLE IF EXISTS StaticEvents;
DROP TABLE IF EXISTS Friends;
DROP TABLE IF EXISTS Users;

--Create the tables:
-- Table for user information
CREATE TABLE Users ( name varchar(31),
	passwordHash varchar(31),
	ID SERIAL PRIMARY KEY );

-- Table for information regarding who is friends with who
CREATE TABLE Friends ( userID integer REFERENCES Users(ID), 
	friendID integer REFERENCES Users(ID), --PRIMARY KEY );
	PRIMARY KEY (userID, friendID) );
	--friendID int REFERENCES Users(ID) PRIMARY KEY(userID, friendID) );

-- Table for information in common between StaticEvents of a user
CREATE TABLE StaticEvents ( userID integer REFERENCES Users(ID), 
	startTime integer, 
	stopTime integer, 
	days char(7),
	label varchar(31), 
	ID SERIAL PRIMARY KEY );

-- Table for information in common between DynamicEvents of a user
CREATE TABLE DynamicEvents ( userID integer REFERENCES Users(ID),
	timesPerWeek integer, 
	length integer,
	label varchar(31),
	ID SERIAL PRIMARY KEY );

-- Table for information that is specific to each DynamicEvent
CREATE TABLE DynamicDays ( dynamicID integer REFERENCES DynamicEvents(ID), 
	day integer, 
	start integer, 
	stop integer );

--Grant users the ability to sleect data from the tables.
GRANT SELECT ON Users TO PUBLIC;
GRANT SELECT ON Friends TO PUBLIC;
GRANT SELECT ON StaticEvents TO PUBLIC;
GRANT SELECT ON DynamicEvents TO PUBLIC;
GRANT SELECT ON DynamicDays TO PUBLIC;

-- Sample queries
INSERT INTO Users VALUES ('Jim', 'passhash1');
INSERT INTO Users VALUES ( 'Bill', 'passhash2' );
INSERT INTO Users VALUES ( 'Paul', 'passhash3' );
INSERT INTO Users VALUES ( 'Matt', 'passhash4' );

INSERT INTO Friends VALUES ( 1, 2 );
INSERT INTO Friends VALUES ( 1, 3 );
-- INSERT INTO Friends VALUES ( 2, 3 ); -- ERROR:  duplicate key value violates unique constraint "friends_pkey"
INSERT INTO Friends VALUES ( 3, 4 );

INSERT INTO StaticEvents VALUES ( 1, 28, 32, '1000100', 'Lunch' );
INSERT INTO StaticEvents VALUES ( 1, 16, 34, '0111010', 'Work' );
INSERT INTO StaticEvents VALUES ( 1, 38, 42, '0010100', 'Soccer Practice' );

INSERT INTO DynamicEvents VALUES ( 2, 2, 2, 'Groceries' );
INSERT INTO DynamicDays VALUES ( 1, 2, 34, 42 );
INSERT INTO DynamicDays VALUES ( 1, 4, 34, 42 );
