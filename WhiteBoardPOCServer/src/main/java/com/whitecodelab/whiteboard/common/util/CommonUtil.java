package com.whitecodelab.whiteboard.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtil {

	public static final int WEEK_SUN = 1; // 일
	public static final int WEEK_MON = 2; // 월
	public static final int WEEK_TUE = 3; // 화
	public static final int WEEK_WED = 4; // 수
	public static final int WEEK_THU = 5; // 목
	public static final int WEEK_FRI = 6; // 금
	public static final int WEEK_SAT = 7; // 토 
	
	/**
	 * 리스트 페이징 처리를 위한 offset 값 계산 (MYSQL 의 LIMIT 에서 사용하는 값)
	 * @param reqCnt : 페이당 목록 수 요청값
	 * @param reqPage : 요청 페이지 번호
	 * @return
	 */
	public static int getListOffset(int reqCnt, int reqPage) {
		if(reqCnt <= 0){
			return 0;
		}
		
		return (reqPage - 1) * reqCnt;
	}
	
	/**
	 * 이메일 정규식 검증
	 * 
	 * @param email : 검증할 이메일 형식의 문자열
	 * @return 유효한 이메일 형식인지 여부
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		String regex = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
	
		return Pattern.matches(regex, email.trim());
	}

	/**
	 * Servlet Path 의 .do 앞의 이름을 가져온다
	 * @param request
	 * @return
	 */
	public static String getLastLevelNameFromServletPath() {
		String servletPath = getRequest().getServletPath();
		return servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.lastIndexOf(".do"));
	}

	/**
	 * 현재 요청의 HttpServletRequest 를 가져온다
	 * @return 현재 요청의 HttpServletRequest 객체
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes
			= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return servletRequestAttributes.getRequest();
	}
	
	/**
	 * 새 패스워드를 부여할 때 사용할 메소드  
	 * @param charLength 자리수
	 * @return 62진수의 문자열로 구성된 패스워드 리턴
	 */
	public static String getNewPasswordBase62(int charLength) {
		
		String [] table = {
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};
		
		Random random = new Random(new Date().getTime());
		
		StringBuilder str = new StringBuilder();
		
		for(int i=0; i<charLength; i++) {
			str.append( table[random.nextInt(table.length)] );
		}
		
		return str.toString();
	}
	
	
	public static int getDay(int year, int month) {
		
		switch (month) {
			case 2 :
				if ((year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
					return 29;
				} else { 
					return 28;
				}
			case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 :
				return 31;
			default : 
				return 30;			
		}
	}
	
	/**************************************************************
	 * MethodName: getUTCNowTime
	 * @author   : Lee Sang-Sub
	 * @date     : 2014. 10. 24.
	 * Comment   : 현재 UTC 시간 구하기. 
	**************************************************************/
	public static long getUTCNowTime(){
		// 현재시간 구하기.
		TimeZone utc = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(utc);
		return calendar.getTimeInMillis();
	}

	/**************************************************************
	 * MethodName: getCuttingDoubleVal
	 * @author   : Jung-Min Park
	 * @date     : 2014. 10. 28.
	 * Comment   : 소수점이 들어온경우 3자리 이하수는 절작한다. 
	**************************************************************/
	public static double getCuttingDoubleVal(double value) { 
		return (double)((int)(value * 1000 / 10) / (double) 100);
	}
	
	
	/**************************************************************
	 * MethodName: getStartWeekByMonth
	 * @author   : Jung-Min Park
	 * @date     : 2014. 10. 27.
	 * Comment   : 각 주의 시작일을 계산한다. 
	**************************************************************/	
	public static ArrayList<Integer> getStartWeekByMonth(int nStartYear, int nStartMonth, int nStartDayOfWeek) {
		
		ArrayList<Integer> nArray = new ArrayList<Integer>();
		
		try { 
			int nLastDay = 0; 
		    SimpleDateFormat lastDateFormat = new SimpleDateFormat("yyyyMMdd") ;
		    Date nLastDate = lastDateFormat.parse(String.format("%04d", nStartYear) + String.format("%02d", nStartMonth) + String.format("%02d", 1)) ;
		    Calendar lastCal = Calendar.getInstance();
		    lastCal.setTime(nLastDate);
		    nLastDay = lastCal.getActualMaximum(Calendar.DATE);
			
		    for (int i = 0; i < nLastDay; i++) {
		    	
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd") ;
			    Date nDate = dateFormat.parse(String.format("%04d", nStartYear) + String.format("%02d", nStartMonth) + String.format("%02d", i + 1)) ;
			     
			    Calendar cal = Calendar.getInstance();
			    cal.setTime(nDate);

			    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;		    	
		    	
		    	if (dayNum == nStartDayOfWeek) { 
		    		if (i == 0) { 
		    			continue ;
		    		}	
		    		nArray.add(i);
		    	}
		    }
		    
		    if (nArray.get(nArray.size() - 1) < nLastDay) { 
		    	nArray.add(nLastDay);
		    }
		    
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
	    return nArray;
	}	
}
