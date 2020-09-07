package de.jackleemmerdeur;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class NanoBenchmark
{
	public static class Duration
	{
		private Long cachedTotalSeconds = null;
		private Long hours = null;
		private Integer minutes = null;
		private Integer seconds = null;
		private Integer milliseconds = null;

		public Duration(Long hours, Integer minutes, Integer seconds, Integer milliseconds)
		{
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
			this.milliseconds = milliseconds;
		}

		public Duration() { }

		public void setHours(long hours)
		{
			if (this.hours == null || !this.hours.equals(hours))
			{
				cachedTotalSeconds = null;
				this.hours = hours;
			}
		}

		public void setMinutes(Integer minutes)
		{
			if (this.minutes == null || !this.minutes.equals(minutes))
			{
				cachedTotalSeconds = null;
				this.minutes = minutes;
			}
		}

		public void setSeconds(Integer seconds)
		{
			if (this.seconds == null || !this.seconds.equals(seconds))
			{
				cachedTotalSeconds = null;
				this.seconds = seconds;
			}
		}

		public void setMilliseconds(Integer milliseconds)
		{
			if (this.milliseconds == null || !this.milliseconds.equals(milliseconds))
			{
				cachedTotalSeconds = null;
				this.milliseconds = milliseconds;
			}
		}

		public Long getHours() { return hours; }
		public Integer getMinutes() { return minutes; }
		public Integer getSeconds() { return seconds; }
		public Integer getMilliseconds() { return milliseconds; }

		public Long getTotalSeconds()
		{
			if (cachedTotalSeconds == null)
			{
				Long secs = null;
				if (hours != null) secs = hours * 3600;
				if (minutes != null) secs = ((secs == null) ? 0 : secs) + minutes * 60;
				if (seconds != null) secs = ((secs == null) ? 0 : secs) + seconds;
				if (milliseconds != null) secs = ((secs == null) ? 0 : secs) + (milliseconds / 1000);
				cachedTotalSeconds = secs;
			}
			return cachedTotalSeconds;
		}
	}

	public static Calendar UTC_PARSER = null;
	String cachedElapsedH, cachedStartTimeH, cachedStopTimeH;
	Long startTime, stopTime, elapsedTime;
	Date dtStartTime, dtStopTime;
	Duration cachedElapsedDuration;
	SimpleDateFormat sdf;
	
	public NanoBenchmark()
	{
		sdf = new SimpleDateFormat("HH:mm:ss:S");
		reset();
	}
	
	public void reset()
	{
		cachedElapsedDuration = null;
		cachedStartTimeH = null;
		cachedStopTimeH = null;
		cachedElapsedH = null;
		startTime = null;
		stopTime = null;
		elapsedTime = null;
	}
	public static Duration getDateDuration(long elapsedTime)
	{
		Duration dd = new Duration();
		if (elapsedTime < 1000)
		{
			dd.milliseconds = (int) elapsedTime;
		}
		else if (elapsedTime < 60000)
		{
			dd.seconds = (int) TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			dd.milliseconds = (int) (elapsedTime - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(elapsedTime)));
		}
		else if (elapsedTime < 3600000)
		{
			long mins = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
			long secs = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			long restsecs = secs - TimeUnit.MINUTES.toSeconds(mins);
			long ms = elapsedTime - secs * 1000;
			dd.minutes = (int) mins;
			dd.seconds = (int) restsecs;
			dd.milliseconds = (int) ms;
		}
		else
		{
			long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
			long mins = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
			long restmins = mins - TimeUnit.HOURS.toMinutes(hours);
			long restMS = elapsedTime - (TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(restmins));
			long restsecs = TimeUnit.MILLISECONDS.toSeconds(restMS);
			long ms = elapsedTime - (TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(restmins) + TimeUnit.SECONDS.toMillis(restsecs));
			dd.hours = hours;
			dd.minutes = (int) restmins;
			dd.seconds = (int) restsecs;
			dd.milliseconds = (int) ms;
		}
		return dd;
	}
	private static Date getLocalDate(Date utcdate)
	{
		if (UTC_PARSER == null)
		{
			UTC_PARSER = Calendar.getInstance();
			UTC_PARSER.setTimeZone(TimeZone.getDefault());
		}
		UTC_PARSER.setTime(utcdate);
		return UTC_PARSER.getTime();
	}

	public void start()
	{
		reset();
		startTime = getLocalDate(new Date()).getTime();
	}

	public void stop()
	{
		stopTime = getLocalDate(new Date()).getTime();
		elapsedTime = stopTime - startTime;
	}

	public Long getElapsedMS()
	{
		return elapsedTime;
	}

	public Long getElapsedS()
	{
		return (elapsedTime >= 1000) ? elapsedTime / 1000 : 0;
	}

	public Duration getElapsedDuration()
	{
		if (cachedElapsedDuration == null && elapsedTime != null)
			cachedElapsedDuration = getDateDuration(elapsedTime);
		return cachedElapsedDuration;
	}
	
	public String getElapsedDurationH()
	{
		if (cachedElapsedH == null && elapsedTime != null)
		{
			if (cachedElapsedDuration == null)
				cachedElapsedDuration = getDateDuration(elapsedTime);
			Long h = cachedElapsedDuration.getHours();
			Integer m = cachedElapsedDuration.getMinutes();
			Integer s = cachedElapsedDuration.getSeconds();
			Integer ms = cachedElapsedDuration.getMilliseconds();
			cachedElapsedH = String.format(
				"%dh %dmin %dsec %dms",
				h == null ? 0 : h,
				m == null ? 0 : m,
				s == null ? 0 : s,
				ms == null ? 0 : ms);
		}
		return cachedElapsedH;
	}
	
	public String getStartTimeH()
	{
		Date starttime = getStartTime();
		if (cachedStartTimeH == null)
			if (starttime != null)
				cachedStartTimeH = sdf.format(starttime);
		return cachedStartTimeH;
	}
	
	
	public Date getStartTime()
	{
		if (dtStartTime == null)
			if (startTime != null)
				dtStartTime = new Date(startTime);
		return dtStartTime;
	}
	
	public String getStopTimeH()
	{
		Date stoptime = getStopTime();
		if (cachedStopTimeH == null)
			if (stoptime != null)
				cachedStopTimeH = sdf.format(stoptime);
		return cachedStopTimeH;
	}
	
	public Date getStopTime()
	{
		if (dtStopTime == null)
			if (stopTime != null)
				dtStopTime = new Date(stopTime);
		return dtStopTime;
	}

	public void writeToFileNoThrow(String filepath, boolean append, boolean humanReadableDuration)
		throws FileNotFoundException, Exception
	{
		writeToFile(filepath, append, humanReadableDuration);
	}

	public void writeToFile(String filepath, boolean append, boolean humanReadableDuration)
			throws UnsupportedEncodingException,
			FileNotFoundException,
			Exception
	{
		File f = new File(filepath);
		BufferedWriter writer = null;

		boolean reallyappend = false;

		if (f.exists())
			if (append)
				reallyappend = true;
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath, reallyappend), "UTF-8"));
			writer.append("At ");
			writer.append(getStartTimeH());
			writer.append(": ");
			writer.append(((humanReadableDuration) ? getElapsedDurationH() : String.valueOf(getElapsedMS())));
			writer.newLine();
		}
		finally
		{
			if (writer != null)
				writer.close();
		}
	}
}
