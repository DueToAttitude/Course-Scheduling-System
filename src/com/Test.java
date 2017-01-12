package com;

import com.bean.ScheduleBean;
import com.bean.WorkBean;
import com.core.ConnDB;
import com.core.GeneralFunc;
import com.dao.ScheduleDao;
import com.dao.WorkDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by recycle on 12/16 0016.
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        demo1();
    }
    static void demo1() throws SQLException {
        ConnDB connDB1 = new ConnDB();
        ConnDB connDB2 = new ConnDB();
        ResultSet rs1;
        ResultSet rs2;
        int tag = 0;

        //检测 schedule
        for (int i = 0; i < 20; i++) {
            rs1 = connDB1.executeQuery("select * from `schedule` where `time`=" + (i + 1));
            while (rs1.next()) {
                rs2 = connDB2.executeQuery("select * from `schedule` where `time`=" + (i + 1));
                while (rs2.next()) {
                    if(rs1.getInt(1) != rs2.getInt(1) && (rs1.getInt(3) == rs2.getInt(3) || rs1.getInt(5) == rs2.getInt(5) || rs1.getInt(6) == rs2.getInt(6))) {
                        System.out.println("idSchedule " + rs1.getInt(1) + "  idSchedule " + rs2.getInt(1));
                        tag = 1;
                    }
                }
                connDB2.close();
            }
            connDB1.close();
        }
        if (tag == 0) {
            System.out.println("schedule 时间正常");
        }
        int sizeTotal = 0;
        rs1 = connDB1.executeQuery("select `size` from `work`");
        while (rs1.next()) {
            sizeTotal += rs1.getInt(1);
        }
        connDB1.close();
        rs1 = connDB1.executeQuery("select count(*) from `schedule`");
        if(rs1.next() && sizeTotal == rs1.getInt(1)) {
            System.out.println("schedule 行数正常");
        }
        connDB1.close();

        //检测 group
        int groupScheduled = 0;
        tag = 0;
        rs1 = connDB1.executeQuery("select * from `group`");
        while (rs1.next()) {
            groupScheduled = 0;
            rs2 = connDB2.executeQuery("select `size` from `work` where `idGroup`=" + rs1.getInt(1));
            while (rs2.next()) {
                groupScheduled += rs2.getInt(1);
            }
            connDB2.close();
            if(rs1.getInt(3) != groupScheduled) {
                System.out.println("group : " + rs1.getInt(1) + "  scheduled in group : " + rs1.getInt(3) + "  in work : " + groupScheduled);
                tag = 1;
            }
        }
        connDB1.close();
        if(tag == 0) {
            System.out.println("group 正常");
        }

        //检测 teacher
        tag = 0;
        rs1 = connDB1.executeQuery("select * from `teacher`");
        while (rs1.next()) {
            rs2 = connDB2.executeQuery("select count(*) from `schedule` where `idTeacher`=" + rs1.getInt(1));
            if(rs2.next() && rs2.getInt(1) != rs1.getInt(4)) {
                System.out.println("teacher : " + rs1.getInt(1) + "  scheduled in teacher : " + rs1.getInt(4) + "  in schedule : " + rs2.getInt(1));
                tag = 1;
            }
            connDB2.close();
        }
        connDB1.close();
        if(tag == 0) {
            System.out.println("teacher 正常");
        }

        //检测 room
        tag = 0;
        rs1 = connDB1.executeQuery("select * from `room`");
        while (rs1.next()) {
            rs2 = connDB2.executeQuery("select count(*) from `schedule` where `idRoom`=" + rs1.getInt(1));
            if(rs2.next() && rs2.getInt(1) != rs1.getInt(3)) {
                System.out.println("room : " + rs1.getInt(1) + "  scheduled in room : " + rs1.getInt(3) + "  in schedule : " + rs2.getInt(1));
                tag = 1;
            }
            connDB2.close();
        }
        connDB1.close();
        if(tag == 0) {
            System.out.println("room 正常");
        }
    }
    static void demo2() {
        int[] a = {1,2,3,4,5};
        System.out.println(GeneralFunc.randomInt(a));
    }
    static void demo3() {
        int[] a = GeneralFunc.randomArrays2(3,0);
        for(int i : a) {
            System.out.println(i);
        }
    }

    static void clearSchedule() throws SQLException {
        ConnDB connDB = new ConnDB();
        ResultSet rs = connDB.executeQuery("select * from `schedule`");
        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            new ScheduleDao().deleteRow(scheduleBean);
        }
    }
}

class MyComparator implements Comparator<ScheduleBean> {


    @Override
    public int compare(ScheduleBean o1, ScheduleBean o2) {
        return o1.getTime() - o2.getTime();
    }
}
