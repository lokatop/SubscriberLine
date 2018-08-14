PRAGMA foreign_keys=on;

CREATE TABLE if not exists 'catalog' (
  'id' INTEGER PRIMARY KEY AUTOINCREMENT,
  'title' TEXT NOT NULL,
  'type' TEXT NOT NULL,
  'description' TEXT,
  'image' TEXT,
  'data' TEXT
);

CREATE TABLE if not exists 'apparatus_to_cable' (
  'apparatus_id' INTEGER NOT NULL,
  'cable_id' INTEGER NOT NULL,
  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),
  FOREIGN KEY (cable_id) REFERENCES catalog(id)
);

CREATE TABLE if not exists 'apparatus_to_ta' (
  'apparatus_id' INTEGER NOT NULL,
  'ta_id' INTEGER NOT NULL,
  'ta_count' INTEGER NOT NULL,
  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),
  FOREIGN KEY (ta_id) REFERENCES catalog(id)
);