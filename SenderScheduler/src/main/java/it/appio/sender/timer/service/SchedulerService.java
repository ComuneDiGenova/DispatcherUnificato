package it.appio.sender.timer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.appio.sender.timer.info.TimerInfo;
import it.appio.sender.timer.util.TimerUtils;

/**
 * Definisce lo scheduler quartz che viene poi eseguito dal timer
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class SchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

	// inject
	private final Scheduler scheduler;

	@Autowired
	public SchedulerService(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@SuppressWarnings("rawtypes")
	public void schedule(final Class jobClass, final TimerInfo timerInfo) throws SchedulerException {
		final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, timerInfo);
		final Trigger trigger = TimerUtils.buildTrigger(timerInfo);

		scheduler.scheduleJob(jobDetail, trigger);
	}

	public List<TimerInfo> getAllRunningTimers() throws SchedulerException {
		List<TimerInfo> result = new ArrayList<TimerInfo>();
		Set<JobKey> keys = scheduler.getJobKeys(GroupMatcher.anyGroup());

		for (JobKey key : keys) {
			JobDetail jobDetail = scheduler.getJobDetail(key);
			result.add((TimerInfo) jobDetail.getJobDataMap().get(key.getName()));
		}

		return result;
	}

	public boolean deleteTimer(String timerId) throws SchedulerException {
		boolean result = scheduler.deleteJob(new JobKey(timerId));
		return result;
	}

	@PostConstruct
	public void init() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
