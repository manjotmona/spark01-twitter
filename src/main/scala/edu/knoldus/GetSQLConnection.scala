package edu.knoldus

import java.sql.{Connection, DriverManager}

/**
 * Created by pallavi on 8/3/18.
 */
class GetSQLConnection {
  def getConnection(): Connection = {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost/twitterdata"
    val username = "root"
    val password = "root"

    Class.forName(driver)
    DriverManager.getConnection(url, username, password)

  }

}
