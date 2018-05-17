package com.ifoods.utils;

/*import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.protobuf.Message;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;*/
import org.apache.commons.lang3.StringUtils;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {
    public static <T> boolean isNull(T... objects) {
        for (T obj : objects) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean notNull(T... objects) {
        for (T obj : objects) {
            if (null == obj) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String... objects) {
        for (String obj : objects) {
            if (null == obj || obj.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notBlank(String... objects) {
        for (String obj : objects) {
            if (null == obj || obj.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmpty(Collection collection) {

        if (isNull(collection)) {
            return false;
        }
        if (collection.size() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean isEmpty(Collection collection) {
        if (isNull(collection)) {
            return true;
        }
        if (collection.size() <= 0) {
            return true;
        }
        return false;
    }

    public static <T extends Collection> boolean hasTheSameItem(T main, T second) {
        if (CommonUtils.notEmpty(main) && CommonUtils.notEmpty(second)) {
            for (Object objects : main) {
                if (second.contains(main)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> trimAndSplitBySemicolon(String s) {
        return (ArrayList) Arrays.asList(s.trim().split("\\s*;\\s*"));
    }

    public static List<String> listForStr(List<String> reses, String reg) {
        if (reses == null || reses.isEmpty() || StringUtils.isEmpty(reg)) {
            return null;
        }
        Set<String> set = new HashSet<String>();
        for (String res : reses) {
            if (!StringUtils.isEmpty(res)) {
                set.addAll(new HashSet<String>(Arrays.asList(res.split(reg))));
            }
        }
        return new ArrayList<String>(set);
    }

    /*public static String toPinYin(String str) throws PinyinException {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PinyinHelper.getShortPinyin(str)).append(";");
        stringBuilder.append(PinyinHelper.convertToPinyinString(str, ";", PinyinFormat.WITHOUT_TONE)).append(";");
        stringBuilder.append(PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE));
        return stringBuilder.toString();
    }*/

    /*public static Object mapToObject(Map<String, String> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }*/
    
    /**
     * 复制集合
     * @param <E>
     * @param source
     * @param destinationClass
     * @return
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    /*public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) throws IllegalAccessException, InvocationTargetException, InstantiationException{
        if (source.size()== 0) {
            return Collections.emptyList();
        }
        List<E> res = new ArrayList<E>(source.size());
        for (Object o : source) {
            E e = destinationClass.newInstance();
            BeanUtils.copyProperties(e, o);
            res.add(e);
        }
        return res;
    }*/

    /**
     * 设置equestHead
     * @param message
     * @return
     */
    /*public static Object setRequestHead(Message.Builder message,String rpcServiceName,String rpcMethodName){
        //调用者类名
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        //调用者方法名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        User user = UserHolder.getCurrentUser().get();
        Map<String,Object> headMap = new HashMap<>(16);
        if(null != user){
            headMap.put("groupID",user.getGroupId());
            headMap.put("userID",user.getUserId());
        }
        Map<String,Object> map =  new HashMap<>(16);
        // add by xiyong.lxy 20170909
        ConsoleContext context = ThreadLocalManager.getConsoleContext();
        if(context!=null) {
            headMap.put("traceID", context.getTraceId());
        }else {
            headMap.put("traceID",CommonUtils.newTraceId());
        }
        headMap.put("rpcID",className+":"+methodName+"#"+rpcServiceName+":"+rpcMethodName);
        headMap.put("version","1.0.1");
        map.put("commRequest",headMap);
        return ProtoConverter.mapToMessage(map,message);
    }*/

    /**
     * 设置equestHead
     * @return
     */
    /*public static Object setShopCenterRequestHead(long groupID,long userID,Message.Builder message,String rpcServiceName,String rpcMethodName){
        //调用者类名
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        //调用者方法名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Map<String,Object> headMap = new HashMap<>(16);
        headMap.put("groupID",groupID);
        headMap.put("userID",userID);
        Map<String,Object> map =  new HashMap<>(5);
        headMap.put("traceID",CommonUtils.newTraceId());
        headMap.put("rpcID",className+":"+methodName+"#"+rpcServiceName+":"+rpcMethodName);
        headMap.put("version","1.0.1");
        map.put("commRequest",headMap);
        return ProtoConverter.mapToMessage(map,message);
    }*/


    /** shop下方法因没有user 所以需要先创建user **/
    /*public static void setUser(Long gorupID,String userID){
        ThreadLocal<User> threadLocal = UserHolder.getCurrentUser();
        User user = new User();
        user.setGroupId(gorupID);
        user.setUserId(userID);
        threadLocal.set(user);
        UserHolder.setCurrentUser(threadLocal);
    }*/

    /**时间+随机数的生产方法 **/
    public static String random() {

        Random random = new Random();
        return Integer.toString(random.nextInt(Integer.MAX_VALUE), 36);
    }

    public static String time(String format) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return df.format(date);
    }

    public static String newTraceId(){
        String date = time("yyyyMMddHHmmssSSS");
        String rnd = random();
        return date + rnd;
    }



}
