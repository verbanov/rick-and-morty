--liquibase formatted sql
--changeset <vverbanov>:<add-external-id-column-to-movie-character-table>

ALTER TABLE public.movie_character ADD external_id BIGINT;

--rollback ALTER TABLE DROP COLUMN external_id;