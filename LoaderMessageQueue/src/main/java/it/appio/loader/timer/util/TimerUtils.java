package it.appio.loader.timer.util;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import it.appio.loader.timer.info.TimerInfo;

/**
 * Utils per lo scheduler
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class TimerUtils {

	private TimerUtils() {
	}

	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo timerInfo) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(timerInfo.getTimerName(), timerInfo);

		return JobBuilder.newJob(jobClass).withDescription(timerInfo.getTimerName()).withIdentity(timerInfo.getTimerName()).setJobData(jobDataMap)
				.build();

	}

	public static Trigger buildTrigger(final TimerInfo timerInfo) {
		CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(timerInfo.getCronScheduleValue());

		return TriggerBuilder.newTrigger().withIdentity(timerInfo.getTimerName()).withSchedule(builder)
				.startAt(new Date(System.currentTimeMillis() + timerInfo.getInitialOffsetMs())).build();
	}

}
