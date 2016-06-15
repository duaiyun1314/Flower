package com.andy.flower;

/**
 * Created by andy on 16-6-6.
 */
public class Constants {

    public static final String Authorization = "Authorization";

    //user
    public static final String IS_LOGIN = "is_login";
    public static final String LOGINTIME = "loginTime";
    public static final String USERNAME = "userName";
    public static final String USERID = "userID";
    public static final String USERACCOUNT = "userAccount";
    public static final String USERPASSWORD = "userPassword";
    public static final String USERHEADKEY = "userHeadKey";
    public static final String USEREMAIL = "userEmail";
    //token
    public static final String TOKENACCESS = "TokenAccess";
    public static final String TOKENREFRESH = "TokenRefresh";
    public static final String TOKENTYPE = "TokenType";
    public static final String TOKENEXPIRESIN = "TokeExpiresIn";

    //board info
    public static final String BOARDTILTARRAY = "boardTitleArray";
    public static final String BOARDIDARRAY = "boardIdArray";


    public static final String USERNAME_NULL = "用户名为空";
    public static final String PASSWORD_NULL = "密码为空";

    public static final String EMAIL_INVALID = "邮箱格式不正确";

    private static final String mBasic = "Basic ";
    private static final String mClientInfo = "MWQ5MTJjYWU0NzE0NGZhMDlkODg6Zjk0ZmNjMDliNTliNDM0OWExNDhhMjAzYWIyZjIwYzc=";
    public static final String mClientInto = mBasic + mClientInfo;


    //获得用户画板列表详情的操作符
    public static final String OPERATEBOARDEXTRA = "recommend_tags";

    //,
    public static final String SEPARATECOMMA = ",";

    //root url
    public static final String ImgRootUrl = "http://img.hb.aicdn.com/";
    public static final String Register_Url = "http://www.huaban.com";

    public static final String[] Categories_ID = new String[]{"all", "design", "illustration", "home", "apparel", "men", "wedding_events", "industrial_design", "photography", "modeling_hair", "food_drink", "travel_places", "diy_crafts", "fitness", "kids", "pets", "quotes", "people", "beauty", "desire", "geek", "anime", "architecture", "art", "data_presentation", "games", "cars_motorcycles", "film_music_books", "tips", "education", "sports", "funny"};
    public static final String[] Categories_NAMES = new String[]{"首页", "平面", "插画/漫画", "家居/家装", "女装/搭配", "男士/风尚", "婚礼", "工业设计", "摄影", "造型/美妆", "美食", "旅行", "手工/布艺", "健身/舞蹈", "儿童", "宠物", "美图", "明星", "美女", "礼物", "极客", "动漫", "建筑设计", "人文艺术", "数据图", "游戏", "汽车/摩托", "电影/图书", "生活百科", "教育", "运动", "搞笑"};
    public static final int PAGE_COUNT_LIMIT = 20;
    public static final int CATEGORY_CACHE_COUNT = 5;
}