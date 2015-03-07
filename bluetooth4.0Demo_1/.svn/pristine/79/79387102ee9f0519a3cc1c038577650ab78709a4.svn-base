package com.example.mybletestdemo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
/**
 * 数据转换工具类
 * @author Administrator
 *
 */
public class DataUtil {

	private static StringBuffer sbTime;
	private static String now, year, month, day, hour, min, ss;
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

	private static String resultString = null;
	private static String resultTrimData = null;

	public static DecimalFormat df = new DecimalFormat("0.00");

	public DataUtil() {

	}

	/**
	 * 时间同步 取得当前的时间, 并转为需要的格式: 16进制String并以F4开头
	 */
	public static String getTimeFormat() {
		sbTime = new StringBuffer();
		now = null;
		now = sdf.format(new Date());
		year = Integer
				.toHexString(Integer.parseInt(now.substring(0, 4)) - 1900);
		now.substring(4, 6);
		month = Integer.toHexString(Integer.parseInt(now.substring(4, 6)));
		if (month.length() == 1)
			month = '0' + month;
		day = Integer.toHexString(Integer.parseInt(now.substring(6, 8)));
		if (day.length() == 1)
			day = '0' + day;
		hour = Integer.toHexString(Integer.parseInt(now.substring(8, 10)));
		if (hour.length() == 1)
			hour = '0' + hour;
		min = Integer.toHexString(Integer.parseInt(now.substring(10, 12)));
		if (min.length() == 1)
			min = '0' + min;
		ss = Integer.toHexString(Integer.parseInt(now.substring(12, 14)));
		if (ss.length() == 1)
			ss = '0' + ss;
		sbTime.append("F4");
		sbTime.append(year);
		sbTime.append(month);
		sbTime.append(day);
		sbTime.append(hour);
		sbTime.append(min);
		sbTime.append(ss);
		sbTime.append("00");
		now = sbTime.toString().toUpperCase();
		return now;
	}

	/**
	 * 取得String字符的前两位
	 */
	public static String getTag(String data) {
		if (!data.isEmpty()) {
			resultString = data.substring(0, 2);
		}
		return resultString;
	}

	/**
	 * 去除String字符中所有的空白符
	 */
	public static String getTrimData(String data) {
		if (!data.isEmpty()) {
			resultTrimData = data.replaceAll("\\s*", "");
		}
		return resultTrimData;
	}

	/**
	 * byte[]转变为16进制String字符, 每个字节2位, 不足补0
	 */
	public static String getStringByBytes(byte[] bytes) {
		String result = null;
		String hex = null;
		if (bytes != null && bytes.length > 0) {
			final StringBuilder stringBuilder = new StringBuilder(bytes.length);
			for (byte byteChar : bytes) {
				hex = Integer.toHexString(byteChar & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				stringBuilder.append(hex.toUpperCase());
			}
			result = stringBuilder.toString();
		}
		return result;
	}

	/**
	 * 把16进制String字符转变为byte[]
	 */
	public static byte[] getBytesByString(String data) {
		byte[] bytes = null;
		if (data != null) {
			data = data.toUpperCase();
			int length = data.length() / 2;
			char[] dataChars = data.toCharArray();
			bytes = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				bytes[i] = (byte) (charToByte(dataChars[pos]) << 4 | charToByte(dataChars[pos + 1]));
			}
		}
		return bytes;
	}

	/**
	 * 取得在16进制字符串中各char所代表的16进制数
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 方法介绍
	 */
	public static void helpInfo() {
		System.out.println("十进制转成十六进制: Integer.toHexString(int i) ");
		System.out.println("十六进制转成十进制: Integer.valueOf('FFFF',16).toString() ");
	}

	/**
	 * 根据传入的两个double类型的时间戳, 后者比较大, 算出他们之间隔了多少小时
	 */
	public static double getHours(double date1, double date2) {
		double hours = (date2 - date1) / (1000 * 60 * 60);
		hours = Double.parseDouble(df.format(hours));
		// hours = Math.round((hours * 100) / 100);
		return hours;
	}

	/**
	 * 根据 步数(步), 身高(厘米), 体重(千克), 时间(小时)算出消耗的能量KCal(千卡) kcal = 体重 * 时间 * 指数(k) k
	 * = 30 / 速度( N分钟 / 400米) = 30 / 多少分钟400米
	 */
	public static double getKCal(int step, double height, double weight,
			double time) {
		double kcal = 0;
		// 步幅 = 身高 * 0.45 (米)
		double stride = (double) (height * 0.45 / 100);
		// 速度 = 时间(分钟) / (步幅 * 步数(米) / 400)
		double speed = (time * 60) / (stride * step / 400);
		// 指数 = 30 / 速度(分钟/400米)
		double k = 30 / speed;
		kcal = weight * time * k;
		kcal = Double.valueOf(df.format(kcal));
		return kcal;
	}

	/**
	 * 
	 * 根据 步数(步), 身高(厘米),步幅(厘米),体重(千克), 时间(小时)算出消耗的能量KCal(千卡) kcal = 体重 * 时间 * 指数(k) k
	 * = 30 / 速度( N分钟 / 400米) = 30 / 多少分钟400米
	 * @param step
	 * @param height
	 * @param stride
	 * @param weight
	 * @param time
	 * @return
	 */
	public static double getKCal(int step, double height, double stride,
			double weight, double time) {
		double kcal = 0;
		// 步幅 = 身高 * 0.45 (米)
		// double stride = (double) (height * 0.45 / 100);
		// 速度 = 时间(分钟) / (步幅 * 步数(米) / 400)
		double speed = (time * 60) / (stride * step / 400);
		// 指数 = 30 / 速度(分钟/400米)
		double k = 30 / speed;
		kcal = weight * time * k;
		kcal = Double.valueOf(df.format(kcal));
		return kcal;
	}

	/**
	 * 根据传过来的时间String转成毫秒级的时间数据double型 数据格式可能为2:00:25或者01:25
	 */
	public static long getMillsTime(String time) {
		long hh, mm, ss;
		int l = time.length();
		if (l <= 5 && l > 0) {
			hh = 0;
			mm = Long.valueOf(time.substring(0, 2));
			ss = Long.valueOf(time.substring(3, 5));
			return (mm * 60 + ss) * 1000;
		} else if (l == 7) {
			hh = Long.valueOf(time.substring(0, 1));
			mm = Long.valueOf(time.substring(2, 4));
			ss = Long.valueOf(time.substring(5, 7));
			return (hh * 60 * 60 + mm * 60 + ss) * 1000;
		} else if (l == 8) {
			hh = Long.valueOf(time.substring(0, 2));
			mm = Long.valueOf(time.substring(3, 5));
			ss = Long.valueOf(time.substring(6, 8));
			return (hh * 60 * 60 + mm * 60 + ss) * 1000;
		} else {
			return 0;
		}
	}

	/**
	 * 根据传入的耗秒数, 转换成为HH:MM:SS的字符串返回
	 */
	public static String getHHMMSS(long time) {
		String hhmmss = "00:00:00";
		StringBuffer bf = new StringBuffer();
		long hh = time / 1000 / 60 / 60;
		long mm = (time % (1000 * 60 * 60)) / 1000 / 60;
		long ss = ((time % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		if (hh < 0) {
			bf.append("00:");
		} else if (hh < 10) {
			bf.append("0" + hh + ":");
		} else {
			bf.append(hh + ":");
		}
		if (mm < 0) {
			bf.append("00:");
		} else if (mm < 10) {
			bf.append("0" + mm + ":");
		} else {
			bf.append(mm + ":");
		}
		if (ss < 0) {
			bf.append("00");
		} else if (ss < 10) {
			bf.append("0" + ss);
		} else {
			bf.append(ss);
		}
		hhmmss = bf.toString();
		System.out.println(hhmmss);
		return hhmmss;
	}

	/**
	 * 根据传入的时间mills和距离(千米)得出速度
	 */
	public static double getSpeed(double mills, double dist) {
		double hours = 0;
		hours = (Double) (mills * 0.001 / 60 / 60);
		if (hours == 0) {
			return 0;
		}
		double speed = dist / hours;
		speed = Double.valueOf(df.format(speed));
		// hours = double.valueOf(df.format(hours));
		return speed;
	}

	/**
	 * 根据用户传入的身高与步数, 返回用户步行的里程
	 */
	public static String getDisc(String height, String stepCount) {
		String dist = "0";
		double distance_dou = Double.valueOf(stepCount)
				* Double.valueOf(height) * 0.45 * 0.01 * 0.001;
		String distance_str = df.format(distance_dou);
		return distance_str;
	}

	/**
	 * 根据传入的当前步数与目标步数，计算完成的百分比
	 */
	public static String getPercent(String currentStep, String targetStep) {
		double percent = Double.valueOf(currentStep)
				/ Double.valueOf(targetStep) * 100;
		return df.format(percent);
	}

	/**
	 * 根据传入的提醒类型和电话号码，返回字节数组
	 * 
	 * @param remindType
	 *            提醒类型
	 * @param phoneNumber
	 *            电话号码
	 * @return
	 */
	public static byte[] getBytesForRemind(String remindType, String phoneNumber) {
		StringBuffer remindStr = new StringBuffer();
		// 提醒的类型
		String type = Integer.toHexString(Integer.parseInt(remindType, 2));
		// 电话号码的位数
		String length = Integer.toHexString(phoneNumber.length());

		remindStr.append("F1");
		remindStr.append(type);
		remindStr.append("000");
		remindStr.append(length.toUpperCase());
		// 对座机、手机号码的处理
		phoneNumber = (phoneNumber.length() % 2) == 0 ? phoneNumber
				: phoneNumber + "0";
		remindStr.append(phoneNumber);
		System.out.println("提示数据协议：" + remindStr.toString());
		return getBytesByString(remindStr.toString());
	}

	/**
	 * 传入未接电话和未读短信数
	 * 
	 * @param calls
	 *            未接电话数
	 * @param sms
	 *            未读短信数
	 * @return 发出信号所需的字节数组
	 */
	public static byte[] getSMS_CALL_Count(int calls, int sms) {
		StringBuffer sb = new StringBuffer();
		String missCall = Integer.toHexString(calls);
		String unReadSMS = Integer.toHexString(sms);
		sb.append("FA");
		// 拼未接电话数
		if (missCall.length() == 1) {
			sb.append("000");
			sb.append(missCall);
		} else if (missCall.length() == 2) {
			sb.append("00");
			sb.append(missCall);
		} else if (missCall.length() == 3) {
			sb.append("0");
			sb.append(missCall);
		} else if (missCall.length() == 4) {
			sb.append(missCall);
		}
		// 拼未读短信数
		if (unReadSMS.length() == 1) {
			sb.append("000");
			sb.append(unReadSMS);
		} else if (unReadSMS.length() == 2) {
			sb.append("00");
			sb.append(unReadSMS);
		} else if (unReadSMS.length() == 3) {
			sb.append("0");
			sb.append(unReadSMS);
		} else if (unReadSMS.length() == 4) {
			sb.append(unReadSMS);
		}
		sb.append("000000");
		System.out.println("未接电话和未读短信协议：" + sb.toString());
		return getBytesByString(sb.toString());
	}

	/**
	 * 根据传入的2个字节4位16进制字符比如FFFF, 计算返回int类型的绝对值
	 */
	public static int hexStringX2bytesToInt(String hexString) {
		return binaryString2int(hexString2binaryString(hexString));
	}

	/**
	 * 16进制转换为2进制
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0) {
			return null;
		}
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 二进制转为10进制 返回int
	 */
	public static int binaryString2int(String binarysString) {
		if (binarysString == null || binarysString.length() % 8 != 0) {
			return 0;
		}
		int result = Integer.valueOf(binarysString, 2);
		if ("1".equals(binarysString.substring(0, 1))) {
			System.out.println("这是个负数");
			char[] values = binarysString.toCharArray();
			for (int i = 0; i < values.length; i++) {
				if (values[i] == '1') {
					values[i] = '0';
				} else {
					values[i] = '1';
				}
			}
			binarysString = String.valueOf(values);
			result = Integer.valueOf(binarysString, 2) + 1;
		}

		return result;
	}

	/**
	 * 
	 * 二进制转为16进制
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
			return null;
		}
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 根据传入的X, Y, Z 算出 x平方 + y平方 + c 平方 的平方根值
	 */
	public static int getXYZsquareRoot(int x, int y, int z) {
		return (int) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 取当前的时间, 返回int型的小时, 比如 23:59:59 返回 23的int
	 */
	public static int getCurrentHour() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	/**
	 * 取得当前的分钟数
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.MINUTE);
		return minute;
	}

	/**
	 * 取得当前的秒数
	 */
	public static int getCurrentSecond() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.SECOND);
		return minute;
	}

	/**
	 * 传入天气及温度的String, 返回协议的bytes[]
	 */
	public static byte[] getWeatherInfo(String weather, String temp) {

		Map<String, String> weatherMap = new HashMap<String, String>();

		weatherMap.put("晴", "01");
		weatherMap.put("阴", "02");
		weatherMap.put("多云", "03");
		weatherMap.put("小雨", "04");
		weatherMap.put("中雨", "05");
		weatherMap.put("小到中雨", "05");
		weatherMap.put("大雨", "06");
		weatherMap.put("中到大雨", "06");
		weatherMap.put("雷阵雨", "07");
		weatherMap.put("阵雨", "07");
		weatherMap.put("暴雨", "07");
		weatherMap.put("大暴雨", "07");
		weatherMap.put("特大暴雨", "07");
		weatherMap.put("大到暴雨", "07");
		weatherMap.put("暴雨到大暴雨", "07");
		weatherMap.put("暴雨到特大暴雨", "07");
		weatherMap.put("小雪", "08");
		weatherMap.put("中雪", "09");
		weatherMap.put("小到中雪", "09");
		weatherMap.put("大雪", "0A");
		weatherMap.put("阵雪", "0A");
		weatherMap.put("暴雪", "0A");
		weatherMap.put("中到大雪", "0A");
		weatherMap.put("大到暴雪", "0A");
		weatherMap.put("雨夹雪", "0B");
		weatherMap.put("雾", "0C");
		weatherMap.put("冰雹", "0D");
		weatherMap.put("冻雨", "0D");
		weatherMap.put("雷阵雨伴有冰雹", "0E");
		weatherMap.put("尘埃", "0F");
		weatherMap.put("沙尘暴", "0F");
		weatherMap.put("浮尘", "0F");
		weatherMap.put("扬沙", "0F");
		weatherMap.put("强沙尘暴", "0F");
		weatherMap.put("霾", "0F");
		weatherMap.put("热带风暴", "10");
		weatherMap.put("风", "11");
		weatherMap.put("大风", "12");
		weatherMap.put("狂风", "13");
		weatherMap.put("龙卷风", "14");
		weatherMap.put("雷暴", "15");
		weatherMap.put("猛烈雷暴", "16");
		byte[] bytes = null;
		StringBuilder sb = new StringBuilder();
		sb.append("F2");
		// 天气
		String weatherCode = weatherMap.get(weather);
		if (weatherCode != null) {
			sb.append(weatherCode);
		} else {
			sb.append("03");
		}
		// System.out.println("收到的天气是 : " + weather + ", 其转换为代码是 : " +
		// weatherCode
		// + "测试天气 : " + weatherMap.get("阵雨"));
		// 温度单位
		Integer intTemp = Integer.valueOf(temp, 10);
		if (intTemp < 0) {
			sb.append("40");
		} else {
			sb.append("C0");
		}
		// 温度值
		String hexTemp = Integer.toHexString(Math.abs(intTemp)).toString();
		sb.append(hexTemp);
		// 不足补零
		sb.append("00000000");

		// System.out.println("send weather info : " + sb.toString());

		return getBytesByString(sb.toString());
	}
	
	/**
	 * 根据传入的数，计算返回整百值
	 * 如传入156，返回200
	 * @param snore_count
	 * @return
	 */
	public static int getMaxbySnorecount(int snore_count){
		String str = (snore_count + "");
		int length = str.length();
		if (length < 3) {
			return 100;
		} else {
			str = str.substring(0, length - 2);
			int max = (Integer.parseInt(str) + 1) * 100;
			System.out.println("转换后的max:" + max);
			return max;
		}
	}
	
}
