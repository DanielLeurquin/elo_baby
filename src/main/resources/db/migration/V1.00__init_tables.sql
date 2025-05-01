CREATE TABLE public.app_user
(
    id bigserial not null primary key,
    username varchar,
    elo int,
    role varchar
);

CREATE TABLE public.game
(
    id bigserial not null primary key,
    date timestamp default CURRENT_TIMESTAMP,
    type varchar
);

CREATE TABLE public.player_score
(
    id bigserial not null primary key,
    player_id bigint,
    game_id bigint,
    score int,
    start_elo int,
    end_elo int,
    team varchar,
    position varchar
);