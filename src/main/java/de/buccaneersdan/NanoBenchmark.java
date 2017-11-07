package de.buccaneersdan;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NanoBenchmark
{
	String cachedElapsedH, cachedStartTimeH, cachedStopTimeH;
	Long startTime, stopTime, elapsedTime;
	Date dtStartTime, dtStopTime;
	TimeTools.Duration cachedElapsedDuration;
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
	
	public void start()
	{
		reset();
		startTime = TimeTools.getLocalDate(new Date()).getTime();
	}

	public void stop()
	{
		stopTime = TimeTools.getLocalDate(new Date()).getTime();
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

	public TimeTools.Duration getElapsedDuration()
	{
		if (cachedElapsedDuration == null && elapsedTime != null)
			cachedElapsedDuration = TimeTools.getDateDuration(elapsedTime);
		return cachedElapsedDuration;
	}
	
	public String getElapsedDurationH()
	{
		if (cachedElapsedH == null && elapsedTime != null)
		{
			if (cachedElapsedDuration == null)
				cachedElapsedDuration = TimeTools.getDateDuration(elapsedTime);
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
