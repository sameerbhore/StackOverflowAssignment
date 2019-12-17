create table if not exists questions (
    question_id varchar(256) not null,
    question varchar(1000) not null,
    answers_count integer,
    created_by varchar(256) not null,
    created_at bigint not null
);
