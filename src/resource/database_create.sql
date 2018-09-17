PRAGMA foreign_keys=on;

CREATE TABLE if not exists 'catalog' (
  'id' INTEGER PRIMARY KEY AUTOINCREMENT,
  'title' TEXT NOT NULL,
  'type' TEXT NOT NULL,
  'description' TEXT,
  'image' TEXT,
  'mass' FLOAT DEFAULT NULL,
  'cable_length' FLOAT DEFAULT NULL
);

CREATE TABLE if not exists 'apparatus_to_cable' (
  'apparatus_id' INTEGER NOT NULL,
  'cable_id' INTEGER NOT NULL,
  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),
  FOREIGN KEY (cable_id) REFERENCES catalog(id),
  PRIMARY KEY (apparatus_id,cable_id)
);

CREATE TABLE if not exists 'apparatus_to_ta' (
  'apparatus_id' INTEGER NOT NULL,
  'ta_id' INTEGER NOT NULL,
  'ta_count' INTEGER NOT NULL,
  FOREIGN KEY (apparatus_id) REFERENCES catalog(id),
  FOREIGN KEY (ta_id) REFERENCES catalog(id),
  PRIMARY KEY (apparatus_id,ta_id)
);

CREATE TABLE if not exists 'cable_wires' (
  'cable_id' INTEGER NOT NULL,
  'wire_material' TEXT NOT NULL,
  'wire_count' INTEGER NOT NULL,
  FOREIGN KEY (cable_id) REFERENCES catalog(id),
  PRIMARY KEY (cable_id,wire_material)
);

CREATE TABLE if not exists 'type_of_military_part' (
  'id' INTEGER PRIMARY KEY AUTOINCREMENT,
  'title' TEXT NOT NULL
);

CREATE TABLE if not exists 'category_of_manage_point' (
  'id' INTEGER PRIMARY KEY AUTOINCREMENT,
  'title' TEXT NOT NULL,
  'military_part' INTEGER NOT NULL,
  FOREIGN KEY (military_part) REFERENCES type_of_military_part(id)
);

-- CREATE TABLE if not exists 'ParentContent' (
--   'parent_id' INTEGER NOT NULL,
--   'child_id' INTEGER NOT NULL,
--   'child_count' INTEGER NOT NULL,
--   FOREIGN KEY (apparatus_id) REFERENCES catalog(id),
--   FOREIGN KEY (ta_id) REFERENCES catalog(id),
--   PRIMARY KEY (apparatus_id,ta_id)
-- );
-- create view apparatus_to_cable
--   as
--   select parent_id as apparatus_id,
--     child_id as cable_id
--   from ParentContent pc join catalog c on pc.child_id=c.id
--   where c.type='CableAndOther'