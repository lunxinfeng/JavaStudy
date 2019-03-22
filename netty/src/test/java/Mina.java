import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mina指令
 * Created by lxf on 2016/12/12.
 */
public class Mina {
    /**
     * 注册成功
     */
    public static final int register = 1;
    /**
     * 聊天
     */
    public static final int chat = 2;
    /**
     * 最新棋步
     */
    public static final int newChess = 3;
    /**
     * 认输
     */
    public static final int giveUp = 4;
    /**
     * 申请悔棋
     */
    public static final int askForRegret = 5;
    /**
     * 同意悔棋
     */
    public static final int agreeRegret = 6;
    /**
     * 不同意悔棋
     */
    public static final int disagreeRegret = 7;
    /**
     * 申请数子
     */
    public static final int askForCount = 52;

    /**
     * 同意数子
     */
    public static final int agreeCount = 53;
    /**
     * 不同意数子
     */
    public static final int disagreeCount = 54;

    /**
     * 发送数子结果
     */
    public static final int sendCountResult = 56;
    /**
     * 修改下棋邀请
     */
    public static final int modifyChess = 109;
    /**
     * 同意下棋邀请
     */
    public static final int agreeChess = 101;
    /**
     * 邀请下棋
     */
    public static final int requestChess = 108;

    /**
     * 拒绝下棋邀请
     */
    public static final int disagreeChess = 102;

    /**
     * 邀请某人下棋
     */
    public static final int request_Other_Chess = 100;

    /**
     * 虚拟账号对弈
     */
    public static final int chessWithAI = 111;

    /**
     * 邀请虚拟账号下棋
     */
    public static final int request_AI_Chess = 110;


    /**
     * 进出具体某个房间
     */
    public static final int gameroom_in_out = 103;
    /**
     * 断开Mina连接（服务器主动发）
     */
    public static final int disconnect_mina = 104;
    /**
     * 开始直播
     */
    public static final int start_live = 199;
    /**
     * 直播发布抢答
     */
    public static final int qiangDa = 200;
    /**
     * 直播发布问答
     */
    public static final int wenDa = 201;
    /**
     * 直播举手
     */
    public static final int juShou = 202;
    /**
     * 授权落子
     */
    public static final int author_chess = 203;
    /**
     * 直播提交答案
     */
    public static final int commit_chess = 204;
    /**
     * 直播落子
     */
    public static final int live_chess = 205;
    /**
     * 授权语音
     */
    public static final int author_voice = 206;
    /**
     * 取消授权语音
     */
    public static final int cancel_author_voice = 207;
    /**
     * 直播页对抗指令
     */
    public static final int live_vs = 208;
    /**
     * 直播结束对抗
     */
    public static final int live_vs_exit = 209;
    /**
     * 直播课堂说明
     */
    public static final int live_class_summary = 210;
    /**
     * 全体禁言
     */
    public static final int live_no_speak = 212;
    /**
     * 主麦推流状态回调（服务器主动发）
     */
    public static final int push_state_1 = 400;
    /**
     * 副麦推流状态回调（服务器主动发）
     */
    public static final int push_state_2 = 402;
    /**
     * 混流状态回调（服务器主动发）
     */
    public static final int mix_state = 401;
    /**
     * 有新通知（服务器主动发）
     */
    public static final int new_notice = 403;
    public static final int reLogin = 1000;
    /**
     * 悔棋
     */
    public static final int regret = 65;
    /**
     * 下发到电子棋盘
     */
    public static final int to_chessBoard = 300;
    /**
     * 电子棋盘课堂结束对局
     */
    public static final int finishGame = 301;
    /**
     * 赛事对弈提交裁判请求重判
     */
    public static final int commit_refree = 501;
    public static String commit_refree(int gameId, int sendId) {
        return "[{\"gameid\":" + gameId  + ",\"sendid\":" + sendId  + "}]";
    }
    public static String regret(int gameId, int backNum, int userId) {
        return "[{\"id\":\"" + gameId + "\",\"backNum\":\"" + backNum + "\",\"sendid\":\"" + userId + "\"}]";
    }

    public static String register(int sendId, String sendName, String sendDate, String level, String des, int roomType, String dynamicKey) {
        return "[{\"id\":\"" + sendId + "\",\"send_ctn\":\"" + des + "\",\"send_date\":\"" + sendDate + "\",\"roomType\":\"" + roomType + "\",\"Level\":\"" + level + "\",\"send_person\":\"" + sendName + "\",\"DynamicKey\":\"" + dynamicKey + "\"}]";
    }

    public static String chatMsg(int userid, String username, String send_ctn, int groupId, int toId) {
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String msgJson = "[{\"id\":\"" + String.valueOf(userid)
                + "\",\"send_ctn\":\"" + send_ctn
                + "\",\"send_date\":\"" + f1.format(now)
                + "\",\"groupId\":\"" + String.valueOf(groupId)
                + "\",\"toId\":\"" + String.valueOf(toId)
                + "\",\"send_userid\":\"" + userid
                + "\",\"send_person\":\"" + username + "\"}]";

        return msgJson;

    }

    public static String newChess(int gameId, String lastStep, int playNum, long time_surplus, int X) {
        return "[{\"id\":\"" + gameId + "\",\"f_position\":\"" + lastStep + "\",\"f_num\":\"" + playNum + "\",\"surplus_time\":\""
                + time_surplus + "\",\"X\":\"" + X + "\" }]";
    }

    public static String askForCount(int userId) {
        return "[{\"yhnm\":\"" + userId + "\"}]";
    }

    public static String agreeCount(int userId) {
        return "[{\"yhnm\":\"" + userId + "\"}]";
    }

    public static String disagreeCount(int userId) {
        return "[{\"yhnm\":\"" + userId + "\"}]";
    }

    public static String askForRegret(int gameId, int userId) {
        return "[{\"id\":\"" + gameId + "\",\"yhnm\":\"" + userId + "\"}]";
    }

    public static String agreeRegret(int gameId, int userId) {
        return "[{\"id\":\"" + gameId + "\",\"yhnm\":\"" + userId + "\"}]";
    }

    public static String disagreeRegret(int gameId, int userId) {
        return "[{\"id\":\"" + gameId + "\",\"yhnm\":\"" + userId + "\"}]";
    }

    public static String giveUp(int gameId, int userId, String winnerup, String looserdown, String roomtype, String winnerEXP, String looserEXP, int otheruserId, String result) {
        return "[{\"id\":\"" + gameId + "\",\"yhnm\":\"" + userId + "\",\"winnerup\":\"" + winnerup + "\",\"looserdown\":\""
                + looserdown + "\",\"gamestyle\":\"" + roomtype + "\",\"winnerEXP\":\"" + winnerEXP + "\",\"looserEXP\":\""
                + looserEXP + "\",\"f_blackid\":\"" + userId + "\",\"f_whiteid\":\"" + otheruserId + "\",\"result\":\"" + result + "\"}]";
    }

    public static String giveUp(int gameId, int userId, String winnerup, String looserdown, String roomtype, String winnerEXP, String looserEXP, int otheruserId, String result,int beats,int weats) {
        return "[{\"id\":\"" + gameId + "\",\"yhnm\":\"" + userId + "\",\"winnerup\":\"" + winnerup + "\",\"looserdown\":\""
                + looserdown + "\",\"gamestyle\":\"" + roomtype + "\",\"winnerEXP\":\"" + winnerEXP + "\",\"looserEXP\":\""
                + looserEXP + "\",\"f_blackid\":\"" + userId + "\",\"f_whiteid\":\"" + otheruserId + "\",\"result\":\"" + result + "\",\"beats\":" + beats+ ",\"weats\":" + weats + "}]";
    }

//    /**
//     * @param ruler   对弈规则lko
//     * @param blackId 黑方id
//     * @param whiteId 白方id
//     * @param creater 我要下棋请求的发起人id
//     */
//    public static String agreeChess(Ruler ruler, int blackId, int whiteId, int creater) {
//        return "[{\"f_rule\":\"" + ruler.toString() + "\",\"f_blackid\":\"" + blackId + "\",\"f_whiteid\":\"" + whiteId + "\",\"f_creater\":\"" + creater + "\"}]";
//    }
//
//    /**
//     * @param ruler    对弈规则
//     * @param sendName 发送人名字
//     * @param sendId   发送人id
//     */
//    public static String modifyChess(Ruler ruler, String sendName, String level, int sendId, int creater, String winning, String score) {
//        return "[{\"sendName\":\"" + sendName + "\",\"level\":\"" + level + "\",\"sendId\":\"" + sendId + "\",\"f_rule\":\"" + ruler.toString() + "\",\"f_creater\":\"" + creater + "\",\"winning\":\"" + winning + "\",\"score\":\"" + score + "\"}]";
//    }

    public static String requestChess(int userId, String userName, String level, String defaultRuler, int usertype, String winning, String score) {
        return "[{\"userId\":" + userId + ",\"userName\":\"" + userName + "\",\"level\":\"" + level + "\",\"f_rule\":\"" + defaultRuler + "\",\"usertype\":" + usertype + ",\"winning\":\"" + winning + "\",\"score\":\"" + score + "\"}]";
    }


    public static String refuseInvitation(int sType,String userName) {
        return "[{\"sType\":\"" + sType + "\",\"userid\":\"" + "*" + "\",\"userName\":\"" + userName + "\"}]";
    }

    public static String sendCountResult(int userid, String StrResult, String tileViewString) {

        return "[{\"yhnm\":\"" + userid + "\",\"StrResult\":\"" + StrResult + "\",\"tileViewString\":\""
                + tileViewString + "\"}]";
    }


    /**
     * @param userid
     * @param gameid
     * @param stype
     * @param realname
     * @return
     */
    public static String EnterOrLeaveRoom(int userid, int gameid, String stype, String realname) {

        return "[{\"id\":\"" + gameid + "\",\"userid\":\"" + userid + "\",\"stype\":\""
                + stype + "\",\"realName\":\"" + realname + "\"}]";
    }

    public static String startLive(int userid, int gameid, String time) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"time\":\"" + time + "\",\"stream\":\"" + "*" + "\",\"mixedstream\":\"" + "#" + "\"}]";
    }

    public static String qiangDa(int userid, int gameid, String sgf, String step, int sgftype, int sgfsource, int boardsize, String sgfid) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"type\":" + 1 + ",\"sgftype\":" + sgftype + ",\"boardsize\":" + boardsize + ",\"sgfsource\":" + sgfsource + ",\"sgfid\":\"" + sgfid + "\",\"sgf\":\"" + sgf + "\",\"step\":\"" + step + "\",\"id\":\"" + "*" + "\"}]";
    }

    public static String wenDa(int userid, int gameid, String sgf, String step, int sgftype, int sgfsource, int boardsize, String sgfid) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"type\":" + 2 + ",\"sgftype\":" + sgftype + ",\"boardsize\":" + boardsize + ",\"sgfsource\":" + sgfsource + ",\"sgfid\":\"" + sgfid + "\",\"sgf\":\"" + sgf + "\",\"step\":\"" + step + "\",\"id\":\"" + "*" + "\"}]";
    }

    public static String juShou(int userid, int codetype) {
        return "[{\"userid\":" + userid + ",\"codetype\":" + codetype + "}]";
    }

    public static String authorChess(int userid, int codetype) {
        return "[{\"userid\":" + userid + ",\"codetype\":" + codetype + "}]";
    }

    public static String commitChess(int userid, int gameid, int type, String sgf, String username, int id) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"type\":" + type + ",\"sgf\":\"" + sgf + "\",\"username\":\"" + username + "\",\"id\":\"" + id + "\"}]";
    }

    public static String liveChess(int userid, String step, int gameId) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameId + ",\"step\":\"" + step + "\"}]";
    }

    public static String authorVoice(int userid, int gameid, String time) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"time\":\"" + time + "\",\"stream\":\"" + "*" + "\",\"mixedstream\":\"" + "#" + "\"}]";
    }

    public static String cancelAuthorVoice(int userid, int gameid) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + "}]";
    }

    public static String liveVS(int userid, int gameid, String sgf, String step, int sgftype, int sgfsource, int boardsize, String sgfid) {
        return "[{\"userid\":" + userid + ",\"gameid\":" + gameid + ",\"type\":" + 3 + ",\"sgftype\":" + sgftype + ",\"boardsize\":" + boardsize + ",\"sgfsource\":" + sgfsource + ",\"sgfid\":\"" + sgfid + "\",\"sgf\":\"" + sgf + "\",\"step\":\"" + step + "\",\"id\":\"" + "*" + "\"}]";
    }

    public static String liveVSExit(int userid) {
        return "[{\"userid\":" + userid + "}]";
    }

    /**
     * @param type 0:允许发言  -1：全体禁言
     */
    public static String liveNoSpeak(int gameid, int type) {
        return "[{\"gameid\":" + gameid + ",\"type\":" + type + "}]";
    }


    public static String liveClassSumarry(int gameid, int userid,String content) {
        return "[{\"gameid\":" + gameid + ",\"userid\":" + userid + ",\"content\":\"" + content + "\"}]";
    }

    public static String to_chessBoard(int classId) {
        return "[{\"classid\":" + classId + "}]";
    }
}
