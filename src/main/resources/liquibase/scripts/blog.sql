--
-- PostgreSQL database sql
--

CREATE SCHEMA blog;

CREATE SCHEMA user_management;


CREATE TABLE blog.article (
    id bigint NOT NULL,
    article_status_type character varying(255) NOT NULL,
    content character varying(255) NOT NULL,
    created_by character varying(255),
    created_on timestamp without time zone,
    delete boolean NOT NULL,
    modified_by character varying(255),
    modified_on timestamp without time zone,
    published_on timestamp without time zone,
    title character varying(255) NOT NULL
);


CREATE TABLE blog.article_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    article_status_type character varying(255),
    content character varying(255),
    created_by character varying(255),
    created_on timestamp without time zone,
    delete boolean,
    modified_by character varying(255),
    modified_on timestamp without time zone,
    published_on timestamp without time zone,
    title character varying(255)
);

CREATE TABLE blog.article_authors (
    article_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE TABLE blog.article_authors_aud (
    rev integer NOT NULL,
    article_id bigint NOT NULL,
    user_id bigint NOT NULL,
    revtype smallint
);


CREATE TABLE blog.article_categories (
    article_id bigint NOT NULL,
    category_id bigint NOT NULL
);


CREATE TABLE blog.article_categories_aud (
    rev integer NOT NULL,
    article_id bigint NOT NULL,
    category_id bigint NOT NULL,
    revtype smallint
);


CREATE SEQUENCE blog.article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE blog.article_id_seq OWNED BY blog.article.id;


CREATE TABLE blog.category (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255),
    parent_id bigint
);

CREATE TABLE blog.category_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    description character varying(255),
    name character varying(255),
    parent_id bigint
);



CREATE SEQUENCE blog.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE blog.category_id_seq OWNED BY blog.category.id;



CREATE TABLE blog.comment (
    id bigint NOT NULL,
    content character varying(255) NOT NULL,
    created_by character varying(255),
    created_on timestamp without time zone,
    modified_by character varying(255),
    modified_on timestamp without time zone,
    published_on timestamp without time zone,
    title character varying(255) NOT NULL,
    article_id bigint NOT NULL,
    parent_id bigint
);

CREATE TABLE blog.comment_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    content character varying(255),
    created_by character varying(255),
    created_on timestamp without time zone,
    modified_by character varying(255),
    modified_on timestamp without time zone,
    published_on timestamp without time zone,
    title character varying(255),
    article_id bigint,
    parent_id bigint
);

CREATE SEQUENCE blog.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE blog.comment_id_seq OWNED BY blog.comment.id;

CREATE TABLE blog.keyword (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    article_id bigint NOT NULL
);

CREATE TABLE blog.keyword_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    name character varying(255),
    article_id bigint
);

CREATE SEQUENCE blog.keyword_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE blog.keyword_id_seq OWNED BY blog.keyword.id;

CREATE TABLE user_management."user" (
    id bigint NOT NULL,
    created_by character varying(255),
    created_on timestamp without time zone,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_login timestamp without time zone,
    last_name character varying(255) NOT NULL,
    mobile character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);

CREATE TABLE user_management.user_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    created_by character varying(255),
    created_on timestamp without time zone,
    email character varying(255),
    first_name character varying(255),
    last_login timestamp without time zone,
    last_name character varying(255),
    mobile character varying(255),
    password_hash character varying(255),
    role character varying(255),
    username character varying(255)
);

CREATE SEQUENCE user_management.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE user_management.user_id_seq OWNED BY user_management."user".id;


ALTER TABLE ONLY blog.article ALTER COLUMN id SET DEFAULT nextval('blog.article_id_seq'::regclass);

ALTER TABLE ONLY blog.category ALTER COLUMN id SET DEFAULT nextval('blog.category_id_seq'::regclass);

ALTER TABLE ONLY blog.comment ALTER COLUMN id SET DEFAULT nextval('blog.comment_id_seq'::regclass);

ALTER TABLE ONLY blog.keyword ALTER COLUMN id SET DEFAULT nextval('blog.keyword_id_seq'::regclass);

ALTER TABLE ONLY user_management."user" ALTER COLUMN id SET DEFAULT nextval('user_management.user_id_seq'::regclass);


SELECT pg_catalog.setval('blog.article_id_seq', 1, false);
SELECT pg_catalog.setval('blog.category_id_seq', 1, false);
SELECT pg_catalog.setval('blog.comment_id_seq', 1, false);
SELECT pg_catalog.setval('blog.keyword_id_seq', 1, false);
SELECT pg_catalog.setval('user_management.user_id_seq', 1, false);

ALTER TABLE ONLY blog.article_aud
    ADD CONSTRAINT article_aud_pkey PRIMARY KEY (id, rev);
ALTER TABLE ONLY blog.article_authors_aud
    ADD CONSTRAINT article_authors_aud_pkey PRIMARY KEY (rev, article_id, user_id);
ALTER TABLE ONLY blog.article_categories_aud
    ADD CONSTRAINT article_categories_aud_pkey PRIMARY KEY (rev, article_id, category_id);
ALTER TABLE ONLY blog.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);
ALTER TABLE ONLY blog.category_aud
    ADD CONSTRAINT category_aud_pkey PRIMARY KEY (id, rev);
ALTER TABLE ONLY blog.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);
ALTER TABLE ONLY blog.comment_aud
    ADD CONSTRAINT comment_aud_pkey PRIMARY KEY (id, rev);
ALTER TABLE ONLY blog.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);
ALTER TABLE ONLY blog.keyword_aud
    ADD CONSTRAINT keyword_aud_pkey PRIMARY KEY (id, rev);
ALTER TABLE ONLY blog.keyword
    ADD CONSTRAINT keyword_pkey PRIMARY KEY (id);
ALTER TABLE ONLY blog.keyword
    ADD CONSTRAINT uk_hvq9bm3mbguqoicyv02g5crjs UNIQUE (name);
ALTER TABLE ONLY user_management.user_aud
    ADD CONSTRAINT user_aud_pkey PRIMARY KEY (id, rev);
ALTER TABLE ONLY user_management."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
ALTER TABLE ONLY blog.article_categories
    ADD CONSTRAINT fk2574p9xuuvh3pxhlfvq6f7rbf FOREIGN KEY (article_id) REFERENCES blog.article(id);
ALTER TABLE ONLY blog.category
    ADD CONSTRAINT fk2y94svpmqttx80mshyny85wqr FOREIGN KEY (parent_id) REFERENCES blog.category(id);
ALTER TABLE ONLY blog.article_authors
    ADD CONSTRAINT fk3axyjg1s4bna7x9ol7mi91sbh FOREIGN KEY (user_id) REFERENCES user_management."user"(id);
ALTER TABLE ONLY blog.comment
    ADD CONSTRAINT fk5yx0uphgjc6ik6hb82kkw501y FOREIGN KEY (article_id) REFERENCES blog.article(id);
ALTER TABLE ONLY blog.keyword
    ADD CONSTRAINT fk6wlo6af3ievrfrdqpb6mtdvtx FOREIGN KEY (article_id) REFERENCES blog.article(id);
ALTER TABLE ONLY blog.comment
    ADD CONSTRAINT fkde3rfu96lep00br5ov0mdieyt FOREIGN KEY (parent_id) REFERENCES blog.comment(id);
ALTER TABLE ONLY blog.article_categories
    ADD CONSTRAINT fkjuad4a02m73fdxvce6fmpobul FOREIGN KEY (category_id) REFERENCES blog.category(id);
ALTER TABLE ONLY blog.article_authors
    ADD CONSTRAINT fkspag2w8eoclck7kfq9nhd2g88 FOREIGN KEY (article_id) REFERENCES blog.article(id);
