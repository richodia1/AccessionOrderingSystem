
-- 1/9/2010 update Query
alter table Query add column templateName varchar(100) after `shortName`;

-- 1/9/2010 new table Template, no link to other entities
create table Template (
    id bigint not null auto_increment,
    footer longtext,
    header longtext,
    shortName varchar(100) not null,
    template longtext,
    title varchar(250) not null,
    primary key (id)
) type=InnoDB;

-- 24/9/2010 new table UserAccessTag
create table UserAccessTag (
    User_id bigint not null,
    tag longtext not null
) type=InnoDB;

alter table UserAccessTag add index FK269E5FEB5FF93CF6 (User_id), add constraint FK269E5FEB5FF93CF6  foreign key (User_id) references User (id);

-- 2/9/2010 unique on Template.shortName
alter table Template add unique (shortName);

-- 29/10/2010 Template auditing
alter table Template add createdBy varchar(255), add createdDate datetime, add lastUpdated datetime, add lastUpdatedBy varchar(255);
