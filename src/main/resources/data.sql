INSERT INTO USER (BIO, EMAIL, IMAGE, PASSWORD, USERNAME) 
	VALUES ('bio', 'robinly@tw.ibm.com', null, null, 'robinly');
	
INSERT INTO ARTICLE (BODY, CREATED_AT, DESCRIPTION, SLUG, TITLE, UPDATED_AT, USER_ID) 
	VALUES ('tunisia is nice', CURRENT_TIMESTAMP, 'biz trip', 'slog', 'title', CURRENT_TIMESTAMP, 1);