INSERT INTO USER (BIO, EMAIL, IMAGE, PASSWORD, USERNAME) 
	VALUES ('bio', 'robinly@tw.ibm.com', null, null, 'robinly');

INSERT INTO USER (BIO, EMAIL, IMAGE, PASSWORD, USERNAME) 
	VALUES ('bio', 'yap@tw.ibm.com', null, null, 'yap');

INSERT INTO FOLLOW_REF (FOLLOW, USER) VALUES (2,1);
INSERT INTO FOLLOW_REF (FOLLOW, USER) VALUES (1,2);

INSERT INTO ARTICLE (BODY, CREATED_AT, DESCRIPTION, SLUG, TITLE, UPDATED_AT, USER_ID) 
	VALUES ('tunisia is nice', CURRENT_TIMESTAMP, 'biz trip', 'tunisia-is-nice', 'title', CURRENT_TIMESTAMP, 1);
	
INSERT INTO COMMENT (BODY, ARTICLE_ID, USER_ID, CREATED_AT, UPDATED_AT)
	VALUES ('comment1', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
INSERT INTO COMMENT (BODY, ARTICLE_ID, USER_ID, CREATED_AT, UPDATED_AT)
	VALUES ('comment2', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
INSERT INTO tag (name) VALUES ('spring');
INSERT INTO tag (name) VALUES ('boot');
INSERT INTO tag (name) VALUES ('react');
INSERT INTO tag (name) VALUES ('angular');

INSERT INTO article_tag VALUES (1,1,1);
INSERT INTO article_tag VALUES (1,2,2);

INSERT INTO article_favorite VALUES (1,2);


INSERT INTO ARTICLE (TITLE, BODY, SLUG, USER_ID, CREATED_AT, DESCRIPTION, UPDATED_AT) VALUES 
 ('list article 1', 'article list test 1', 'list-article-1', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 2', 'article list test 2', 'list-article-2', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 3', 'article list test 3', 'list-article-3', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 4', 'article list test 4', 'list-article-4', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 5', 'article list test 5', 'list-article-5', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 6', 'article list test 6', 'list-article-6', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 7', 'article list test 7', 'list-article-7', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 8', 'article list test 8', 'list-article-8', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article 9', 'article list test 9', 'list-article-9', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article A', 'article list test A', 'list-article-A', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article B', 'article list test B', 'list-article-B', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP),
 ('list article C', 'article list test C', 'list-article-C', 1, CURRENT_TIMESTAMP, 'desc', CURRENT_TIMESTAMP);

