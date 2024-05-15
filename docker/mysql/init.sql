create table member
(
    id                 bigint not null AUTO_INCREMENT comment '사용자 아이디',
    name               varchar(30) not null comment '사용자 이름',
    nickname           varchar(30) not null comment '사용자 닉네임',
    email              varchar(50) not null comment '사용자 이메일',
    created_by         bigint not null comment '사용자 생성자 아이디',
    created_date_time  datetime default now() comment '사용자 생성 시간',
    modified_by        bigint comment '사용자 수정자 아이디',
    modified_date_time datetime default now() comment '사용자 수정 시간',
    primary key (id)
);

create table category
(
    id                 bigint not null AUTO_INCREMENT comment '카테고리 아이디',
    parent_id          bigint comment '카테고리 부모 아이디',
    name               varchar(24) comment '카테고리 이름',
    created_by         bigint not null comment '카테고리 생성자 아이디',
    created_date_time  datetime default now() comment '카테고리 생성 시간',
    modified_by        bigint comment '카테고리 수정자 아이디',
    modified_date_time datetime default now() comment '카테고리 수정 시간',
    primary key (id)
);

create table article
(
    id                 bigint  not null AUTO_INCREMENT comment '게시글 아이디',
    title              varchar(100) not null comment '게시글 제목',
    content            varchar(3000) not null comment '게시글 내용',
    category_id        bigint not null comment '카테고리 아이디',
    created_by         bigint not null comment '게시글 생성자 아이디',
    created_date_time  datetime default now() comment '게시글 생성 시간',
    modified_by        bigint comment '게시글 수정자 아이디',
    modified_date_time datetime default now() comment '게시글 수정 시간',
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
    created_date_time  datetime default now() comment '댓글 작성 시간',
    modified_by        bigint comment '댓글 수정자 아이디',
    modified_date_time datetime default now() comment '댓글 수정 시간',
    is_deleted         boolean not null comment '댓글 삭제 유무',
    primary key (id)
);

create table likes
(
    id                 bigint not null AUTO_INCREMENT comment '좋아요 아이디',
    article_id         bigint not null comment '게시글 아이디',
    member_id          bigint not null comment '좋아요 생성자 아이디',
    created_by          bigint not null comment '좋아요 생성자 아이디',
    created_date_time  datetime default now() comment '좋아요 생성 시간',
    modified_by        bigint comment '좋아요 수정자 아이디',
    modified_date_time datetime default now() comment '좋아요 수정 시간',
    primary key (id),
    foreign key (article_id) references article (id)
);

create table login_history
(
    id              bigint not null AUTO_INCREMENT comment '로그인 이력 아이디',
    member_id       bigint not null comment '사용자 아이디',
    login_date_time datetime default now() comment '로그인 시간',
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

insert into member (id, name, nickname, email, created_by, modified_by)
values (1, '성준혁', '성준혁', 'noose@kakao.com', 1, 1),
(2, '권규정', '권규정', 'kgu1060@daum.net', 2, 2),
(3, '노영록', '노영록', 'nstockley2@nymag.com', 3, 3),
(4, '김영철', '김영철', 'mbillson3@comcast.net', 4, 4),
(5, '박지수', '박지수', 'rpittel4@si.edu', 5, 5),
(6, '아이유', '아이유', 'iu4@si.edu', 6, 6),
(7, '수지', '수지', 'suzy@si.edu', 7, 7),
(8, '차은우', '차은우', 'cha@si.edu', 8, 8),
(9, '김은희', '김은희', 'mother@si.edu', 9, 9),
(10, '김민희', '김민희', 'mother2@si.edu', 10, 10);

insert into category (id, name, parent_id, created_by, modified_by)
values
(1, '쌍용아파트', null, 6, 6),
(2, '현대아파트', null,  5, 5),
(3, '맥북', null, 9, 9),
(4, '갤럭시북', null, 10, 10),
(5, 'LG그램', null, 2, 2),
(6, '아이폰', 1, 7, 7),
(7, '갤럭시폰', 2, 8, 8),
(8, '갤럭시북', 3, 1, 1),
(9, '아이패드', 8, 3, 3),
(10, '에어팟', 9, 4, 4);

insert into article (id, title, content, category_id, created_by, modified_by, is_deleted)
values (1, '너무너무재밌어', '같이하자', 1, 1, 1, false),
(2, '내이름은 코난', '탐정이죠', 2, 2, 2, false),
(3, '자기의 일은 스스로하자', '우리는 척척척 스스로 어린이', 3, 3, 3, false),
(4, '어디로 가야 하죠~', '아저씨', 4, 4, 4, false),
(5, '이건아니자나이건아니자나', '이건아?', 5, 5, 5, false);

insert into comment (id, parent_id, content, article_id, created_by, modified_by, is_deleted)
values
(1, null, '깔깔깔', 1, 1, 1, false),
(2, null, '껄껄껄', 2, 2, 2, false),
(3, null, '준혁입니다', 3, 3, 3, false),
(4, null, '영철에서왔습니다', 4, 4, 4, false),
(5, null, '영록다운영록다운', 5, 5, 5, false),
(6, 1, '벼락맞아벼락맞아', 1, 1, 1, false),
(7, 2, '영록업영록업', 2, 2, 2, false),
(8, 3, '준혁다운준혁다운', 3, 3, 3, false),
(9, 4, '벼락맞고', 4, 4, 4, false),
(10, 5, '머리타', 5, 5, 5, false);
