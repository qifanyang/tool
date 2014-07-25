package com.tobe.joda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import junit.framework.TestCase;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.chrono.CopticChronology;

/**joda for TimeUtil*/
public class TestJodaTimeUtil extends TestCase{

	private Calendar instance;

	public void test1(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		
		DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		
		System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
		
		
		//在某个时间上加90天
		calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		SimpleDateFormat sdf =
		  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		calendar.add(Calendar.DAY_OF_MONTH, 90);
		System.out.println(sdf.format(calendar.getTime()));
		
		dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		System.out.println(dateTime.plusDays(90).toString("yyyy-MM-dd HH:mm:ss E"));
		
		
		//Duration
		DateTime start = new DateTime(2004, 12, 25, 1, 0, 0, 0);
		DateTime end = new DateTime(2005, 1, 1, 0, 0, 0, 0);
		// duration in ms between two instants
		Duration dur = new Duration(start, end);//可以转换为秒,分,时,天
		// calc will be the same as end
		DateTime calc = start.plus(dur);
		System.out.println(calc.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(dur.getStandardDays());//一个时间段有好多天,不足一天直接舍去
		
		//
		DateTime dt = new DateTime();  // current time
		int month = dt.getMonthOfYear();
		System.out.println("month:"+month);
		System.out.println("dayOfWeek :" + dt.getDayOfWeek());
		
		//
		// setup objects
		LocalDate date = new LocalDate(2004, 12, 25);
		LocalTime time = new LocalTime(12, 20);

		int year = date.getYear();  // returns 2004
		int hour = time.getHourOfDay();  // returns 12
		System.out.println(year);
		System.out.println(hour);
		System.out.println(date.dayOfMonth().withMaximumValue().getDayOfMonth());//dayOfMonth() 返回day属性   withMaximumValue这个月最大天数
		
		dt = new DateTime(2014,2,1,0,0,0);
		System.out.println(dt.dayOfMonth().withMaximumValue().getDayOfMonth());//
		
		// interval from start to end
	    start = new DateTime(2004, 2, 2, 0, 0, 0, 0);
		end = new DateTime(2004, 3, 2, 0, 0, 0, 0);
		Interval interval = new Interval(start, end);
		Period period = interval.toPeriod();//可抓换为毫秒,秒,分,时,周,天,年
		
		period = new Period(start, end);
		// period of 1 year and 7 days
		// calc will equal end
		calc = start.plus(period);
		// able to calculate whole days between two dates easily
		Days days = Days.daysBetween(start, end);
		System.out.println("days :" + days.getDays());
		// able to calculate whole months between two dates easily
		Months months = Months.monthsBetween(start, end);
		System.out.println("months :" + months.getMonths());
		System.out.println(start.dayOfMonth().withMaximumValue());
		
		
		
		//
		DateTimeZone zone = DateTimeZone.forID("Europe/London");
		Chronology coptic = CopticChronology.getInstance(zone);

		// current time with coptic chronology
		 dt = new DateTime(coptic);

		 year = dt.getYear();   // gets the current coptic year
		 month = dt.getMonthOfYear(); // gets the current coptic month
		 System.out.println(year);
		 System.out.println(month);
		 
		 TimeZone jdkzone = TimeZone.getDefault();
		 System.out.println(jdkzone.getRawOffset());
		 
		 DateTimeZone jodazone = DateTimeZone.getDefault();
		 System.out.println(jodazone.getOffset(System.currentTimeMillis()));
		 System.out.println(jodazone.getID());
		 //九月第一个星期一
		 DateTime plusDays = new DateTime().monthOfYear().setCopy(7).dayOfMonth().withMinimumValue().plusDays(6).dayOfWeek().setCopy(1);
		 System.out.println(plusDays.toString("yyyy-MM-dd HH:mm:ss"));
//		  plusDays = new DateTime().monthOfYear().setCopy(9).dayOfMonth().withMinimumValue().plusDays(6).dayOfWeek().setCopy(1).plusWeeks(3);
//		 System.out.println(plusDays.toString("yyyy-MM-dd HH:mm:ss"));
		 instance = Calendar.getInstance();
		 instance.set(Calendar.WEEK_OF_MONTH, 1);
		 instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 System.out.println(format.format(instance.getTime()));
		 System.out.println(instance.get(Calendar.WEEK_OF_MONTH));
	}
	
}
