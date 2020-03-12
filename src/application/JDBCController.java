package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

/**
 * @author Xuwen Ren & Jiayuan Zhang
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class JDBCController {

    public int count = 1;
    Connection c = null;
    Statement s = null;

    /**
     * default constructor
     */
    public JDBCController() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return connection to mysql
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/student_basic?characterEncoding=UTF-8",
                "root", "12345678");

    }

    /**
     * add general visit record except appointment
     *
     * @param stu_id student id
     * @param opera operation id
     * @param opedeta operation details
     */
    public void insertGeneral(int stu_id, int opera, String opedeta) {

        String sql = "Insert into visit_record(stu_id, opetime,operation,opedetails) VALUES(?,NOW(),?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);) {

            ps.setInt(1, stu_id);
            ps.setInt(2, opera);
            ps.setString(3, opedeta);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    /**
     * add appointment record include appointment time, student id and teachers
     *
     * @param appointtime appointment time
     * @param stu_id student id
     * @param teacher teacher's name
     */
    public void insertAppointment(Date appointtime, int stu_id, String teacher) {
        String sql = "Insert into appointment(appointtime, stu_id,teacher) VALUES(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);) {
            insert_general(stu_id, 0, teacher);
            ps.setDate(1, appointtime);
            ps.setInt(2, stu_id);
            ps.setString(3, teacher);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * get locker ID when assign a locker
     *
     * @return locker id
     */
    public int getLockerId() {
        int id = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "SELECT id from locker where id=FLOOR(RAND()*50)";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return id;
    }

    /**
     * get password of the respondent locker ID
     *
     * @param id locker id
     * @return password
     */
    public String getPassword(int id) {
        String pass = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "SELECT password from locker where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                pass = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return pass;
    }

    /**
     * get random alert information
     *
     * @return alert information
     */
    public String getAlert() {
        String alert = "22";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            int id = (int) (Math.random() * 3 + 1);
            String sql = "Select introduction from alert where id=" + id;
            ResultSet rs1 = s.executeQuery(sql);
            while (rs1.next()) {
                alert = rs1.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return alert;
    }

    // 
    /**
     * get random announcement
     *
     * @return announcement
     */
    public String getAnnounce() {
        String alert = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            int id = (int) (Math.random() * 3 + 1);
            String sql = "Select announce from announcement where id =" + id;
            ResultSet rs1 = s.executeQuery(sql);
            while (rs1.next()) {
                alert = rs1.getString(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return alert;
    }

    /**
     * get the name of student whose ID is id
     *
     * @param id student id
     * @return student name
     */
    public String getName(int id) {
        String name = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select name from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * get the gender of student whose ID is id
     *
     * @param id student id
     * @return student gender
     */
    public String getGender(int id) {
        String gender = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select gender from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                gender = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gender;
    }

    /**
     * get the program of student whose ID is id
     *
     * @param id student id
     * @return student's program
     */
    public String getProgram(int id) {
        String program = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select program from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                program = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return program;
    }

    /**
     * get the date of birth of student whose ID is id
     *
     * @param id student id
     * @return student's date of birth
     */
    public Date getDob(int id) {
        Date dob = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select birthday from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                dob = rs.getDate(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dob;
    }

    /**
     * get the Andrew ID of student whose ID is id
     *
     * @param id student id
     * @return student's Andrew id
     */
    public String getAndrewid(int id) {
        String andrewid = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select andrewid from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                andrewid = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return andrewid;
    }

    /**
     * get the path of photo of the student whose ID is id
     *
     * @param id student id
     * @return path of student's photo
     */
    public String getPhoto(int id) {
        String photo = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select photo from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                photo = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }

    /**
     * get the total visit times of student whose ID is id
     *
     * @param id student id
     * @return total visit times of student
     */
    public int getVisittimes(int id) {
        int visittimes = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "Select count(distinct id) from visit_record where stu_id=" + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs == null) {
                visittimes = 0;
            } else {
                while (rs.next()) {
                    visittimes = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visittimes;
    }

    /**
     * get the last visit time of student whose ID is id
     *
     * @param id student id
     * @return last visit time of this student
     */
    public String getLastvisit(int id) {
        Timestamp time = null;
        String lastvisit = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "Select MAX(opetime) latestime from visit_record WHERE stu_id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                time = rs.getTimestamp(1);
                if (time == null) {
                    lastvisit = "No record";
                } else {
                    lastvisit = time + "";
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastvisit;
    }

    /**
     * inforscene pichar
     *
     * @param id student id
     * @return Map seperately containing the visit times of all the categories
     */
    public Map<Integer, Integer> getcategory(int id) {
        Map<Integer, Integer> cateMap = new HashMap<>();
        try (Connection c = getConnection(); Statement s = c.createStatement();) {

            String sql = "Select operation,count(distinct id) from visit_record where stu_id=" + id + " group by operation";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cateMap.put(rs.getInt(1), rs.getInt(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return cateMap;

    }

    /**
     * get alert information
     *
     * @return alert information
     */
    public String get_alert() {
        String alert = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            int id = (int) (Math.random() * 3 + 1);
            String sql = "Select introduction from alert where id=" + id;
            ResultSet rs1 = s.executeQuery(sql);
            while (rs1.next()) {
                alert = rs1.getString(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return alert;
    }

    /**
     * get announcement information
     *
     * @return announcement
     */
    public String get_announce() {
        String alert = "";
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            int id = (int) (Math.random() * 3 + 1);
            String sql = "Select announce from announcement where id =" + id;
            ResultSet rs1 = s.executeQuery(sql);
            while (rs1.next()) {
                alert = rs1.getString(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return alert;
    }

    /**
     * report female pichart
     *
     * @param startDate start date of the report
     * @param endDate end date of the report
     * @return Map containing seperately visit records of each category for
     * female
     */
    public Map<Integer, Integer> get_femalecategoryap(Date startDate, Date endDate) {
        Map<Integer, Integer> cateMap = new HashMap<>();
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "Select operation,count(distinct visit_record.id) from visit_record LEFT join student_basic on visit_record.stu_id=student_basic.id where gender='female' "
                    + "and opetime BETWEEN" + "'" + startDate + "'" + "and" + "'" + endDate + "'" + "GROUP BY operation";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cateMap.put(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return cateMap;
    }

    /**
     * report male pichart
     *
     * @param startDate start date of the report
     * @param endDate end date of the report
     * @return Map containing seperately visit records of each category for male
     */
    public Map<Integer, Integer> get_malecategoryap(Date startDate, Date endDate) {
        Map<Integer, Integer> cateMap = new HashMap<>();
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "Select operation,count(distinct visit_record.id) from visit_record LEFT join student_basic on stu_id where gender='male' "
                    + "and opetime BETWEEN" + "'" + startDate + "'" + "and" + "'" + endDate + "'" + "GROUP BY operation";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cateMap.put(rs.getInt(1), rs.getInt(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return cateMap;

    }

    /**
     * report category bar chart
     *
     * @param startDate start date of the report
     * @param endDate end date of the report
     * @return Map containing seperately total numebr of visit records of each
     * category for all the students
     */
    public Map<Integer, Integer> get_categoryap(Date startDate, Date endDate) {
        Map<Integer, Integer> cateMap = new HashMap<>();
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "Select operation,count(distinct visit_record.id) from visit_record LEFT join student_basic on stu_id where opetime BETWEEN"
                    + "'" + startDate + "'" + "and" + "'" + endDate + "'" + "GROUP BY operation";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cateMap.put(rs.getInt(1), rs.getInt(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();// TODO: handle exception
        }
        return cateMap;

    }

    /**
     * insert visit record
     *
     * @param stu_id student id
     * @param opera operation
     * @param opedeta operation detail
     */
    public void insert_general(int stu_id, int opera, String opedeta) {

        String sql = "Insert into visit_record(stu_id, opetime,operation,opedetails) VALUES(?,NOW(),?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);) {

            ps.setInt(1, stu_id);
            ps.setInt(2, opera);
            ps.setString(3, opedeta);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    /**
     * add new user information
     *
     * @param name student name
     * @param gender student gender
     * @param program student program
     * @param birthday student date of birth
     * @param andrewid student's Andrew ID
     * @return student id
     */
    public int insertnewuser(String name, String gender, String program, Date birthday, String andrewid) {
        FXController fx = new FXController();
        int num = fx.count - 1;
        String photo = "pic/new_user" + num + ".jpg";
        String sql = "Insert into student_basic(name, gender,program,birthday,andrewid,photo) VALUES(?,?,?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);) {

            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setString(3, program);
            ps.setDate(4, birthday);
            ps.setString(5, andrewid);
            ps.setString(6, photo);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return getid(name);

    }

    /**
     * add photo of new user into database
     *
     * @param id student id
     * @param outfile path of the photo of nwe student
     */
    public void insertphoto(int id, String outfile) {
        outfile = "pic/new_user" + id + ".jpg";
        String sql = "Insert into student_basic(photo) VALUES(?)";
        try (Connection c = getConnection(); PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);) {

            ps.setString(1, outfile);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * get the id of student whose Name is name
     *
     * @param name student name
     * @return student id
     */
    public int getid(String name) {
        int id = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select id from student_basic where name=" + "'" + name + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * get the photo of student whose ID is id
     *
     * @param id student id
     * @return path of photo
     */
    public String getphoto(int id) {
        String photo = null;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
            String sql = "select photo from student_basic where id=" + id;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                photo = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }

}
