## Requirements

- [MySQL Community Server v9.1.0](https://dev.mysql.com/downloads/mysql/)
- A Java IDE (reccomended: [Intellij IDEA Community](https://www.jetbrains.com/idea/download/?section=linux#:~:text=IntelliJ%20IDEA%20Community%20Edition))

## Folder Structure

The workspace contains two main folders, where:

- `src`: the folder that contains the source code, divided in the "client" and "server" packages.
- `lib`: the folder that contains dependencies (GSON v2.11.0, MySQL Connector J v9.1.0).

## Running the Server

- Create the `announcements_system` MySQL database. User has to be root with password "" (empty string).

  `mysql -u root -p -e "create database announcements_system";`

- Import the dump into the database.

  `mysql -u root -p announcements_system < database.sql`

- Build and run the "ServerLauncher" class to start the server.

## Running the Client

- Build and run the "ClientLauncher" class to start the client.
