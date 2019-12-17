create table if not exists answer(
	answer_id varchar(256) not null,
    question_id varchar(256) not null,
    aanswer varchar(1000) not null,
    comments_count integer,
    created_by varchar(256) not null,
    created_at bigint not null
);

create table if not exists comment (
	comment_id varchar(256) not null,
    contex_id varchar(256) not null,
	context_type varchar(20) not null,
    comment varchar(1000) not null,
	comments_count integer,
    created_by varchar(256) not null,
    created_at bigint not null
);

