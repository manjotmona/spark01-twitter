# spark01-twitter
This application assumes there is a TwitterTable TABLE in twitterdata DATABASE in SQL server.

+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| ID     | int(11)      | NO   | PRI | NULL    | auto_increment |
| status | varchar(255) | YES  |     | NULL    |                |
| count  | int(11)      | YES  |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+
