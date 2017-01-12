package com.dao;

import com.bean.ScheduleBean;
import com.bean.WorkBean;
import com.core.ConnDB;
import com.core.GeneralFunc;
import com.core.SettingProp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by recycle on 12/16 0016.
 */
public class ScheduleDao {
    public static final int RECYCLE_MAX_TIME = 50;

    private ArrayList<Integer> queryTime(ScheduleBean scheduleBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        ResultSet rs;
        ArrayList<Integer> timeList = new ArrayList<Integer>();

        if(scheduleBean.getIdGroup() > 0) {
            rs = connDB.executeQuery("select `time` from `schedule` where `idGroup`=" + scheduleBean.getIdGroup());
            while (rs.next()) {
                int time = rs.getInt(1);
                for(int a : timeList) {
                    if(a == time) {
                        time = 0;
                        break;
                    }
                }
                if(time != 0) {
                    timeList.add(Integer.valueOf(time));
                }
            }
            connDB.close();
        }
        if(scheduleBean.getIdTeacher() > 0) {
            rs = connDB.executeQuery("select `time` from `schedule` where `idTeacher`=" + scheduleBean.getIdTeacher());
            while (rs.next()) {
                int time = rs.getInt(1);
                for(int a : timeList) {
                    if(a == time) {
                        time = 0;
                        break;
                    }
                }
                if(time != 0) {
                    timeList.add(Integer.valueOf(time));
                }
            }
            connDB.close();
        }
        if(scheduleBean.getIdRoom() > 0) {
            rs = connDB.executeQuery("select `time` from `schedule` where `idRoom`=" + scheduleBean.getIdRoom());
            while (rs.next()) {
                int time = rs.getInt(1);
                for(int a : timeList) {
                    if(a == time) {
                        time = 0;
                        break;
                    }
                }
                if(time != 0) {
                    timeList.add(Integer.valueOf(time));
                }
            }
            connDB.close();
        }
        return timeList;
    }

    //更新教室
    public int fillRoomRemain() throws SQLException {
        ConnDB connDB1 = new ConnDB();
        ConnDB connDB2 = new ConnDB();
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        int tag = 0;
        int tag1 = 4;

        rs1 = connDB1.executeQuery("select * from `schedule` where `idRoom`=0");
        while (rs1.next()) {
            rs2 = connDB2.executeQuery("select * from `room` order by `roomScheduled` asc");
            while (rs2.next()) {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setIdSchedule(rs1.getInt(1));
                scheduleBean.setIdRoom(rs2.getInt(1));
                scheduleBean.setIdTeacher(0);
                scheduleBean.setTime(0);
                tag = fillRow(scheduleBean);
                if(tag == 4) {
                    break;
                }
            }
            connDB2.close();
            if(tag != 4) {
                System.out.println("schedule id = " + rs1.getInt(1) + ": 找不到时间匹配的教室");
                tag1 = tag;
            }
        }
        connDB1.close();
        return tag1;
    }

    //更新时间,修改 table work 中 column workScheduled
    public int fillTimeRemain() throws SQLException {
        int tag = 0;
        int tag1 = 4;

        ConnDB connDB = new ConnDB();
        ResultSet rs = connDB.executeQuery("select * from `schedule` where `time`<=0 or `time`>" + (new SettingProp().getNum()) * 5);
        while (rs.next()) {
            int[] ints = GeneralFunc.randomArrays((new SettingProp().getNum()) * 5);
            for (int i : ints) {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setIdSchedule(rs.getInt(1));
                scheduleBean.setTime(i);
                scheduleBean.setIdTeacher(0);
                scheduleBean.setIdRoom(0);
                tag = fillRow(scheduleBean);
                if(tag == 4) {
                    break;
                }
            }
            if(tag != 4) {
                System.out.println("schedule id = " + rs.getInt(1) + ": 找不到时间匹配的时间" + tag);
                tag1 = tag;
            }
        }
        connDB.close();
        return tag1;
    }

    public int fillTeacherRemain() throws SQLException {
        ConnDB connDB1 = new ConnDB();
        ConnDB connDB2 = new ConnDB();
        ResultSet rs1;
        ResultSet rs2;
        int tag = 0;
        int tag1 = 4;

        rs1 = connDB1.executeQuery("select *,count(distinct `idWork`) from `schedule` where `idTeacher`=0 group by `idWork`");
        while (rs1.next()) {
            rs2 = connDB2.executeQuery("select * from `teacher` where `idCourse`=" + rs1.getInt(4) + " order by `teacherScheduled` asc");
            while (rs2.next()) {
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.setIdWork(rs1.getInt(2));
                scheduleBean.setIdTeacher(rs2.getInt(1));
                tag = fillRow(scheduleBean);
                if(tag == 4) {
                    break;
                }
            }
            connDB2.close();
            if(tag != 4) {
                System.out.println("idWork = " + rs1.getInt(1) + ": 找不到时间匹配的教师");
                tag1 = tag;
            }
        }
        connDB1.close();
        return tag1;
    }

    //更新一行
    public int fillRow(ScheduleBean scheduleBean) throws SQLException {
        if(scheduleBean.getTime() <= 0 && scheduleBean.getIdRoom() <= 0 && scheduleBean.getIdTeacher() <= 0) {
            return 0;   //数据都为0，无效
        }
        ScheduleBean scheduleBean1 = new ScheduleBean();
        ConnDB connDB = new ConnDB();

        ResultSet rs = connDB.executeQuery("select * from `schedule` where `idschedule`=" + scheduleBean.getIdSchedule());
        if(rs.next()) {
            scheduleBean1.setIdSchedule(rs.getInt(1));
            scheduleBean1.setIdWork(rs.getInt(2));
            scheduleBean1.setIdGroup(rs.getInt(3));
            scheduleBean1.setIdCourse(rs.getInt(4));
            scheduleBean1.setIdTeacher(rs.getInt(5));
            scheduleBean1.setIdRoom(rs.getInt(6));
            scheduleBean1.setTime(rs.getInt(7));
        }
        connDB.close();

        if(scheduleBean.getTime() > 0) {
            ArrayList<Integer> integerArrayList = queryTime(scheduleBean1);
            for(int i : integerArrayList) {
                if(scheduleBean.getTime() == i) {
                    return 1;   //时间冲突
                }
            }
            connDB.executeUpdate("update `schedule` set `time`=" + scheduleBean.getTime() + " where `idschedule`=" + scheduleBean.getIdSchedule());
            connDB.close();
        }

        if(scheduleBean.getIdRoom() > 0) {
            int flag = 1;
            if(scheduleBean1.getTime() == 0) {
                flag = 1;
            }
            else {
                //可改进为idschedule行不算进里面，这样选同一个room不显示冲突，看起来无响应
                rs = connDB.executeQuery("select `time` from `schedule` where `idRoom`=" + scheduleBean.getIdRoom());
                while (rs.next()) {
                    if(rs.getInt(1) == scheduleBean1.getTime()) {
                        flag = 0;
                        break;
                    }
                }
                connDB.close();
            }
            if(flag == 0) {
                return 2;   //教室冲突
            }
            connDB.executeUpdate("update `schedule` set `idRoom`=" + scheduleBean.getIdRoom() + " where `idschedule`=" + scheduleBean.getIdSchedule());
            connDB.close();
            if(scheduleBean1.getIdRoom() > 0) {
                connDB.executeUpdate("update `room` set `roomScheduled`=if(`roomScheduled`<1,0,`roomScheduled`-1) where `idroom`=" + scheduleBean1.getIdRoom());
                connDB.close();
            }
            connDB.executeUpdate("update `room` set `roomScheduled`=`roomScheduled`+1 where `idroom`=" + scheduleBean.getIdRoom());
            connDB.close();
        }

        if(scheduleBean.getIdTeacher() > 0) {
            int flag = 1;
            ArrayList<ScheduleBean> scheduleBeanArrayList = queryByWork(scheduleBean.getIdWork());
            scheduleBean1.setIdTeacher(scheduleBeanArrayList.get(0).getIdTeacher());
            int size = scheduleBeanArrayList.size();
            for (int i = 0; i < scheduleBeanArrayList.size(); i++) {
                if(scheduleBeanArrayList.get(i).getTime() == 0) {
                    scheduleBeanArrayList.remove(i);
                    i--;
                }
            }
            if(scheduleBeanArrayList.size() == 0) {
                flag = 1;
            }
            else {
                for(ScheduleBean scheduleBean2 : scheduleBeanArrayList) {
                    for(ScheduleBean scheduleBean3 : scheduleBeanArrayList) {
                        if(scheduleBean2.getTime() != 0 && scheduleBean2.getIdSchedule() != scheduleBean3.getIdSchedule() && scheduleBean2.getTime() == scheduleBean3.getTime()) {
                            flag = 0;
                        }
                    }
                }
                if(flag == 1) {
                    rs = connDB.executeQuery("select `time` from `schedule` where `idWork`!=" + scheduleBean.getIdWork() + " and `idTeacher`=" + scheduleBean.getIdTeacher());
                    while (rs.next()) {
                        for(ScheduleBean scheduleBean2 : scheduleBeanArrayList) {
                            if(scheduleBean2.getTime() == rs.getInt(1)) {
                                flag = 0;
                                break;
                            }
                        }
                    }
                }
            }
            if(flag == 0) {
                return 3;   //教师冲突
            }
            connDB.executeUpdate("update `schedule` set `idTeacher`=" + scheduleBean.getIdTeacher() + " where `idWork`=" + scheduleBean.getIdWork());
            connDB.close();
            if(scheduleBean1.getIdTeacher() > 0) {
                connDB.executeUpdate("update `teacher` set `teacherScheduled`=if(`teacherScheduled`<" + size + ",0,`teacherScheduled`-" + size + ") where `idteacher`=" + scheduleBean1.getIdTeacher());
                connDB.close();
            }
            connDB.executeUpdate("update `teacher` set `teacherScheduled`=`teacherScheduled`+" + size + " where `idteacher`=" + scheduleBean.getIdTeacher());
            connDB.close();
        }
        return 4;   //成功
    }

    public void unfillRow(ScheduleBean scheduleBean) throws SQLException {
        ConnDB connDB = new ConnDB();

        if(scheduleBean.getTime() == 0) {
            connDB.executeUpdate("update `schedule` set `time`=0 where `idschedule`=" + scheduleBean.getIdSchedule());
            connDB.close();
        }

        if(scheduleBean.getIdTeacher() == 0) {
            int size = 0;
            int idTeacher = 0;
            ResultSet rs = connDB.executeQuery("select count(*) from `schedule` where `idWork`=" + scheduleBean.getIdWork());
            if(rs.next()) {
                size = rs.getInt(1);
            }
            connDB.close();
            rs = connDB.executeQuery("select `idTeacher` from `schedule` where `idWork`=" + scheduleBean.getIdWork());
            if(rs.next()) {
                idTeacher = rs.getInt(1);
            }
            connDB.close();
            connDB.executeUpdate("update `teacher` set `teacherScheduled`=if(`teacherScheduled`<" + size + ",0,`teacherScheduled`-" + size + ") where `idteacher`=" + idTeacher);
            connDB.close();
            connDB.executeUpdate("update `schedule` set `idTeacher`=0 where `idWork`=" + scheduleBean.getIdWork());
            connDB.close();
        }

        if(scheduleBean.getIdRoom() == 0) {
            int idRoom = 0;
            ResultSet rs = connDB.executeQuery("select `idRoom` from `schedule` where `idschedule`=" + scheduleBean.getIdSchedule());
            if(rs.next()) {
                idRoom = rs.getInt(1);
            }
            connDB.close();
            connDB.executeUpdate("update `room` set `roomScheduled`=if(`roomScheduled`<1,0,`roomScheduled`-1) where `idroom`=" + idRoom);
            connDB.close();
            connDB.executeUpdate("update `schedule` set `idRoom`=0 where `idschedule`=" + scheduleBean.getIdSchedule());
            connDB.close();
        }
    }

    //删除一行，修改 teacher&room.roomScheduled-1，work 补充
    public void deleteRow(ScheduleBean scheduleBean) throws SQLException {
        ConnDB connDB = new ConnDB();
        ConnDB connDB1 = new ConnDB();
        ResultSet rs = connDB.executeQuery("select `idWork`,`idTeacher`,`idRoom` from `schedule` where `idschedule`=" + scheduleBean.getIdSchedule());
        rs.next();
        connDB1.executeUpdate("update `teacher` set `teacherScheduled`=if(`teacherScheduled`<1,0,`teacherScheduled`-1) where `idteacher`=" + rs.getInt(2));
        connDB1.close();
        connDB1.executeUpdate("update `room` set `roomScheduled`=if(`roomScheduled`<1,0,`roomScheduled`-1) where `idroom`=" + rs.getInt(3));
        connDB1.close();
        connDB.close();
        connDB.executeUpdate("delete from `schedule` where `idschedule`=" + scheduleBean.getIdSchedule());
        connDB.close();
    }

    public ArrayList<ScheduleBean> queryAll() throws SQLException {
        ConnDB connDB = new ConnDB();
        String sql = "select * from `schedule`";
        ResultSet rs = connDB.executeQuery(sql);
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    //按班级查询
    public ArrayList<ScheduleBean> queryByGroup(String idGroup) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `idGroup`=" + idGroup + " order by `time` asc";
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    public ArrayList<ScheduleBean> queryByGroup1(String idGroup) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `time`>0 and `time`<=" + (new SettingProp().getNum()) * 5 + " and `idGroup`=" + idGroup;
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    public ArrayList<ScheduleBean> queryByGroup2(String idGroup) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `time`<=0 and `idGroup`=" + idGroup + " or " +
                " `time`>" + (new SettingProp().getNum()) * 5 + " and `idGroup`=" + idGroup;
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    //按教师查询
    public ArrayList<ScheduleBean> queryByTeacher(String idTeacher) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `idTeacher`=" + idTeacher;
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    //按教室查询
    public ArrayList<ScheduleBean> queryByRoom(String idRoom) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `idRoom`=" + idRoom;
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }

    public ArrayList<ScheduleBean> queryByWork(int idWork) throws SQLException {
        ConnDB connDB = new ConnDB();
        ArrayList<ScheduleBean> scheduleBeanArrayList = new ArrayList<>();
        String sql = "select * from `schedule` where `idWork`=" + idWork;
        ResultSet rs = connDB.executeQuery(sql);

        while (rs.next()) {
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.setIdSchedule(rs.getInt(1));
            scheduleBean.setIdWork(rs.getInt(2));
            scheduleBean.setIdGroup(rs.getInt(3));
            scheduleBean.setIdCourse(rs.getInt(4));
            scheduleBean.setIdTeacher(rs.getInt(5));
            scheduleBean.setIdRoom(rs.getInt(6));
            scheduleBean.setTime(rs.getInt(7));
            scheduleBeanArrayList.add(scheduleBean);
        }
        connDB.close();
        return scheduleBeanArrayList;
    }
}
