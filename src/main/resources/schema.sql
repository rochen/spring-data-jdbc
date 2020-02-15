
create table user (
  id long auto_increment PRIMARY key,
  username varchar(255) UNIQUE,
  password varchar(255),
  email varchar(255) UNIQUE,
  bio text,
  image varchar(512)
);

create table follow_ref (
  follow long not NULL,
  user long not null,
  primary key(follow, user)
);

create table article (
  id long auto_increment primary key,
  user_id long,
  slug varchar(255) UNIQUE,
  title varchar(255),
  description text,
  body text,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table article_favorite (
  article long not null,
  user long not null,
  primary key(article, user)
);

create table tag (
  id long auto_increment primary key,
  name varchar(255)
);

create table article_tag (
  article long not null,
  tag long not NULL,
  article_key long,
  PRIMARY key(article, tag)
);

create table comment (
  id long auto_increment primary key,
  body text,
  article_id long,
  user_id long,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);