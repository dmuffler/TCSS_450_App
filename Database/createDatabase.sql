create database SmartConvert
use SmartConvert

create table Users
(UserID		int				primary key, 
UserFN		varchar(128)	not null,
UserLN		varchar(128)	not null)

create table LoginData
(LoginID	int				primary key,
Username	varchar(128)	not null,
UserPassword	varchar(128)	not null)

create table UserLogin
(UserLoginID	int			primary key,
UserID			int			foreign key references Users(UserID) not null,
LoginID			int			foreign key references LoginData(LoginID) not null)

create table Locations
(LocationID		int			primary key,
LocationName	varchar(128)	not null)

create table Currency
(CurrencyID		int			primary key,
CurrencyName	varchar(128)	not null)

create table LocationsCurrency
(LocationCurr	int			primary key,
LocationID		int			foreign key references Locations(LocationID) not null,
CurrencyID		int			foreign key references Currency(CurrencyID) not null)

create table UserActivity
(CurrConvertID	int			primary key,
UserID			int			foreign key references Users(UserID) not null,
defaultLocID	int			foreign key references LocationsCurrency(LocationCurr))