package com.railway.ticket.office.webapp.db;



/**
 * Constants for MySQL
 */
public class Constants {

    private static final String ROUTES_GET = "select  r.* , s.date, starting_s.name starting_station_name, " +
            " final_s.name final_station_name, t.seats total_seats from route r " +
            " right join schedule s on s.id=r.schedule_id " +
            " right join station starting_s on r.starting_station_id=starting_s.id " +
            " right join station final_s on  r.final_station_id=final_s.id " +
            " right join train t on s.train_id = t.number ";
    public static final String ROUTES_INSERT_ROUTE = "insert into route values (default,?,?,?,?,?,?,?,?,?,?)";
    public static final String ROUTES_DELETE_ROUTE = "delete from route where id=?";
    public static final String ROUTES_UPDATE_ROUTE = "update route set stoppage_number=?, starting_station_id=?, final_station_id=?, departure_time=?, arrival_time=?, available_seats=?, day=?, schedule_id=?, train_id=?, price=? where id=?";
    public static final String ROUTES_GET_ROUTE_BY_ID = ROUTES_GET + " where r.id=?";
    public static final String ROUTES_GET_ROUTE_BY_SCHEDULE_ID = ROUTES_GET + " where schedule_id=? order by stoppage_number;";
    public static final String ROUTES_GET_ALL_ROUTES = ROUTES_GET + " order by r.id";
    public static final String ROUTES_GET_ALL_ROUTES_WITH_OFFSET = ROUTES_GET_ALL_ROUTES + " LIMIT 10 OFFSET ?";
    public static final String ROUTES_GET_COUNT = "SELECT COUNT(*) FROM route";
    public static final String ROUTES_FIND_ROUTES_BETWEEN_STATIONS = ROUTES_GET + " where date_add(s.date, interval r.day DAY) = ?" +
            " AND r.stoppage_number >= (select stoppage_number from route where route.starting_station_id=?  and route.schedule_id=s.id) " +
            " AND r.stoppage_number <= (select stoppage_number from route where route.final_station_id=? and route.schedule_id=s.id) order by s.id";
    public static final String ROUTES_GET_ROUTE_BY_TICKET_ID = ROUTES_GET
            + " right join ticket_has_route on ticket_has_route.route_id=r.id where ticket_id=?";


    public static final String STATIONS_INSERT_STATION = "insert into station values (default, ?)";
    public static final String STATIONS_DELETE_STATION = "delete from station where id=?";
    public static final String STATIONS_UPDATE_STATION = "update station set name=? where id=?";
    public static final String STATIONS_GET_STATION_BY_ID = "select * from station where id=?";
    public static final String STATIONS_GET_STATION_BY_NAME = "select * from station where name=?";
    public static final String STATIONS_GET_ALL_STATIONS_WITH_OFFSET = "select * from station order by id LIMIT 10 OFFSET ?";
    public static final String STATIONS_GET_COUNT = "SELECT COUNT(*) FROM station";
    public static final String STATIONS_GET_ALL_STATIONS = "select * from station order by name";


    public static final String SCHEDULE_INSERT_SCHEDULE = "insert into schedule values (default, ?,?)";
    public static final String SCHEDULE_DELETE_SCHEDULE = "delete from schedule where id=?";
    public static final String SCHEDULE_UPDATE_SCHEDULE = "update schedule set date=?, train_id=? where id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_ID = "select * from schedule left join train on schedule.train_id=train.number where id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_TRAIN_ID = "select * from schedule left join train on schedule.train_id=train.number where train_id=?";
    public static final String SCHEDULE_GET_SCHEDULE_BY_DATE = "select * from schedule left join train on schedule.train_id=train.number where date=?";
    public static final String SCHEDULE_GET_ALL_SCHEDULE = "select * from schedule left join train on schedule.train_id=train.number";

    public static final String TICKETS_INSERT_TICKET = "insert into ticket values (default, ?,?,?,?,?,?,?,?)";
    public static final String TICKETS_DELETE_TICKET = "delete from ticket where id=?";
    public static final String TICKETS_UPDATE_TICKET = "update ticket set fare=?, starting_station=?, final_station=?, departure_time=?, arrival_time=?,train_number=?,user_id=?,ticket_status_id=? where id=?";
    public static final String TICKETS_GET_TICKET_BY_ID = "select * from ticket where id=?";
    public static final String TICKETS_GET_TICKET_BY_USER_ID = "select * from ticket where user_id=?";
    public static final String TICKETS_GET_TICKET_BY_USER_ID_WITH_OFFSET = " SELECT * FROM ticket WHERE user_id = ? LIMIT 10 OFFSET ?";
    public static final String TICKETS_GET_ALL_TICKETS = "select * from ticket";
    public static final String TICKETS_GET_COUNT = "SELECT COUNT(*) FROM ticket";

    public static final String TRAINS_INSERT_TRAIN = "insert into train values (?,?)";
    public static final String TRAINS_DELETE_TRAIN = "delete from train where number=?";
    public static final String TRAINS_UPDATE_TRAIN = "update train set seats=? where number=?";
    public static final String TRAINS_GET_TRAIN_BY_NUMBER = "select * from train where number=?";
    public static final String TRAINS_GET_ALL_TRAINS = "select * from train order by number LIMIT 10 OFFSET ?";
    public static final String TRAINS_GET_COUNT = "SELECT COUNT(*) FROM train";

    public static final String USERS_INSERT_USER = "insert into user values (default, ?,?,?,?,?,?)";
    public static final String USERS_DELETE_USER = "delete from user where id=?";
    public static final String USERS_UPDATE_USER = "update user set login=?, password_id=?, first_name=?, last_name=?, phone=?, role_id=? where id=?";
    public static final String USERS_GET_USER_BY_ID = "select * from user where id=?";
    public static final String USERS_GET_USER_BY_LOGIN = "select * from user where login=?";
    public static final String USERS_GET_ALL_USERS = "select * from user order by id LIMIT 10 OFFSET ?";
    public static final String USERS_GET_COUNT = "SELECT COUNT(*) FROM user";
    public static final String TICKETS_INSERT_TICKET_ROUTES = "insert into ticket_has_route value (?, ? , ? , ?, ?,?,?,?)";

    private Constants() {}
}
