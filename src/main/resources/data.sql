insert into category(name) values ('IT'), ('컴퓨터');

insert into member(name, nickname, email) values
('영록', '영록록', 'aaa@naver.com'),
('준혁', '준혁혁', 'bbb@naver.com'),
('규정', '규정정', 'ccc@naver.com');


insert into article(title, content, category_id, is_deleted) values
('테스트 제목 A', '테스트 내용 A', 1, false),
('테스트 제목 B', '테스트 내용 B', 1, false),
('테스트 제목 C', '테스트 내용 C', 2, false);

insert into comments(article_id, content, created_by, is_deleted) values
(1, '테스트 댓글 A', 1, false),
(1, '테스트 댓글 B', 2, false),
(1, '테스트 댓글 C', 3, false);
