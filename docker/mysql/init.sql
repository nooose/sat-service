create table member
(
    id                 bigint not null AUTO_INCREMENT comment '사용자 아이디',
    name               varchar(30) not null comment '사용자 이름',
    nickname           varchar(30) not null comment '사용자 닉네임',
    email              varchar(50) not null comment '사용자 이메일',
    created_by         bigint not null comment '사용자 생성자 아이디',
    created_date_time  timestamp default current_timestamp comment '사용자 생성 시간',
    modified_by        bigint comment '사용자 수정자 아이디',
    modified_date_time timestamp default current_timestamp comment '사용자 수정 시간',
    primary key (id)
);

create table category
(
    id                 bigint not null AUTO_INCREMENT comment '카테고리 아이디',
    parent_id          bigint comment '카테고리 부모 아이디',
    name               varchar(24) comment '카테고리 이름',
    created_by         bigint not null comment '카테고리 생성자 아이디',
    created_date_time  timestamp default current_timestamp comment '카테고리 생성 시간',
    modified_by        bigint comment '카테고리 수정자 아이디',
    modified_date_time timestamp default current_timestamp comment '카테고리 수정 시간',
    primary key (id)
);

create table article
(
    id                 bigint  not null AUTO_INCREMENT comment '게시글 아이디',
    title              varchar(100) not null comment '게시글 제목',
    content            varchar(3000) not null comment '게시글 내용',
    category_id        bigint not null comment '카테고리 아이디',
    created_by         bigint not null comment '게시글 생성자 아이디',
    created_date_time  timestamp default current_timestamp comment '게시글 생성 시간',
    modified_by        bigint comment '게시글 수정자 아이디',
    modified_date_time timestamp default current_timestamp comment '게시글 수정 시간',
    is_deleted         boolean default false comment '게시글 삭제 유무',
    primary key (id),
    foreign key (category_id) references category (id)
);

create table comment
(
    id                 bigint not null AUTO_INCREMENT comment '댓글 아이디',
    parent_id          bigint comment '부모 댓글 아이디',
    content            varchar(1000) not null comment '댓글 내용',
    article_id         bigint not null comment '게시글 아이디',
    created_by         bigint not null comment '댓글 작성자 아이디',
    created_date_time  timestamp default current_timestamp comment '댓글 작성 시간',
    modified_by        bigint comment '댓글 수정자 아이디',
    modified_date_time timestamp default current_timestamp comment '댓글 수정 시간',
    is_deleted         boolean not null comment '댓글 삭제 유무',
    primary key (id)
);

create table likes
(
    id                 bigint not null AUTO_INCREMENT comment '좋아요 아이디',
    article_id         bigint not null comment '게시글 아이디',
    member_id          bigint not null comment '좋아요 생성자 아이디',
    created_by          bigint not null comment '좋아요 생성자 아이디',
    created_date_time  timestamp default current_timestamp comment '좋아요 생성 시간',
    modified_by        bigint comment '좋아요 수정자 아이디',
    modified_date_time timestamp default current_timestamp comment '좋아요 수정 시간',
    primary key (id),
    foreign key (article_id) references article (id)
);

create table login_history
(
    id              bigint not null AUTO_INCREMENT comment '로그인 이력 아이디',
    member_id       bigint not null comment '사용자 아이디',
    login_date_time timestamp default current_timestamp comment '로그인 시간',
    primary key (id)
);

create table point
(
    id        bigint  not null AUTO_INCREMENT comment '포인트 아이디',
    member_id bigint  not null comment '사용자 아이디',
    point     integer not null comment '포인트',
    type      varchar(20) not null comment '포인트 유형',
    primary key (id)
);

insert into member (id, name, nickname, email, created_by, created_date_time, modified_by, modified_date_time)
values (1, '성준혁', '성준혁', 'noose@kakao.com', 1, '2023-10-12 10:09:10', 1, '2023-10-01 11:09:04'),
(2, '권규정', '권규정', 'kgu1060@daum.net', 2, '2023-09-13 12:56:00', 2, '2023-12-23 17:27:48'),
(3, '노영록', '노영록', 'nstockley2@nymag.com', 3, '2023-11-25 09:26:02', 3, '2023-07-24 14:59:06'),
(4, '김영철', '김영철', 'mbillson3@comcast.net', 4, '2024-02-11 01:58:53', 4, '2023-09-27 16:48:50'),
(5, '박지수', '박지수', 'rpittel4@si.edu', 5, '2024-03-31 15:14:16', 5, '2023-07-18 15:56:07'),
(6, '아이유', '아이유', 'iu4@si.edu', 6, '2024-03-31 15:14:16', 6, '2023-07-18 15:56:07'),
(7, '수지', '수지', 'suzy@si.edu', 7, '2024-03-31 15:14:16', 7, '2023-07-18 15:56:07'),
(8, '차은우', '차은우', 'cha@si.edu', 8, '2024-03-31 15:14:16', 8, '2023-07-18 15:56:07'),
(9, '김은희', '김은희', 'mother@si.edu', 9, '2024-03-31 15:14:16', 9, '2023-07-18 15:56:07'),
(10, '김민희', '김민희', 'mother2@si.edu', 10, '2024-03-31 15:14:16', 10, '2023-07-18 15:56:07');

insert into category (id, name, parent_id, created_by, created_date_time, modified_by, modified_date_time)
values
(1, 'Century', null, 6, '2023-07-28 10:15:48', 6, '2024-01-30 16:02:41'),
(2, 'Terrain', null,  5, '2023-07-20 15:00:23', 5, '2023-07-07 05:29:56'),
(3, 'Aztek', null, 9, '2023-07-24 21:13:00', 9, '2024-02-17 02:49:02'),
(4, 'F350', null, 10, '2023-07-03 12:34:43', 10, '2023-09-19 14:35:25'),
(5, '280ZX', null, 2, '2024-01-18 21:20:49', 2, '2023-09-17 19:55:33'),
(6, 'Grand Cherokee', 1, 7, '2023-07-24 10:39:46', 7, '2024-03-01 09:20:13'),
(7, 'Evora', 2, 8, '2023-08-17 18:24:28', 8, '2023-10-11 09:32:58'),
(8, '911', 3, 1, '2023-09-29 03:14:57', 1, '2024-04-06 02:25:33'),
(9, 'M-Class', 8, 3, '2024-02-10 05:40:08', 3, '2023-09-21 11:56:11'),
(10, '3 Series', 9, 4, '2023-12-13 10:10:26', 4, '2024-01-15 14:36:35');

insert into article (id, title, content, category_id, created_by, created_date_time, modified_by, modified_date_time, is_deleted)
values (1, 'Sales Associate', 'DQM', 1, 1, '2023-12-19 12:07:24', 1, '2023-11-11 18:53:35', false),
(2, 'Office Assistant II', 'VCV', 2, 2, '2024-02-07 07:28:51', 2, '2023-10-19 02:15:53', false),
(3, 'Sales Associate', 'LSF', 3, 3, '2023-12-03 07:15:11', 3, '2023-07-30 20:10:16', false),
(4, 'Office Assistant IV', 'SIX', 4, 4, '2024-04-14 17:30:48', 4, '2023-08-03 03:24:09', false),
(5, 'Chief Design Engineer', 'YDN', 5, 5, '2023-09-08 19:24:43', 5, '2024-03-15 12:29:37', false);

insert into comment (id, parent_id, content, article_id, created_by, created_date_time, modified_by, modified_date_time, is_deleted)
values
(1, null, 'CRL', 1, 1, '2024-03-22 17:38:25', 1, '2024-01-09 00:03:56', false),
(2, null, 'FAM', 2, 2, '2023-08-09 08:22:08', 2, '2023-09-04 13:13:47', false),
(3, null, 'AIP', 3, 3, '2023-06-25 20:21:59', 3, '2024-01-19 03:40:02', false),
(4, null, '0', 4, 4, '2024-04-14 22:15:23', 4, '2023-12-30 19:28:59', false),
(5, null, 'OHA', 5, 5, '2024-04-03 00:18:39', 5, '2023-12-18 02:44:53', false),
(6, 1, 'SYW', 1, 1, '2023-08-13 07:57:32', 1, '2024-03-22 07:50:42', false),
(7, 2, 'YVT', 2, 2, '2023-05-18 14:26:58', 2, '2024-03-10 13:27:49', false),
(8, 3, 'TOP', 3, 3, '2023-12-18 15:51:58', 3, '2023-11-29 00:04:17', false),
(9, 4, 'BYU', 4, 4, '2024-04-29 08:53:51', 4, '2023-09-24 11:14:12', false),
(10, 5, 'CPS', 5, 5, '2023-05-24 12:12:24', 5, '2024-02-28 02:46:52', false);
