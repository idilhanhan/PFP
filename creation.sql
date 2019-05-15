-- Table structure for User
CREATE TABLE IF NOT EXISTS user(
  user_id INTEGER PRIMARY KEY,
  user_name TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL
);

-- Table structure for Project Idea
CREATE TABLE IF NOT EXISTS project_idea(
  idea_id INTEGER PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  abstract TEXT NOT NULL,
  creator INTEGER,
  member_limit INTEGER
);

-- Table structure for Many-to-Many relationship btw user and project idea
CREATE TABLE IF NOT EXISTS participants(
  participant_id INTEGER,
  project_id INTEGER,
  rel_id	INTEGER,

  PRIMARY KEY(rel_id),
  FOREIGN KEY(participant_id) REFERENCES user(user_id),
  FOREIGN KEY(project_id) REFERENCES project_idea(idea_id)
);

-- Table structure for keywords
CREATE TABLE IF NOT EXISTS keywords(
  keyword_id INTEGER PRIMARY KEY,
  word TEXT UNIQUE NOT NULL
);

-- Table structure for Many-to-Many relationship btw keywords and project idea
﻿CREATE TABLE IF NOT EXISTS idea_keywords (
    pro_idea_id	INTEGER,
	word_id	INTEGER,
	rel_id	INTEGER,
	PRIMARY KEY(rel_id),
	FOREIGN KEY(pro_idea_id) REFERENCES project_idea(idea_id),
	FOREIGN KEY(word_id) REFERENCES keywords(keyword_id)
);