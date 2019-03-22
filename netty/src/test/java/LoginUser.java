import java.util.Date;

/**
 * 简明用户实体，登录后返回给前端。综合登录信息（前期：ID、姓名、用户类型、动态口令、权限）和权限信息
 *
 * @author apple
 */
public class LoginUser {
    public static final int admin4 = 4;
    public static final int teacher = 5;
    public static final int student = 6;

    private Integer userid; // userid
    private String account; // 登录账号
    private String password; // 登录密码
    private String realname; // 用户姓名
    private Integer usertype; // 用户类型（0：公司；1：一级；2：二级；3：媒体）
    private Integer parentid; // 父级ID
    private String dypassword; // 动态口令
    private Date lastlogintime; // 最后登录时间
    private String lastloginip; // 最后登录IP
    private String notes; // 备注信息
    private String authority; //用户权限，通过关联表得到。多角色用户权限颗粒需去重，减少数据传输。
    private String sex; // 性别
    private Date birthday; // 出生日期
    private String idcard; // 身份证号
    private String province; // 省
    private String city; // 市
    private String county; // 区
    private String town; // town
    private String address; // 具体地址
    private String workplace; // workplace
    private String tel; // 联系电话
    private String qq; // qq
    private String post; // post
    private Date addtime; // 添加时间
    private String summary;//简介
    private int classid;//班级id
    private String classname;//班级名称
    private String f_roomnum;
    private String level;
    private String chessname;//棋校名称
    private int chessid;
    private String winning = "";
    private String score = "";
    private String ruler;
    private int roomtype;
    private String permission;
    private String token;
    private int levelid;//等级id
    private int yquser;//是否弈趣用户

    public int getYquser() {
        return yquser;
    }

    public void setYquser(int yquser) {
        this.yquser = yquser;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(int roomtype) {
        this.roomtype = roomtype;
    }

    public String getRuler() {
        return ruler;
    }

    public void setRuler(String ruler) {
        this.ruler = ruler;
    }

    public String getWinning() {
        return winning;
    }

    public void setWinning(String winning) {
        this.winning = winning;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String salt;//盐

    public String getProvince_chess() {
        return province_chess;
    }

    public void setProvince_chess(String province_chess) {
        this.province_chess = province_chess;
    }

    public String getCity_chess() {
        return city_chess;
    }

    public void setCity_chess(String city_chess) {
        this.city_chess = city_chess;
    }

    public String getCounty_chess() {
        return county_chess;
    }

    public void setCounty_chess(String county_chess) {
        this.county_chess = county_chess;
    }

    private String province_chess;
    private String city_chess;
    private String county_chess;

    public String getViptime() {
        return viptime;
    }

    public void setViptime(String viptime) {
        this.viptime = viptime;
    }

    public int getVipdate() {
        return vipdate;
    }

    public void setVipdate(int vipdate) {
        this.vipdate = vipdate;
    }

    private String viptime;
    private int vipdate;    // 剩余VIP时间

    public int getIfaccept() {
        return ifaccept;
    }

    public void setIfaccept(int ifaccept) {
        this.ifaccept = ifaccept;
    }

    private int ifaccept;//是否接受挑战:1为接受;0为拒绝

    public String getChessname() {
        return chessname;
    }

    public void setChessname(String chessname) {
        this.chessname = chessname;
    }

    public int getChessid() {
        return chessid;
    }

    public void setChessid(int chessid) {
        this.chessid = chessid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        if (birthday == null) {
            return new Date();
        } else {
            return birthday;
        }

    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getF_roomnum() {
        return f_roomnum;
    }

    public void setF_roomnum(String f_roomnum) {
        this.f_roomnum = f_roomnum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getDypassword() {
        return dypassword;
    }

    public void setDypassword(String dypassword) {
        this.dypassword = dypassword;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getLastloginip() {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String _authority) {
        this.authority = _authority;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }


}