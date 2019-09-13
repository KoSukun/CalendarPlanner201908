use calendarplanner;

create table memberinfo (
				id varchar(30) not null primary key,
				pw varchar(30) not null,
				name varchar(50),
				email varchar(50) not null,
				tel varchar(30),
				joindate varchar(30)
			);