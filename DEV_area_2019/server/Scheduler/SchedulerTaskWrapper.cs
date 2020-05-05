using NCrontab;
using System;

namespace Area.Scheduler
{
    public class SchedulerTaskWrapper
    {
        public CrontabSchedule Schedule { get; set; }
        public IScheduledTask Task { get; set; }
        public DateTime LastRunTime { get; set; }
        public DateTime NextRunTime { get; set; }

        public void Increment()
        {
            LastRunTime = NextRunTime;
            NextRunTime = Schedule.GetNextOccurrence(NextRunTime);
        }
        public bool ShouldRun(DateTime time)
        {
            return (NextRunTime < time && LastRunTime != NextRunTime);
        }
    }
}