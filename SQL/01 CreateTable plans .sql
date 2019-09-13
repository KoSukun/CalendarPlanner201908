use calendarplanner;

create table
	Plans (PlanId int not null primary key,
	UserId varchar(20) not null,
	EffectiveDate date not null,
	TimeCreated Time not null,
	Place varchar(30),
	Note varchar(100));