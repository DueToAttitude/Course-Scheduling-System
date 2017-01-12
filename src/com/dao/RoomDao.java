package com.dao;

import com.bean.RoomBean;
import com.core.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/17 0017.
 */
public class RoomDao {
    public ArrayList<RoomBean> queryAll() throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from `room`";
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<RoomBean> roomBeanArrayList = new ArrayList<>();

        while (rs.next()) {
            RoomBean roomBean = new RoomBean();
            roomBean.setIdRoom(rs.getInt(1));
            roomBean.setRoomName(rs.getString(2));
            roomBean.setRoomScheduled(rs.getInt(3));
            roomBeanArrayList.add(roomBean);
        }
        connDB.close();
        return roomBeanArrayList;
    }

    public void insertRow(RoomBean roomBean) {
        ConnDB connDB = new ConnDB();
        String sql = "insert into `room` (`roomName`,`roomScheduled`) values ('" + roomBean.getRoomName() + "',0)";
        connDB.executeUpdate(sql);
        connDB.close();
    }

    //table room 删除一行
    public void deleteRow(RoomBean roomBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        connDB.executeUpdate("delete from `room` where `idroom`=" + roomBean.getIdRoom());
        connDB.close();

        connDB.executeUpdate("update `schedule` set `idRoom`=0 where `idRoom`=" + roomBean.getIdRoom());
        connDB.close();
    }

    public String roomName(int idRoom) throws SQLException {
        ConnDB connDB = new ConnDB();
        String roomName = null;
        ResultSet rs = connDB.executeQuery("select * from `room`");
        while (rs.next()) {
            if(idRoom == rs.getInt(1)) {
                roomName = rs.getString(2);
            }
        }
        connDB.close();
        return roomName;
    }
}
