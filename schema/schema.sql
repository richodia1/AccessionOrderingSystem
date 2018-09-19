--
-- Project Base schema
-- 1/9/2010 MObreza: generated from blank application
-- 25/9/2010 MObreza: added UserAccessTag entity
--

create table Notification (
    id bigint not null auto_increment,
    createdBy varchar(255),
    createdDate datetime,
    lastUpdated datetime,
    lastUpdatedBy varchar(255),
    message longtext,
    originAppName varchar(200),
    `read` bit,
    title longtext,
    subscriberNotificationId bigint,
    primary key (id)
) type=InnoDB;

create table Preference (
    preferenceType integer not null,
    id bigint not null auto_increment,
    createdBy varchar(255),
    createdDate datetime,
    lastUpdated datetime,
    lastUpdatedBy varchar(255),
    description varchar(255),
    preferenceKey varchar(255),
    boolVal bit,
    charVal char(1),
    doubleVal double precision,
    longVal bigint,
    stringVal varchar(255),
    intVal integer,
    bigVal decimal(19,2),
    dateVal datetime,
    userId bigint,
    primary key (id)
) type=InnoDB;

create table Query (
    id bigint not null auto_increment,
    createdBy varchar(255),
    createdDate datetime,
    lastUpdated datetime,
    lastUpdatedBy varchar(255),
    version integer not null,
    allowedRoles longtext,
    description longtext,
    heads longtext,
    query longtext,
    shortName varchar(100),
    templateName varchar(100),
    title varchar(200),
    primary key (id)
) type=InnoDB;

create table QueryParameter (
    id bigint not null auto_increment,
    name varchar(100),
    query_id bigint,
    primary key (id)
) type=InnoDB;

create table Template (
    id bigint not null auto_increment,
    footer longtext,
    header longtext,
    shortName varchar(100) not null,
    template longtext,
    title varchar(250) not null,
    primary key (id),
    unique (shortName)
) type=InnoDB;

create table User (
    id bigint not null auto_increment,
    authenticationType integer,
    department varchar(255),
    description varchar(255),
    displayName varchar(255),
    firstName varchar(255),
    lastLogin datetime,
    lastLoginFailed datetime,
    lastName varchar(255),
    mail varchar(255),
    password varchar(255),
    staffId varchar(255),
    status integer,
    username varchar(255) unique,
    primary key (id)
) type=InnoDB;

create table UserAccessTag (
    User_id bigint not null,
    tag longtext not null
) type=InnoDB;

create table UserDelegation (
    id bigint not null auto_increment,
    application varchar(50) not null,
    delegatedTo bigint not null,
    ownerId bigint not null,
    primary key (id)
) type=InnoDB;

create table UserLookup (
    ID bigint not null auto_increment,
    identifier varchar(200) not null,
    userid bigint not null,
    primary key (ID)
) type=InnoDB;

create table UserNotification (
    id bigint not null auto_increment,
    createdBy varchar(255),
    createdDate datetime,
    lastUpdated datetime,
    lastUpdatedBy varchar(255),
    version integer not null,
    eventName varchar(200) not null,
    formatString longtext,
    frequency varchar(100),
    priority varchar(100),
    subscribed bit not null,
    user_id bigint not null,
    primary key (id)
) type=InnoDB;

create table UserPasswordRequest (
    id bigint not null auto_increment,
    dateGenerated datetime,
    dateUsed datetime,
    `key` varchar(255),
    status integer not null,
    user_id bigint,
    primary key (id)
) type=InnoDB;

create table UserRole (
    ID bigint not null auto_increment,
    application varchar(50) not null,
    role varchar(50) not null,
    userId bigint not null,
    primary key (ID)
) type=InnoDB;

create table UserSupervisor (
    id bigint not null auto_increment,
    application varchar(100),
    supervisorId bigint,
    userId bigint,
    primary key (id)
) type=InnoDB;

alter table Notification 
    add index FK2D45DD0B89944C95 (subscriberNotificationId), 
    add constraint FK2D45DD0B89944C95 
    foreign key (subscriberNotificationId) 
    references User (id);

alter table Preference 
    add index FK1FCE98FB36E9810D (userId), 
    add constraint FK1FCE98FB36E9810D 
    foreign key (userId) 
    references User (id);

alter table QueryParameter 
    add index FK32A3E7E16058DB98 (query_id), 
    add constraint FK32A3E7E16058DB98 
    foreign key (query_id) 
    references Query (id);

alter table UserAccessTag 
    add index FK269E5FEB5FF93CF6 (User_id), 
    add constraint FK269E5FEB5FF93CF6 
    foreign key (User_id) 
    references User (id);

alter table UserDelegation 
    add index FK44143F73707D4D21 (delegatedTo), 
    add constraint FK44143F73707D4D21 
    foreign key (delegatedTo) 
    references User (id);

alter table UserDelegation 
    add index FK44143F7329E06DF5 (ownerId), 
    add constraint FK44143F7329E06DF5 
    foreign key (ownerId) 
    references User (id);

alter table UserLookup 
    add index FKC97E194536E9810D (userid), 
    add constraint FKC97E194536E9810D 
    foreign key (userid) 
    references User (id);

alter table UserNotification 
    add index FKC31EB4765FF93CF6 (user_id), 
    add constraint FKC31EB4765FF93CF6 
    foreign key (user_id) 
    references User (id);

alter table UserPasswordRequest 
    add index FK8175E0095FF93CF6 (user_id), 
    add constraint FK8175E0095FF93CF6 
    foreign key (user_id) 
    references User (id);

alter table UserRole 
    add index FKF3F7670136E9810D (userId), 
    add constraint FKF3F7670136E9810D 
    foreign key (userId) 
    references User (id);

alter table UserSupervisor 
    add index FK8C15E5B3A74D756A (supervisorId), 
    add constraint FK8C15E5B3A74D756A 
    foreign key (supervisorId) 
    references User (id);

alter table UserSupervisor 
    add index FK8C15E5B336E9810D (userId), 
    add constraint FK8C15E5B336E9810D 
    foreign key (userId) 
    references User (id);
