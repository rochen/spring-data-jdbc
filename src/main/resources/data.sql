INSERT INTO USER (BIO, EMAIL, IMAGE, PASSWORD, USERNAME) 
	VALUES ('bio', 'robinly@tw.ibm.com', null, null, 'robinly');

INSERT INTO USER (BIO, EMAIL, IMAGE, PASSWORD, USERNAME) 
	VALUES ('bio', 'yap@tw.ibm.com', null, null, 'yap');

INSERT INTO FOLLOW_REF (FOLLOW, USER) VALUES (2,1);

INSERT INTO ARTICLE (BODY, CREATED_AT, DESCRIPTION, SLUG, TITLE, UPDATED_AT, USER_ID) 
	VALUES ('tunisia is nice', CURRENT_TIMESTAMP, 'biz trip', 'tunisia-is-nice', 'title', CURRENT_TIMESTAMP, 1);