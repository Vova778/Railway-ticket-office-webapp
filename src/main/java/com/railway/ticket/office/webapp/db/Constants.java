package com.railway.ticket.office.webapp.db;

public class Constants {
    public static final String URL = "jdbc:mysql://localhost:3306/railway_station_ticket";
    public static final String FULL_URL = "jdbc:mysql://localhost:3306/railway_station_ticket?user=root&password=Vova778ltym1";

    public static final String ROUTES_INSERT_ROUTE = "insert into route values (default,?,?,?,?,?,?,?,?,?,?)";
    public static final String ROUTES_DELETE_ROUTE = "delete from route where id=?";
    public static final String ROUTES_UPDATE_ROUTE = "update route set stoppage_number=?, starting_station_id=?, final_station_id=?, departure_time=?, arrival_time=?, available_seats=?, day=?, schedule_id=? train_id=? where id=?";
    public static final String ROUTES_GET_ROUTE_BY_ID = "select * from route where id=?";
    public static final String ROUTES_GET_ROUTE_BY_SCHEDULE_ID = "select * from route where schedule_id=?";
    public static final String ROUTES_GET_ALL_ROUTES = "SELECT * from route";

    public static final String STATIONS_INSERT_STATION = "insert into station values (default, ?)";
    public static final String STATIONS_DELETE_STATION = "delete from station where id=?";
    public static final String STATIONS_UPDATE_STATION = "update station set name=? where id=?";
    public static final String STATIONS_GET_STATION_BY_ID = "select * from station where id=?";
    public static final String STATIONS_GET_STATION_BY_NAME = "select * from station where name=?";
    public static final String STATIONS_GET_ALL_STATIONS = "select * from station";

    public static final String SCHEDULE_INSERT_SCHEDULE = "insert into schedule values (default, ?,?)";
    public static final String SCHEDULE_DELETE_SCHEDULE = "delete from schedule where id=?";
    public static final String SCHEDULE_UPDATE_SCHEDULE = "update schedule set date=?, train_id=? where id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_ID = "select * from schedule where id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_TRAIN_ID = "select * from schedule where train_id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_DATE = "select * from schedule where date=?";
    public static final String SCHEDULE_GET_ALL_SCHEDULE = "select * from schedule";


    public static final String TICKETS_INSERT_TICKET = "insert into ticket values (default, ?,?,?,?,?,?,?,?)";
    public static final String TICKETS_DELETE_TICKET = "delete from ticket where id=?";
    public static final String TICKETS_UPDATE_TICKET = "update ticket set fare=?, starting_station=?, final_station=?, departure_time=?, arrival_time=?,train_number=?, user_id=?,ticket_status_id where id=?";
    public static final String TICKETS_GET_TICKET_BY_ID = "select * from ticket where id=?";
    public static final String TICKETS_GET_TICKET_BY_USER_ID = "select * from ticket where user_id=?";
    public static final String TICKETS_GET_ALL_TICKETS = "select * from ticket";
    public static final String GET_TICKET_STATUS_BY_TICKET_ID =" select name from ticket_status right join ticket on ticket.ticket_status_id= ticket_status.id where ticket.id=?";
    public static final String GET_TICKET_STATUS_ID_BY_NAME = "SELECT id FROM ticket_status WHERE name=?";

    public static final String TRAINS_INSERT_TRAIN = "insert into train values (default, ?,?)";
    public static final String TRAINS_DELETE_TRAIN = "delete from train where number=?";
    public static final String TRAINS_UPDATE_TRAIN = "update train set seats=? where number=?";
    public static final String TRAINS_GET_TRAIN_BY_NUMBER = "select * from train where number=?";
  // public static final String TRAINS_GET_TRAIN_BETWEEN_STATIONS="select * from routes where";
    public static final String TRAINS_GET_ALL_TRAINS = "select * from train";

    public static final String USERS_INSERT_USER = "insert into user values (default, ?,?,?,?,?,?)";
    public static final String USERS_DELETE_USER = "delete from user where id=?";
    public static final String USERS_UPDATE_USER = "update user set login=?, password_id=?, first_name=?, last_name=?, phone=?, role_id=? where id=?";
    public static final String USERS_GET_USER_BY_ID = "select * from user where id=?";
    public static final String USERS_GET_USER_BY_LOGIN = "select * from user where login=?";
    public static final String USERS_GET_ALL_USERS = "select * from user";
    public static final String GET_ROLE_NAME_BY_ID = "SELECT name FROM role RIGHT JOIN user ON role.id = user.role_id WHERE user.id = ?";
    public static final String GET_ROLE_ID_BY_NAME = "select id from role where name=?";


    private Constants() {
    }
}
